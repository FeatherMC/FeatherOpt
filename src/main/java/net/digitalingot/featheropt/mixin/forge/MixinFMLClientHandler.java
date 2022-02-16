package net.digitalingot.featheropt.mixin.forge;

import com.google.common.base.CharMatcher;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Forge 1.12.2
 */
@Mixin(FMLClientHandler.class)
public class MixinFMLClientHandler {
    @Unique
    private static final CharMatcher DISALLOWED_CHAR_MATCHER = CharMatcher.anyOf("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000").negate();

    /**
     * Backported from Minecraft Forge 1.12.2:
     * https://github.com/MinecraftForge/MinecraftForge/commit/14d8151b026c9c4e89b7004d52190184269e3287
     *
     * @reason Reduce memory pressure from new objects during loading screen
     * @author mezz
     */
    @Overwrite(remap = false)
    public String stripSpecialChars(String message) {
        return DISALLOWED_CHAR_MATCHER.removeFrom(StringUtils.stripControlCodes(message));
    }

    /**
     * Backported from Minecraft Forge 1.12.2:
     * https://github.com/MinecraftForge/MinecraftForge/commit/08c00662a0c236e80a9938621efd516d52f72b0d
     *
     * @author RainWarrior
     */
    @Redirect(
            method = "addModAsResource",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/registry/LanguageRegistry;loadLanguagesFor(Lnet/minecraftforge/fml/common/ModContainer;Lnet/minecraftforge/fml/relauncher/Side;)V"),
            remap = false
    )
    private void feather$fixModAsResource(LanguageRegistry instance, ModContainer container, Side side) {
    }
}
