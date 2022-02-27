package net.digitalingot.featheropt.mixin.forge;

import com.google.common.cache.LoadingCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/90bf8dd95dd3b7955896d3268d0ec13d06a7575f
 *
 * @author bs2609
 */
@Mixin(Minecraft.class)
public class MixinMinecraft_ShorterReferenceHolding {

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("TAIL"))
    public void featherOpt$clearCache(WorldClient world, String worldName, CallbackInfo ci) {
        LoadingCache<Pair<World, BlockPos>, RegionRenderCache> regionCache = MinecraftForgeClientAccessor.featherOpt$getRegionCache();
        regionCache.invalidateAll();
        regionCache.cleanUp();
    }

}
