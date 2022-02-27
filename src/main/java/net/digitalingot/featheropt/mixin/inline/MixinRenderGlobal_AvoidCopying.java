package net.digitalingot.featheropt.mixin.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal_AvoidCopying {

    @Redirect(method = "setupTerrain", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$setupTerrain$getCachedArray() {
        return Constants.FACINGS;
    }

}
