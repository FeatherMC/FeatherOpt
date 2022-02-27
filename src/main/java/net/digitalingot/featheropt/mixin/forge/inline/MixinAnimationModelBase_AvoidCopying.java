package net.digitalingot.featheropt.mixin.forge.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.AnimationModelBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AnimationModelBase.class, remap = false)
public class MixinAnimationModelBase_AvoidCopying {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    public EnumFacing[] featherOpt$getCachedArray() {
        return Constants.FACINGS;
    }

}
