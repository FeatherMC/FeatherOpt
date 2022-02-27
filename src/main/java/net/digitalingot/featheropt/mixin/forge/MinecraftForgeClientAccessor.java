package net.digitalingot.featheropt.mixin.forge;

import com.google.common.cache.LoadingCache;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MinecraftForgeClient.class, remap = false)
public interface MinecraftForgeClientAccessor {

    @Accessor("regionCache")
    static LoadingCache<Pair<World, BlockPos>, RegionRenderCache> featherOpt$getRegionCache() {
        throw new AssertionError();
    }

}
