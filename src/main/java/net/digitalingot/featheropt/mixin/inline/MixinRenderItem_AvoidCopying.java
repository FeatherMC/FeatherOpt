package net.digitalingot.featheropt.mixin.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderItem.class)
public class MixinRenderItem_AvoidCopying {

    @Redirect(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing[] featherOpt$renderModel$getCachedArray() {
        return Constants.FACINGS;
    }

}
