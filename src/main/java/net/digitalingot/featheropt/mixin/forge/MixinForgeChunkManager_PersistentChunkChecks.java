package net.digitalingot.featheropt.mixin.forge;

import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/3b99c8063655f56fa6fd1a5e75b7e2f4924b69fa
 *
 * @author bs2609
 */
@Mixin(ForgeChunkManager.class)
public class MixinForgeChunkManager_PersistentChunkChecks {

    @Shadow(remap = false)
    private static Map<World, ImmutableSetMultimap<ChunkCoordIntPair, ForgeChunkManager.Ticket>> forcedChunks;

    @Inject(method = "<clinit>", at = @At("RETURN"), remap = false)
    private static void featherOpt$init(CallbackInfo ci) {
        forcedChunks = Collections.synchronizedMap(new WeakHashMap<>());
    }

    @Inject(method = "unloadWorld", at = @At("HEAD"), remap = false)
    private static void featherOpt$unloadWorld(World world, CallbackInfo ci) {
        forcedChunks.remove(world);
    }

    /**
     * @author bs2609
     * @reason Instead of using Map#containsKey, use a null-check as well as check if the world is remote.
     */
    @Overwrite(remap = false)
    public static ImmutableSetMultimap<ChunkCoordIntPair, ForgeChunkManager.Ticket> getPersistentChunksFor(World world) {
        if (world.isRemote) {
            return ImmutableSetMultimap.of();
        }

        ImmutableSetMultimap<ChunkCoordIntPair, ForgeChunkManager.Ticket> persistentChunks = forcedChunks.get(world);
        return persistentChunks != null ? persistentChunks : ImmutableSetMultimap.of();
    }
}
