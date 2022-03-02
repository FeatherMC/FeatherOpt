package net.digitalingot.featheropt.mixin.block;

import net.digitalingot.featheropt.helpers.ICachedHashcode;
import net.minecraft.block.properties.PropertyInteger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PropertyInteger.class)
public class MixinPropertyInteger_CacheHashcode {

    /**
     * @author FeatherOpt
     * @reason Cache hashcode
     *
     * {@link PropertyInteger} is a subclass of {@link net.minecraft.block.properties.PropertyHelper}, so we don't have to cache
     * the hashcode ourselves.
     */
    @Overwrite
    public int hashCode() {
        return ((ICachedHashcode) this).getCachedHashcode();
    }

}
