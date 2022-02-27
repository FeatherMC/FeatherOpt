package net.digitalingot.featheropt.mixin.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/50014c7ca446616e53ea6b61d513465780aac8f0
 *
 * @author sfPlayer1
 */
@Mixin(World.class)
public abstract class MixinWorld_ReducedLightCheckRangeRequirements {

    @Shadow
    public abstract boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty);

    @Unique
    private int featherOpt$updateRange;

    @ModifyArg(method = "checkLightFor",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAreaLoaded(Lnet/minecraft/util/BlockPos;IZ)Z", ordinal = 0),
            index = 1
    )
    public int featherOpt$checkLightFor$reduceAreaLoadedCheckRange(int radius) {
        return 16;
    }

    @Inject(method = "checkLightFor", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 0))
    public void featherOpt$checkLightFor$calculateUpdateRange(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        this.featherOpt$updateRange = this.isAreaLoaded(pos, 18, false) ? 17 : 15;
    }

    @ModifyConstant(method = "checkLightFor",
            constant = @Constant(intValue = 17),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;startSection(Ljava/lang/String;)V", ordinal = 0)),
            allow = 2
    )
    public int featherOpt$checkLightFor$replaceRangeConstants(int constant) {
        return featherOpt$updateRange;
    }
}
