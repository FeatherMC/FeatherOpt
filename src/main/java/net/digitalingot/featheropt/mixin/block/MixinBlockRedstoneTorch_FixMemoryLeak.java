package net.digitalingot.featheropt.mixin.block;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/ef1efaffec9161c93c29316be1c5cae242f0582a
 *
 * @author bs2609
 */
@Mixin(BlockRedstoneTorch.class)
public class MixinBlockRedstoneTorch_FixMemoryLeak {

    @Shadow
    private static Map<World, List<?>> toggles;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void featherOpt$useWeakMap(CallbackInfo ci) {
        toggles = new WeakHashMap<>();
    }
}
