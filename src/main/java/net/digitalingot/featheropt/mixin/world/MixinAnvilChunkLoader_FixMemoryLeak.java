package net.digitalingot.featheropt.mixin.world;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/3a62514e60b16873cd87a36119db7fbeb60eedc2
 *
 * @author bs2609
 */
@Mixin(AnvilChunkLoader.class)
public class MixinAnvilChunkLoader_FixMemoryLeak {

    @Redirect(
            method = "loadChunk__Async",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompressedStreamTools;read(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NBTTagCompound;")
    )
    public NBTTagCompound featherOpt$closeStream(DataInputStream inputStream) throws IOException {
        NBTTagCompound read = CompressedStreamTools.read(inputStream);
        inputStream.close();
        return read;
    }

}
