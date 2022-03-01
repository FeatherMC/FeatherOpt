package net.digitalingot.featheropt.mixin.world;

import net.digitalingot.featheropt.helpers.PooledMutableBlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld_BlockPosPool {

    @Unique
    private PooledMutableBlockPos featherOpt$checkLightFor$blockPos;
    @Unique
    private int featherOpt$checkLightFor$x;
    @Unique
    private int featherOpt$checkLightFor$y;
    @Unique
    private int featherOpt$checkLightFor$z;
    @Unique
    private BlockPos featherOpt$rawLight$originalBlockPos;
    @Unique
    private PooledMutableBlockPos featherOpt$rawLight$blockPos;

    @Redirect(method = "getCollisionBoxes", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$getCollisionBoxes$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(method = "getCollisionBoxes", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void featherOpt$getCollisionBoxes$returnToPool(AxisAlignedBB p_getCollisionBoxes_1_, CallbackInfoReturnable<List<AxisAlignedBB>> cir, List list, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Redirect(method = "isAnyLiquid", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$isAnyLiquid$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(method = "isAnyLiquid", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void featherOpt$isAnyLiquid$returnToPool(AxisAlignedBB p_isAnyLiquid_1_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Redirect(method = "isFlammableWithin", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$isFlammableWithin$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(
            method = "isFlammableWithin", at = @At(value = "RETURN", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void featherOpt$isFlammableWithin$returnToPool$first(AxisAlignedBB p_isFlammableWithin_1_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Inject(
            method = "isFlammableWithin", at = @At(value = "RETURN", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void featherOpt$isFlammableWithin$returnToPool$second(AxisAlignedBB p_isFlammableWithin_1_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Redirect(method = "handleMaterialAcceleration", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$handleMaterialAcceleration$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(method = "handleMaterialAcceleration", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;lengthVector()D"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void featherOpt$handleMaterialAcceleration$returnToPool(AxisAlignedBB p_handleMaterialAcceleration_1_, Material p_handleMaterialAcceleration_2_, Entity p_handleMaterialAcceleration_3_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int m, int n, boolean x, Vec3 vec3, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Redirect(method = "isMaterialInBB", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$isMaterialInBB$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(method = "isMaterialInBB", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void featherOpt$isMaterialInBB$returnToPool(AxisAlignedBB p_isMaterialInBB_1_, Material p_isMaterialInBB_2_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Redirect(method = "isAABBInMaterial", at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos"))
    private BlockPos.MutableBlockPos featherOpt$isAABBInMaterial$usePooled() {
        return PooledMutableBlockPos.get();
    }

    @Inject(method = "isAABBInMaterial", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void featherOpt$isAABBInMaterial$returnToPool(AxisAlignedBB p_isAABBInMaterial_1_, Material p_isAABBInMaterial_2_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Inject(method = "isAABBInMaterial", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void featherOpt$getRawLight$returnToPool(AxisAlignedBB p_isAABBInMaterial_1_, Material p_isAABBInMaterial_2_, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int i1, int j1, BlockPos.MutableBlockPos blockPos) {
        ((PooledMutableBlockPos) blockPos).release();
    }

    @Inject(
            method = "getRawLight",
            at = @At(value = "CONSTANT", args = "intValue=0", ordinal = 2),
            cancellable = true
    )
    private void featherOpt$getRawLight$getFromPool(BlockPos blockPos, EnumSkyBlock enumSkyBlock, CallbackInfoReturnable<Integer> cir) {
        this.featherOpt$rawLight$originalBlockPos = blockPos;
        this.featherOpt$rawLight$blockPos = PooledMutableBlockPos.get();
    }

    @Redirect(
            method = "getRawLight",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$getRawLight$usePooled(BlockPos instance, EnumFacing direction) {
        return featherOpt$rawLight$blockPos.set(featherOpt$rawLight$originalBlockPos).move(direction);
    }

    @Inject(
            method = "getRawLight",
            at = @At("RETURN"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getLightFor(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/BlockPos;)I")
            )
    )
    private void featherOpt$getRawLight$returnToPool(BlockPos blockPos, EnumSkyBlock p_getRawLight_2_, CallbackInfoReturnable<Integer> cir) {
        featherOpt$rawLight$blockPos.release();
    }

    @Inject(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 0)
    )
    private void featherOpt$checkLightFor$usePooled$init1(EnumSkyBlock p_checkLightFor_1_, BlockPos p_checkLightFor_2_, CallbackInfoReturnable<Boolean> cir) {
        this.featherOpt$checkLightFor$blockPos = PooledMutableBlockPos.get();
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos", ordinal = 0),
            require = 1
    )
    private BlockPos featherOpt$checkLightFor$usePooled$set(int x, int y, int z) {
        return featherOpt$checkLightFor$blockPos.set(x, y, z);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos", ordinal = 1)
    )
    private BlockPos featherOpt$checkLightFor$usePooled$init2(int x, int y, int z) {
        this.featherOpt$checkLightFor$x = x;
        this.featherOpt$checkLightFor$y = y;
        this.featherOpt$checkLightFor$z = z;
        return featherOpt$checkLightFor$blockPos.set(x, y, z);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;west()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$west(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;east()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$east(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;down()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$down(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;up()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$up(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;north()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$north(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Redirect(
            method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;south()Lnet/minecraft/util/BlockPos;")
    )
    private BlockPos featherOpt$checkLightFor$usePooled$directions$south(BlockPos instance) {
        return ((PooledMutableBlockPos) instance).set(featherOpt$checkLightFor$x, featherOpt$checkLightFor$y, featherOpt$checkLightFor$z).move(EnumFacing.WEST);
    }

    @Inject(method = "checkLightFor", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 1))
    public void featherOpt$checkLightFor$release(EnumSkyBlock p_checkLightFor_1_, BlockPos p_checkLightFor_2_, CallbackInfoReturnable<Boolean> cir) {
        featherOpt$checkLightFor$blockPos.release();
    }
}
