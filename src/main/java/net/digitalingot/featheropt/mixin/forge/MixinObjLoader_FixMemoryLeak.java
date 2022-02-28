package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.ResourcesHook;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backport from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/cf4325058401d20bd73fdfa49cb2d677aa644cd5
 *
 * @author ichttt
 */
@Mixin(value = OBJLoader.class, remap = false)
public class MixinObjLoader_FixMemoryLeak {

    @Unique
    private IResource featherOpt$caughtResource;

    @ModifyArg(
            method = "loadModel",
            at = @At(value = "NEW", target = "Lnet/minecraftforge/client/model/obj/OBJModel$Parser;<init>(Lnet/minecraft/client/resources/IResource;Lnet/minecraft/client/resources/IResourceManager;)V"),
            index = 0
    )
    private IResource featherOpt$catchLeakedResource(IResource from) {
        this.featherOpt$caughtResource = from;
        return from;
    }

    @Inject(
            method = "loadModel",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
    )
    public void featherOpt$closeResource(ResourceLocation resource, CallbackInfoReturnable<IModel> cir) {
        ResourcesHook.closeResource(this.featherOpt$caughtResource);
    }

}
