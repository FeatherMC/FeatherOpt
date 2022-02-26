package net.digitalingot.featheropt.mixin.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class MixinWorld_DirectValues {

    @Redirect(method = "getRawLight", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    public EnumFacing[] featherOpt$getRawLight$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "checkLightFor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    public EnumFacing[] featherOpt$checkLightFor$getCachedArray() {
        return Constants.FACINGS;
    }

    @Redirect(method = "isBlockIndirectlyGettingPowered", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    public EnumFacing[] featherOpt$isBlockIndirectlyGettingPowered$getCachedArray() {
        return Constants.FACINGS;
    }

}
