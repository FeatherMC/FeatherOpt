package net.digitalingot.featheropt.mixin.renderer;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/0d34b02f8821bf114a7ee8e298af6263e7abb8d0
 *
 * @author bs2609
 */
@Mixin(VertexFormat.class)
public class MixinVertexFormat_CacheHashcode {

    @Shadow
    @Final
    private List<VertexFormatElement> elements;
    @Shadow
    @Final
    private List<Integer> offsets;
    @Shadow
    private int nextOffset;

    @Unique
    private int featherOpt$cachedHashCode;

    @Inject(method = "addElement", at = @At(value = "RETURN", ordinal = 1))
    private void featherOpt$resetHashCode(VertexFormatElement element, CallbackInfoReturnable<VertexFormat> cir) {
        featherOpt$cachedHashCode = 0;
    }

    /**
     * @author bs2609
     * @reason Cache hashCode()
     */
    @Overwrite(remap = false)
    public int hashCode() {
        if (this.featherOpt$cachedHashCode != 0) return this.featherOpt$cachedHashCode;

        int i = this.elements.hashCode();
        i = 31 * i + this.offsets.hashCode();
        i = 31 * i + this.nextOffset;
        featherOpt$cachedHashCode = i;
        return i;
    }


}
