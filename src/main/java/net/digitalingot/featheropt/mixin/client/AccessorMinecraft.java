package net.digitalingot.featheropt.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface AccessorMinecraft {

    @Accessor("enableGLErrorChecking")
    void setEnableGLErrorChecking(boolean enable);

}
