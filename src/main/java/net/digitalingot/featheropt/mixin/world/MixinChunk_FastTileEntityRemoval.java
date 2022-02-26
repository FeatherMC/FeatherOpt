package net.digitalingot.featheropt.mixin.world;

import net.digitalingot.featheropt.helpers.IWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/pull/4281/files
 *
 * @author mezz
 */
@Mixin(Chunk.class)
public class MixinChunk_FastTileEntityRemoval {

    @Shadow
    @Final
    private World worldObj;

    @Redirect(method = "onChunkUnload", at = @At(value = "INVOKE", target = "Ljava/util/Map;values()Ljava/util/Collection;", remap = false, ordinal = 0))
    private Collection<TileEntity> featherOpt$(Map<BlockPos, TileEntity> instance) {
        ((IWorld) this.worldObj).markTileEntitiesInChunkForRemoval((Chunk) (Object) this);

        // don't iterate
        return Collections.emptyList();
    }

}
