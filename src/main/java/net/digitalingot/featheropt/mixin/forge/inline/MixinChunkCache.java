package net.digitalingot.featheropt.mixin.forge.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Similar to the caching of the array in https://github.com/MinecraftForge/MinecraftForge/commit/8d938660e7a8e504c731ab29c70945c5220db34b
 */
@Mixin(ChunkCache.class)
public class MixinChunkCache {
    @Redirect(method = "getLightForExt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$getCachedArray() {
        return Constants.FACINGS;
    }
}
