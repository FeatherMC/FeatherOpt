package net.digitalingot.featheropt.mixin.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockModelRenderer.class)
public class MixinBlockModelRenderer {

    @Redirect(method = "renderModelAmbientOcclusion", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$ambientOcclusion$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "renderModelStandard", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$standard$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "fillQuadBounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$fillQuadBounds$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "renderModelBrightnessColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$renderModelBrightnessColor$getCachedArray() {
        return Constants.FACINGS;
    }
}
