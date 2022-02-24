package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.IVertexLighterFlat;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.pipeline.ForgeBlockModelRenderer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/90bf8dd95dd3b7955896d3268d0ec13d06a7575f
 *
 * @author bs2609
 */
@Mixin(ForgeBlockModelRenderer.class)
public class MixinForgeBlockModelRendererCond {

    @Inject(method = "render", at = @At("RETURN"), remap = false)
    private static void feather$clean(VertexLighterFlat lighter, IBlockAccess quad, IBakedModel side, Block block, BlockPos world, WorldRenderer model, boolean checkSides, CallbackInfoReturnable<Boolean> cir) {
        ((IVertexLighterFlat) lighter).resetBlockInfo();
    }
}
