package net.digitalingot.featheropt.mixin.forge.inline;

import net.digitalingot.featheropt.helpers.Constants;
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
@Mixin(value = BlockInfo.class, remap = false)
public class MixinBlockInfo_AvoidCopying {
    @Redirect(method = "updateLightMatrix", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$getCachedArray() {
        return Constants.FACINGS;
    }
}
