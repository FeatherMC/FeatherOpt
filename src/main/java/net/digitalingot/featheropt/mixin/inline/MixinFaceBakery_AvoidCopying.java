package net.digitalingot.featheropt.mixin.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FaceBakery.class)
public class MixinFaceBakery_AvoidCopying {

    @Redirect(method = "getPositionsDiv16", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$getPositionsDiv16$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "getFacingFromVertexData", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private static EnumFacing[] featherOpt$getFacingFromVertexData$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "applyFacing", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$applyFacing$getCachedArray() {
        return Constants.FACINGS;
    }

}
