package net.digitalingot.featheropt.mixin.entity;

import net.digitalingot.featheropt.helpers.PooledMutableBlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity_BlockPosPool {

    @Shadow
    public World worldObj;
    @Shadow
    protected boolean inWater;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float height;

    @Unique
    private PooledMutableBlockPos featherOpt$opaqueCheck$blockPos;
    @Unique
    private PooledMutableBlockPos featherOpt$blockPosTemp;
    @Unique
    private PooledMutableBlockPos featherOpt$blockPos1;
    @Unique
    private PooledMutableBlockPos featherOpt$blockPos2;

    @Redirect(
            method = "doBlockCollisions",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos", ordinal = 0)
    )
    private BlockPos featherOpt$getFromPool$first(double x, double y, double z) {
        this.featherOpt$blockPosTemp = PooledMutableBlockPos.get();
        this.featherOpt$blockPos1 = PooledMutableBlockPos.get(x, y, z);
        return this.featherOpt$blockPos1;
    }

    @Redirect(
            method = "doBlockCollisions",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos", ordinal = 1)
    )
    private BlockPos featherOpt$getFromPool$second(double x, double y, double z) {
        this.featherOpt$blockPos2 = PooledMutableBlockPos.get(x, y, z);
        return this.featherOpt$blockPos2;
    }


    @Inject(
            method = "doBlockCollisions",
            at = @At("HEAD")
    )
    private void featherOpt$getFromPool$new(CallbackInfo ci) {
        this.featherOpt$blockPos1 = PooledMutableBlockPos.get();
    }

    @Redirect(
            method = "doBlockCollisions",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos", ordinal = 2)
    )
    private BlockPos featherOpt$getFromPool$usePooled(int x, int y, int z) {
        return this.featherOpt$blockPosTemp.set(x, y, z);
    }

    @Inject(method = "doBlockCollisions", at = @At("TAIL"))
    private void featherOpt$returnToPool(CallbackInfo ci) {
        this.featherOpt$blockPos1.release();
        this.featherOpt$blockPos2.release();
        this.featherOpt$blockPosTemp.release();
    }

    /**
     * @author FeatherOpt
     * @reason Use pooled block positions
     */
    @Overwrite
    public boolean isWet() {
        try (PooledMutableBlockPos blockPos = PooledMutableBlockPos.get(this.posX, this.posY, this.posZ)) {
            return this.inWater || this.worldObj.isRainingAt(blockPos) || this.worldObj.isRainingAt(blockPos.set(this.posX, this.posY + (double) this.height, this.posZ));
        }
    }

    @Redirect(
            method = "isEntityInsideOpaqueBlock",
            at = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos")
    )
    public BlockPos.MutableBlockPos featherOpt$getFromPool(int x, int y, int z) {
        this.featherOpt$opaqueCheck$blockPos = PooledMutableBlockPos.get(x, y, z);
        return this.featherOpt$opaqueCheck$blockPos;
    }

    @Inject(
            method = "isEntityInsideOpaqueBlock",
            at = @At("RETURN"),
            slice = @Slice(
                    from = @At(value = "NEW", target = "net/minecraft/util/BlockPos$MutableBlockPos")
            )
    )
    private void featherOpt$isEntityInsideOpaqueBlock$returnToPool(CallbackInfoReturnable<Boolean> cir) {
        this.featherOpt$opaqueCheck$blockPos.release();
    }
}
