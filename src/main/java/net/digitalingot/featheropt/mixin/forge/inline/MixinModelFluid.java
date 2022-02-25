package net.digitalingot.featheropt.mixin.forge.inline;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ModelFluid.BakedFluid.class, remap = false)
public class MixinModelFluid {

    @Redirect(method = "<init>(Lcom/google/common/base/Optional;Lcom/google/common/collect/ImmutableMap;Lnet/minecraft/client/renderer/vertex/VertexFormat;ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZZ[II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;values()[Lnet/minecraft/util/EnumFacing;"))
    public EnumFacing[] featherOpt$getCachedArray() {
        return Constants.FACINGS;
    }

}
