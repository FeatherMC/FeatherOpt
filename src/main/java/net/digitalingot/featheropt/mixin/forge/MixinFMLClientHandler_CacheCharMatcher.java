package net.digitalingot.featheropt.mixin.forge;

import com.google.common.base.CharMatcher;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

/**
 * Backported from Minecraft Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/14d8151b026c9c4e89b7004d52190184269e3287
 *
 * @author mezz
 */
@Mixin(FMLClientHandler.class)
public class MixinFMLClientHandler_CacheCharMatcher {
    @Unique
    private static final CharMatcher FEATHER_OPT$DISALLOWED_CHAR_MATCHER = CharMatcher.anyOf("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000").negate();

    /**
     * @reason Reduce memory pressure from new objects during loading screen
     * @author mezz
     */
    @Overwrite(remap = false)
    public String stripSpecialChars(String message) {
        return FEATHER_OPT$DISALLOWED_CHAR_MATCHER.removeFrom(StringUtils.stripControlCodes(message));
    }
}
