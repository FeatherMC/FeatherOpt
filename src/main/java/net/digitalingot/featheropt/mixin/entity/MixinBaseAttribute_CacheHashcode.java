package net.digitalingot.featheropt.mixin.entity;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseAttribute.class)
public class MixinBaseAttribute_CacheHashcode {

    @Unique
    private int featherOpt$cachedHashcode;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void featherOpt$cacheHashcode(IAttribute p_i45892_1_, String p_i45892_2_, double p_i45892_3_, CallbackInfo ci) {
        this.featherOpt$cachedHashcode = this.hashCode();
    }

    /**
     * @author FeatherOpt
     * @reason Cache hashcode
     */
    @Overwrite
    public int hashCode() {
        return this.featherOpt$cachedHashcode;
    }

}
