package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.Constants;
import net.digitalingot.featheropt.helpers.IBlockInfo;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.BlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/90bf8dd95dd3b7955896d3268d0ec13d06a7575f
 *
 * @author bs2609 & others
 */
@Mixin(BlockInfo.class)
public class MixinBlockInfoCond implements IBlockInfo {
    @Shadow(remap = false)
    private IBlockAccess world;
    @Shadow(remap = false)
    private Block block;
    @Shadow(remap = false)
    private BlockPos blockPos;

    @Shadow(remap = false)
    private int cachedTint;
    @Shadow(remap = false)
    private int cachedMultiplier;

    @Shadow(remap = false)
    private float shz;
    @Shadow(remap = false)
    private float shy;
    @Shadow(remap = false)
    private float shx;
    @Unique
    private final int[] featherOpt$packed = new int[7];
    @Unique
    private boolean featherOpt$full;

    @Override
    public void updateFlatLighting() {
        this.featherOpt$full = this.block.isFullCube();
        this.featherOpt$packed[0] = this.block.getMixedBrightnessForBlock(this.world, this.blockPos);

        for (EnumFacing side : Constants.FACINGS) {
            int i = side.ordinal() + 1;
            this.featherOpt$packed[i] = this.block.getMixedBrightnessForBlock(this.world, this.blockPos.offset(side));
        }
    }

    @Override
    public void reset() {
        this.world = null;
        this.block = null;
        this.blockPos = null;
        cachedTint = -1;
        cachedMultiplier = -1;
        shx = shy = shz = 0;
    }

    @Override
    public int[] getPackedLight() {
        return this.featherOpt$packed;
    }

    @Override
    public boolean isFull() {
        return this.featherOpt$full;
    }
}
