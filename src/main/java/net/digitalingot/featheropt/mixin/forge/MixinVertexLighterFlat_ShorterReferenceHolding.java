package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.IBlockInfo;
import net.digitalingot.featheropt.helpers.IVertexLighterFlat;
import net.minecraftforge.client.model.pipeline.BlockInfo;
import net.minecraftforge.client.model.pipeline.QuadGatheringTransformer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/90bf8dd95dd3b7955896d3268d0ec13d06a7575f
 *
 * @author bs2609
 */
@Mixin(value = VertexLighterFlat.class, remap = false)
public abstract class MixinVertexLighterFlat_ShorterReferenceHolding extends QuadGatheringTransformer implements IVertexLighterFlat {

    @Shadow
    @Final
    protected BlockInfo blockInfo;

    @Override
    public void resetBlockInfo() {
        ((IBlockInfo) this.blockInfo).reset();
    }
}
