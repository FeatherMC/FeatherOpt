package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.ResourcesHook;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.b3d.B3DLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

/**
 * Backport from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/cf4325058401d20bd73fdfa49cb2d677aa644cd5
 *
 * @author ichttt
 */
@Mixin(value = B3DLoader.class, remap = false)
public class MixinB3DLoader_FixMemoryLeak {

    @Unique
    private IResource featherOpt$caughtResource;

    @Redirect(
            method = "loadModel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/IResource;getInputStream()Ljava/io/InputStream;")
    )
    private InputStream featherOpt$catchLeakedResource(IResource from) {
        this.featherOpt$caughtResource = from;
        return from.getInputStream();
    }

    @Inject(
            method = "loadModel",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    public void featherOpt$closeResource(ResourceLocation resource, CallbackInfoReturnable<IModel> cir) {
        ResourcesHook.closeResource(this.featherOpt$caughtResource);
    }

}
