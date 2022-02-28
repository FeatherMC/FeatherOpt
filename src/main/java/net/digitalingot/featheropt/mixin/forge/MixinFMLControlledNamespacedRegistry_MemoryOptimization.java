package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.BitSet;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/b203468cde953bbd35b4043fa8ebff4364f1f8d9
 *
 * @author LexManos
 */
@Mixin(value = FMLControlledNamespacedRegistry.class, remap = false)
public class MixinFMLControlledNamespacedRegistry_MemoryOptimization {

    @Shadow
    @Final
    private BitSet availabilityMap;

    @ModifyArg(
            method = "<init>(Lnet/minecraft/util/ResourceLocation;IILjava/lang/Class;ZLnet/minecraftforge/fml/common/registry/FMLControlledNamespacedRegistry$AddCallback;)V",
            at = @At(value = "NEW", target = "Ljava/util/BitSet;<init>(I)V"))
    public int featherOpt$dontPreallocateBitset(int size) {
        return Math.min(size, 0xFFFF);
    }

    @Inject(method = "validateContent", at = @At("HEAD"))
    public void featherOpt$trimBitset(ResourceLocation name, CallbackInfo ci) {
        try {
            ReflectionHelper.findMethod(BitSet.class, this.availabilityMap, new String[]{"trimToSize"}).invoke(this.availabilityMap);
        } catch (Exception e) {
            //We don't care... Just a micro-optimization
        }
    }

}
