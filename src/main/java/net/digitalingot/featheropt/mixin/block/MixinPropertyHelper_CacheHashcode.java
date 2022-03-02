package net.digitalingot.featheropt.mixin.block;

import net.digitalingot.featheropt.helpers.ICachedHashcode;
import net.minecraft.block.properties.PropertyHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PropertyHelper.class)
public class MixinPropertyHelper_CacheHashcode implements ICachedHashcode {

    @Unique
    private int featherOpt$cachedHashcode;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void featherOpt$cacheHashcode(String p_i45652_1_, Class<?> p_i45652_2_, CallbackInfo ci) {
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

    @Override
    public int getCachedHashcode() {
        return this.featherOpt$cachedHashcode;
    }
}
