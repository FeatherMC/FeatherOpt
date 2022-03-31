package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/blob/bc381b92ea5cd4ed9817be76cf9490281038e016/src/main/java/net/minecraftforge/items/wrapper/InvWrapper.java
 *
 * @author mezz
 */
@Mixin(value = InvWrapper.class, remap = false)
public class MixinInvWrapper_ImprovedInsertionPerformance {

    @Shadow
    @Final
    public IInventory inv;
    @Unique
    private ItemStack featherOpt$caughtStackInSlot;

    @Redirect(method = "insertItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;isItemValidForSlot(ILnet/minecraft/item/ItemStack;)Z", ordinal = 0))
    public boolean featherOpt$skipCheck(IInventory instance, int i, ItemStack itemStack) {
        return true;
    }

    @Inject(method = "insertItem", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", shift = At.Shift.BEFORE, remap = false), require = 2, allow = 2, cancellable = true)
    public void featherOpt$insertItem$insertChecksAtNewPositions(int slot, ItemStack stack, boolean simulate, CallbackInfoReturnable<ItemStack> cir) {
        if (!this.inv.isItemValidForSlot(slot, stack)) {
            cir.setReturnValue(stack);
        }
    }

    @Redirect(
            method = "insertItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;getStackInSlot(I)Lnet/minecraft/item/ItemStack;", ordinal = 0)
    )
    public ItemStack featherOpt$catchStackInSlot(IInventory instance, int i) {
        this.featherOpt$caughtStackInSlot = this.inv.getStackInSlot(i);
        return featherOpt$caughtStackInSlot;
    }

    @Inject(method = "insertItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/ItemHandlerHelper;canItemStacksStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", remap = false, shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void featherOpt$addFasterCountCheck(int slot, ItemStack stack, boolean simulate, CallbackInfoReturnable<ItemStack> cir) {
        if (featherOpt$caughtStackInSlot != null && featherOpt$caughtStackInSlot.stackSize >= Math.min(featherOpt$caughtStackInSlot.getMaxStackSize(), inv.getInventoryStackLimit())) {
            cir.setReturnValue(stack);
        }
    }
}
