package net.digitalingot.featheropt.helpers;

import net.minecraft.world.chunk.Chunk;

public interface IWorld {

    void markTileEntitiesInChunkForRemoval(Chunk chunk);

    void removeTileEntitiesForRemovedChunks();

}
