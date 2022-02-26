package net.digitalingot.featheropt.mixin.world;

import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/3b99c8063655f56fa6fd1a5e75b7e2f4924b69fa
 *
 * @author bs2609
 */
@Mixin(World.class)
public class MixinWorld_PersistentChunkChecks {

    @Shadow
    @Final
    public boolean isRemote;

    @Redirect(method = "updateEntityWithOptionalForce", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableSetMultimap;containsKey(Ljava/lang/Object;)Z", remap = false))
    public boolean featherOpt$containsKey(ImmutableSetMultimap<?, ?> multimap, Object key) {
        return !isRemote && multimap.containsKey(key);
    }
}
