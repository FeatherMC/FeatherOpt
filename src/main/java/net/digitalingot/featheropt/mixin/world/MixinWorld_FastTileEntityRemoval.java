package net.digitalingot.featheropt.mixin.world;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;
import net.digitalingot.featheropt.helpers.IWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Predicate;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/pull/4281/files
 *
 * @author mezz
 */
@Mixin(World.class)
public abstract class MixinWorld_FastTileEntityRemoval implements IWorld {

    @Shadow
    @Final
    public List<TileEntity> tickableTileEntities;
    @Shadow
    @Final
    public List<TileEntity> loadedTileEntityList;
    @Unique
    private TLongCollection featherOpt$tileEntitiesChunkToBeRemoved = new TLongHashSet();

    @Inject(method = "updateEntities",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;processingLoadedTiles:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.BEFORE))
    public void featherOpt$updateEntities$triggerRemoval(CallbackInfo ci) {
        this.removeTileEntitiesForRemovedChunks();
    }

    @Override
    public void markTileEntitiesInChunkForRemoval(Chunk chunk) {
        if (!chunk.getTileEntityMap().isEmpty()) {
            long pos = ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
            this.featherOpt$tileEntitiesChunkToBeRemoved.add(pos);
        }
    }

    @Override
    public void removeTileEntitiesForRemovedChunks() {
        if (this.featherOpt$tileEntitiesChunkToBeRemoved.isEmpty()) {
            return;
        }

        Predicate<TileEntity> isInChunk = (tileEntity) -> {
            BlockPos tilePos = tileEntity.getPos();
            long tileChunkPos = ChunkCoordIntPair.chunkXZ2Int(tilePos.getX() >> 4, tilePos.getZ() >> 4);
            return this.featherOpt$tileEntitiesChunkToBeRemoved.contains(tileChunkPos);
        };
        Predicate<TileEntity> isInChunkDoUnload = (tileEntity) -> {
            boolean inChunk = isInChunk.test(tileEntity);
            if (inChunk) {
                tileEntity.onChunkUnload();
            }
            return inChunk;
        };
        this.tickableTileEntities.removeIf(isInChunk);
        this.loadedTileEntityList.removeIf(isInChunkDoUnload);
        this.featherOpt$tileEntitiesChunkToBeRemoved.clear();
    }
}
