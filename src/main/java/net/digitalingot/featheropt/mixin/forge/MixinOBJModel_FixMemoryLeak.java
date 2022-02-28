package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJModel;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/ea26df0174b2f9338cab0846366d93299f691df7
 *
 * @author bs2609
 */
@Mixin(value = OBJModel.MaterialLibrary.class, remap = false)
public class MixinOBJModel_FixMemoryLeak {

    @Shadow
    private InputStreamReader mtlStream;

    @Shadow
    private BufferedReader mtlReader;

    @Inject(method = "parseMaterials", at = @At("TAIL"))
    public void featherOpt$closeStream(IResourceManager color, String mapStrings, ResourceLocation texturePath, CallbackInfo ci) {
        IOUtils.closeQuietly(this.mtlStream);
        IOUtils.closeQuietly(this.mtlReader);
    }
}
