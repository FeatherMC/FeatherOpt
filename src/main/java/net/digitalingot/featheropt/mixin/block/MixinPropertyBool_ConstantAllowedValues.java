package net.digitalingot.featheropt.mixin.block;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyBool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PropertyBool.class)
public class MixinPropertyBool_ConstantAllowedValues {

    @Unique
    private static final ImmutableSet<Boolean> FEATHER_OPT$ALLOWED_VALUES = ImmutableSet.of(true, false);

    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSet;of(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;", remap = false)
    )
    private ImmutableSet<Boolean> featherOpt$useCached(Object first, Object second) {
        return FEATHER_OPT$ALLOWED_VALUES;
    }

}
