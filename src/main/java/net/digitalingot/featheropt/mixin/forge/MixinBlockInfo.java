package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.BlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/8d938660e7a8e504c731ab29c70945c5220db34b
 *
 * @author bs2609
 */
@Mixin(BlockInfo.class)
public class MixinBlockInfo {
    @Redirect(method = "updateLightMatrix", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] feather$getCachedArray() {
        return Constants.FACINGS;
    }
}
