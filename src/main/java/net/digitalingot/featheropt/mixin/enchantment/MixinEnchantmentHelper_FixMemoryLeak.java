package net.digitalingot.featheropt.mixin.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/8fdc6eff0787e0167f2b5a466de519f870d20c29
 *
 * @author bs2609
 */
@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper_FixMemoryLeak {

    @Shadow
    @Final
    private static EnchantmentHelper.HurtIterator ENCHANTMENT_ITERATOR_HURT;

    @Shadow
    @Final
    private static EnchantmentHelper.DamageIterator ENCHANTMENT_ITERATOR_DAMAGE;

    @Shadow
    @Final
    private static EnchantmentHelper.ModifierDamage enchantmentModifierDamage;

    @Shadow
    @Final
    private static EnchantmentHelper.ModifierLiving enchantmentModifierLiving;

    @Inject(method = "getEnchantmentModifierDamage", at = @At("TAIL"))
    private static void featherOpt$getEnchantmentModifierDamage$nullify(ItemStack[] p_getEnchantmentModifierDamage_0_, DamageSource p_getEnchantmentModifierDamage_1_, CallbackInfoReturnable<Integer> cir) {
        enchantmentModifierDamage.source = null;
    }

    @Inject(method = "getModifierForCreature", at = @At("TAIL"))
    private static void featherOpt$getModifierForCreature$nullify(ItemStack p_getModifierForCreature_0_, EnumCreatureAttribute p_getModifierForCreature_1_, CallbackInfoReturnable<Float> cir) {
        enchantmentModifierLiving.entityLiving = null;
    }

    @Inject(method = "applyThornEnchantments", at = @At("TAIL"))
    private static void featherOpt$applyThornEnchantments$nullify(EntityLivingBase p_151384_0_, Entity p_151384_1_, CallbackInfo ci) {
        ENCHANTMENT_ITERATOR_HURT.attacker = null;
        ENCHANTMENT_ITERATOR_HURT.user = null;
    }

    @Inject(method = "applyArthropodEnchantments", at = @At("TAIL"))
    private static void featherOpt$applyArthropodEnchantments$nullify(EntityLivingBase p_151384_0_, Entity p_151384_1_, CallbackInfo ci) {
        ENCHANTMENT_ITERATOR_DAMAGE.target = null;
        ENCHANTMENT_ITERATOR_DAMAGE.user = null;
    }

}
