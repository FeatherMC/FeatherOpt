package net.digitalingot.featheropt.mixin.forge;

import net.minecraftforge.client.model.TRSRTransformation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/0b5a6a3b031816ca46d4bc405a0062781dec914b
 *
 * @author bs2609
 */
@Mixin(value = TRSRTransformation.class, remap = false)
public class MixinTRSRTransformation_ReduceTransformationAllocations {

    @Shadow
    @Final
    private static TRSRTransformation identity;

    @Inject(method = "compose", at = @At("HEAD"), cancellable = true)
    public void featherOpt$dontAllocateWhenIdentity(TRSRTransformation b, CallbackInfoReturnable<TRSRTransformation> cir) {
        TRSRTransformation instance = (TRSRTransformation) (Object) this;
        if (instance == identity) {
            cir.setReturnValue(b);
        } else if (b == identity) {
            cir.setReturnValue(instance);
        }
    }

    @Inject(method = "blockCenterToCorner", at = @At("HEAD"), cancellable = true)
    private static void featherOpt$blockCenterToCorner$dontCalculateTrivial(TRSRTransformation b, CallbackInfoReturnable<TRSRTransformation> cir) {
        if (b == identity) {
            cir.setReturnValue(b);
        }
    }

    @Inject(method = "blockCornerToCenter", at = @At("HEAD"), cancellable = true)
    private static void featherOpt$blockCornerToCenter$dontCalculateTrivial(TRSRTransformation b, CallbackInfoReturnable<TRSRTransformation> cir) {
        if (b == identity) {
            cir.setReturnValue(b);
        }
    }
}
