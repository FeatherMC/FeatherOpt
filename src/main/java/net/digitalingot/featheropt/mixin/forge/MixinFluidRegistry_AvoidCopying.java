package net.digitalingot.featheropt.mixin.forge;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.Set;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/5a48ca99b6787c7f811045d1f98b26db6ce073b7
 *
 * @author mezzz
 */
@Mixin(FluidRegistry.class)
public abstract class MixinFluidRegistry_AvoidCopying {
    @Shadow(remap = false)
    static Set<Fluid> bucketFluids;

    /**
     * @reason Avoid copying the fluid set
     * @author mezz
     */
    @Overwrite(remap = false)
    public static Set<Fluid> getBucketFluids() {
        return Collections.unmodifiableSet(bucketFluids);
    }
}
