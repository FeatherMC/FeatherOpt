package net.digitalingot.featheropt.mixin.forge;

import com.google.common.cache.LoadingCache;
import net.digitalingot.featheropt.helpers.LightUtilHook;
import net.digitalingot.featheropt.helpers.VertexLighterFlatHook;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Backported from Minecraft Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/0d34b02f8821bf114a7ee8e298af6263e7abb8d0
 * and
 * https://github.com/MinecraftForge/MinecraftForge/commit/1aae18d4bc28c0436ef2b5f243f7aa25d8d1c649
 *
 * @author bs2609 & ichttt
 */
@Mixin(LightUtil.class)
public class MixinLightUtil_OptimizedAddingNormals {

    @Unique
    private static final ConcurrentMap<Pair<VertexFormat, VertexFormat>, int[]> FEATHER_OPT$FORMAT_MAPS = new ConcurrentHashMap<>();
    @Unique
    private static final VertexFormat FEATHER_OPT$DEFAULT_FROM = VertexLighterFlatHook.withNormal(DefaultVertexFormats.BLOCK);
    @Unique
    private static final VertexFormat FEATHER_OPT$DEFAULT_TO = DefaultVertexFormats.ITEM;
    @Unique
    private static final int[] FEATHER_OPT$DEFAULT_MAPPING = LightUtilHook.generateMapping(FEATHER_OPT$DEFAULT_FROM, FEATHER_OPT$DEFAULT_TO);

    @Redirect(method = "putBakedQuad", at = @At(value = "INVOKE", target = "Lcom/google/common/cache/LoadingCache;getUnchecked(Ljava/lang/Object;)Ljava/lang/Object;", remap = false), remap = false)
    private static <K, V> V featherOpt$putBakedQuad$redirectCacheAccess(@SuppressWarnings("UnstableApiUsage") LoadingCache<K, V> instance, K k) {
        VertexFormat vertexFormat = (VertexFormat) k;
        return (V) mapFormats(vertexFormat, DefaultVertexFormats.ITEM);
    }

    /**
     * @author bs2609 & ichttt
     * @reason Improve performance of vertex format mapping by caching hashcodes; plus cache default from & to
     */
    @Overwrite(remap = false)
    public static int[] mapFormats(VertexFormat from, VertexFormat to) {
        //Speedup: in 99.99% this is the mapping, no need to go make a pair, and go through the slower hash map
        if (from.equals(FEATHER_OPT$DEFAULT_FROM) && to.equals(FEATHER_OPT$DEFAULT_TO))
            return FEATHER_OPT$DEFAULT_MAPPING;

        return FEATHER_OPT$FORMAT_MAPS.computeIfAbsent(Pair.of(from, to), pair -> LightUtilHook.generateMapping(pair.getLeft(), pair.getRight()));
    }
}
