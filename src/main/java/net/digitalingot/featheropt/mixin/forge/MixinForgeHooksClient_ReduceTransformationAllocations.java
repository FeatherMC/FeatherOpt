package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.TRSRTransformationHook;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.TRSRTransformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/0b5a6a3b031816ca46d4bc405a0062781dec914b
 *
 * @author bs2609
 */
@Mixin(value = ForgeHooksClient.class, remap = false)
public class MixinForgeHooksClient_ReduceTransformationAllocations {

    @Redirect(
            method = "applyTransform(Lnet/minecraft/client/renderer/block/model/ItemTransformVec3f;Lcom/google/common/base/Optional;)Lcom/google/common/base/Optional;",
            at = @At(value = "NEW", target = "Lnet/minecraftforge/client/model/TRSRTransformation;<init>(Lnet/minecraft/client/renderer/block/model/ItemTransformVec3f;)V")
    )
    private static TRSRTransformation featherOpt$applyItemTransform$cacheTransformations(ItemTransformVec3f transform) {
        return TRSRTransformationHook.from(transform);
    }
}
