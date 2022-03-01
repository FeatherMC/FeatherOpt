package net.digitalingot.featheropt.mixin.renderer;

import net.digitalingot.featheropt.helpers.AssociatedMutableBlockPos;
import net.digitalingot.featheropt.helpers.PooledMutableBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockModelRenderer.AmbientOcclusionFace.class)
public class MixinBlockModelRenderer_BlockPosPool {

    @ModifyVariable(
            method = "updateVertexBrightness",
            at = @At("HEAD"),
            argsOnly = true
    )
    public BlockPos featherOpt$getFromPool$first(BlockPos instance) {
        return AssociatedMutableBlockPos.get(instance);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 0
            )
    )
    public BlockPos featherOpt$usePool$first$ternaryTrue(BlockPos instance, EnumFacing offset) {
        return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos().move(offset);
    }

    @ModifyVariable(
            method = "updateVertexBrightness",
            at = @At(value = "STORE", ordinal = 1),
            ordinal = 1
    )
    public BlockPos featherOpt$usePool$first$ternaryFalse(BlockPos instance) {
        if (instance instanceof AssociatedMutableBlockPos) {
            return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos();
        } else {
            return instance;
        }
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"),
            slice = @Slice(
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 2, shift = At.Shift.AFTER)
            )
    )
    public BlockPos featherOpt$getFromPool$secondAndThird(BlockPos instance, EnumFacing offset) {
        return AssociatedMutableBlockPos.get(instance).move(offset);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 2, shift = At.Shift.AFTER),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMixedBrightnessForBlock(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I", ordinal = 0)
            )
    )
    public BlockPos featherOpt$getFromPool$forthAndFifth(BlockPos instance, EnumFacing offset) {
        return PooledMutableBlockPos.get(instance).move(offset);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 4, shift = At.Shift.AFTER),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 6, shift = At.Shift.AFTER)
            )
    )
    public BlockPos featherOpt$getBlockState$offset$secondAndThird(BlockPos instance, EnumFacing direction) {
        return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos().move(direction);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 6, shift = At.Shift.AFTER),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 8, shift = At.Shift.AFTER)
            )
    )
    public BlockPos featherOpt$getBlockState$offset$fourthAndFifth(BlockPos instance, EnumFacing direction) {
        return ((PooledMutableBlockPos) instance).move(direction);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/IBlockAccess;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/IBlockAccess;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 6, shift = At.Shift.BEFORE),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/world/IBlockAccess;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 7, shift = At.Shift.AFTER)
            )
    )
    public IBlockState featherOpt$getBlockState$releaseFourthAndFifth(IBlockAccess instance, BlockPos blockPos) {
        try (PooledMutableBlockPos pos = ((PooledMutableBlockPos) blockPos)) {
            return instance.getBlockState(pos);
        }
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 8, shift = At.Shift.AFTER),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;", ordinal = 12, shift = At.Shift.AFTER)
            )
    )
    public BlockPos featherOpt$conditionalGetBlockState(BlockPos instance, EnumFacing direction) {
        return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos().move(direction);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMixedBrightnessForBlock(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getAmbientOcclusionLightValue()F", ordinal = 5, shift = At.Shift.BEFORE)
            )
    )
    public int featherOpt$getMixedBrightnessForBlock$releaseSecond(Block instance, IBlockAccess p_getMixedBrightnessForBlock_1_, BlockPos associatedBlockPos) {
        try (AssociatedMutableBlockPos blockPos = ((AssociatedMutableBlockPos.Companion) associatedBlockPos).getParent()) {
            return instance.getMixedBrightnessForBlock(p_getMixedBrightnessForBlock_1_, blockPos);
        }
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMixedBrightnessForBlock(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getAmbientOcclusionLightValue()F", ordinal = 7, shift = At.Shift.BEFORE)
            )
    )
    public int featherOpt$getMixedBrightnessForBlock$releaseThird(Block instance, IBlockAccess p_getMixedBrightnessForBlock_1_, BlockPos associatedBlockPos) {
        try (AssociatedMutableBlockPos blockPos = ((AssociatedMutableBlockPos.Companion) associatedBlockPos).getParent()) {
            return instance.getMixedBrightnessForBlock(p_getMixedBrightnessForBlock_1_, blockPos);
        }
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/IBlockAccess;getBlockState(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 1),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getAmbientOcclusionLightValue()F", ordinal = 7, shift = At.Shift.AFTER)
            )
    )
    public IBlockState featherOpt$getMixedBrightnessForBlock$useFirst(IBlockAccess instance, BlockPos associatedBlockPos) {
        return instance.getBlockState(associatedBlockPos);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/BlockPos;offset(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMixedBrightnessForBlock(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I", ordinal = 8, shift = At.Shift.AFTER)
            )
    )
    public BlockPos featherOpt$getMixedBrightnessForBlock$useFirst(BlockPos instance, EnumFacing direction) {
        return ((AssociatedMutableBlockPos) instance).associateWithOwnBlockPos().move(direction);
    }

    @Redirect(
            method = "updateVertexBrightness",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getMixedBrightnessForBlock(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)I", ordinal = 9)
    )
    public int featherOpt$getMixedBrightnessForBlock$releaseFirst(Block instance, IBlockAccess p_getMixedBrightnessForBlock_1_, BlockPos blockPos) {
        AssociatedMutableBlockPos parent = ((AssociatedMutableBlockPos.Companion) blockPos).getParent();
        try {
            return instance.getMixedBrightnessForBlock(p_getMixedBrightnessForBlock_1_, blockPos);
        } finally {
            parent.release();
        }
    }
}
