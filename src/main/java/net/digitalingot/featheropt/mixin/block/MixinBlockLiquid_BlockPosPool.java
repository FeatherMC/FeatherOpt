package net.digitalingot.featheropt.mixin.block;

import net.digitalingot.featheropt.helpers.AssociatedMutableBlockPos;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid_BlockPosPool {

    @ModifyVariable(
            method = "getFlowVector",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing$Plane;iterator()Ljava/util/Iterator;", ordinal = 0),
            argsOnly = true
    )
    public BlockPos featherOpt$getFromPool(BlockPos pos) {
        return AssociatedMutableBlockPos.get(pos);
    }

    @Redirect(
            method = "getFlowVector",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;")
    )
    public BlockPos featherOpt$usedPoolBlockPos(BlockPos instance, EnumFacing direction) {
        return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos().move(direction);
    }

    @Inject(
            method = "getFlowVector",
            at = @At("RETURN")
    )
    public void featherOpt$releaseBlockPos(IBlockAccess p_getFlowVector_1_, BlockPos blockPos, CallbackInfoReturnable<Vec3> cir) {
        ((AssociatedMutableBlockPos) blockPos).release();
    }
}
