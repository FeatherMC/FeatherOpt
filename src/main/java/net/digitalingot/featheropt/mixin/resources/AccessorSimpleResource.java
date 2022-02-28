package net.digitalingot.featheropt.mixin.resources;

import net.minecraft.client.resources.SimpleResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.InputStream;

@Mixin(SimpleResource.class)
public interface AccessorSimpleResource {

    @Accessor("mcmetaInputStream")
    InputStream featherOpt$getMcmetaInputStream();

}
