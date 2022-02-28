package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.client.GuiModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/3a62514e60b16873cd87a36119db7fbeb60eedc2
 *
 * @author bs2609
 */
@Mixin(value = GuiModList.class, remap = false)
public class MixinGuiModList_FixMemoryLeak {

    @Redirect(
            method = "updateCache",
            at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;", remap = false)
    )
    public BufferedImage featherOpt$closeStream(InputStream input) throws IOException {
        return TextureUtil.readBufferedImage(input);
    }

}
