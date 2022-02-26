package net.digitalingot.featheropt.mixin.forge;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Minecraft Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/08c00662a0c236e80a9938621efd516d52f72b0d
 *
 * @author RainWarrior
 */
@Mixin(FMLClientHandler.class)
public class MixinFMLClientHandler_RemoveModAsResource {

    @Redirect(
            method = "addModAsResource",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/registry/LanguageRegistry;loadLanguagesFor(Lnet/minecraftforge/fml/common/ModContainer;Lnet/minecraftforge/fml/relauncher/Side;)V"),
            remap = false
    )
    private void feather$fixModAsResource(LanguageRegistry instance, ModContainer container, Side side) {
    }
}
