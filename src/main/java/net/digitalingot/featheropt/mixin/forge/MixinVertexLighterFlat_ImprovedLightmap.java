package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.IBlockInfo;
import net.digitalingot.featheropt.helpers.IVertexLighterFlat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.BlockInfo;
import net.minecraftforge.client.model.pipeline.QuadGatheringTransformer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/8d938660e7a8e504c731ab29c70945c5220db34b
 *
 * @author bs2609
 */
@Mixin(VertexLighterFlat.class)
public abstract class MixinVertexLighterFlat_ImprovedLightmap extends QuadGatheringTransformer implements IVertexLighterFlat {
    @Shadow(remap = false)
    @Final
    protected BlockInfo blockInfo;

    /**
     * Backported from Forge 1.12.2:
     *
     * @author bs2609
     * @reason Cached Lightmap
     */
    @Overwrite(remap = false)
    protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
        IBlockInfo blockInfo = (IBlockInfo) this.blockInfo;
        boolean full = blockInfo.isFull();

        EnumFacing side = null;
        if ((full || y < -0.99F) && normal[1] < -0.95F) side = EnumFacing.DOWN;
        else if ((full || y > 0.99F) && normal[1] > 0.95F) side = EnumFacing.UP;
        else if ((full || z < -0.99F) && normal[2] < -0.95F) side = EnumFacing.NORTH;
        else if ((full || z > 0.99F) && normal[2] > 0.95F) side = EnumFacing.SOUTH;
        else if ((full || x < -0.99F) && normal[0] < -0.95F) side = EnumFacing.WEST;
        else if ((full || x > 0.99F) && normal[0] > 0.95F) side = EnumFacing.EAST;

        int i = side == null ? 0 : side.ordinal() + 1;
        int brightness = blockInfo.getPackedLight()[i];
        lightmap[0] = (float) (brightness >> 4 & 15) * 32.0F / 65535.0F;
        lightmap[1] = (float) (brightness >> 20 & 15) * 32.0F / 65535.0F;
    }

    /**
     * Backported from Forge 1.12.2:
     * https://github.com/MinecraftForge/MinecraftForge/commit/8d938660e7a8e504c731ab29c70945c5220db34b
     *
     * @author bs2609
     */
    @Inject(method = "updateBlockInfo", at = @At("TAIL"), remap = false)
    public void featherOpt$updateBlockInfo(CallbackInfo ci) {
        ((IBlockInfo) this.blockInfo).updateFlatLighting();
    }

    /**
     * Backported from Forge 1.12.2:
     * https://github.com/MinecraftForge/MinecraftForge/commit/90bf8dd95dd3b7955896d3268d0ec13d06a7575f
     *
     * @author bs2609
     */
    @Override
    public void resetBlockInfo() {
        ((IBlockInfo) this.blockInfo).reset();
    }
}
