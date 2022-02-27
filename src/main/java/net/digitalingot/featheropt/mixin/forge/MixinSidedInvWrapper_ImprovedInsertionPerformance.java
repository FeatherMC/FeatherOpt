package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
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
 * and
 * https://github.com/MinecraftForge/MinecraftForge/commit/7b4040b70b07787ddc19ca3a55cbcee4fe09cba2
 *
 * @author mezz & bs2609
 */
@Mixin(value = SidedInvWrapper.class, remap = false)
public class MixinSidedInvWrapper_ImprovedInsertionPerformance {

    @Shadow
    @Final
    protected ISidedInventory inv;
    @Shadow
    @Final
    protected EnumFacing side;
    @Unique
    private ItemStack featherOpt$caughtStackInSlot;

    @Redirect(
            method = "insertItem",
            at = @At(value = "INVOKE", target = "net/minecraft/inventory/ISidedInventory.isItemValidForSlot(ILnet/minecraft/item/ItemStack;)Z", ordinal = 0)
    )
    public boolean featherOpt$skipIsItemValidCheck(ISidedInventory instance, int i, ItemStack itemStack) {
        return true;
    }

    @Redirect(
            method = "insertItem",
            at = @At(value = "INVOKE", target = "net/minecraft/inventory/ISidedInventory.canInsertItem(ILnet/minecraft/item/ItemStack;Lnet/minecraft/util/EnumFacing;)Z", ordinal = 0)
    )
    public boolean featherOpt$skipCanInsertItemCheck(ISidedInventory instance, int i, ItemStack itemStack, EnumFacing enumFacing) {
        return true;
    }

    @Inject(method = "insertItem", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", remap = false), require = 2, allow = 2, cancellable = true)
    public void featherOpt$insertItem$insertChecksAtNewPositions(int slot, ItemStack stack, boolean simulate, CallbackInfoReturnable<ItemStack> cir) {
        if (!this.inv.canInsertItem(slot, stack, this.side) || !this.inv.isItemValidForSlot(slot, stack)) {
            cir.setReturnValue(stack);
        }
    }

    @Redirect(
            method = "insertItem",
            at = @At(value = "INVOKE", target = "net/minecraft/inventory/ISidedInventory.getStackInSlot(I)Lnet/minecraft/item/ItemStack;", ordinal = 0)
    )
    public ItemStack featherOpt$catchStackInSlot(ISidedInventory instance, int i) {
        this.featherOpt$caughtStackInSlot = this.inv.getStackInSlot(i);
        return featherOpt$caughtStackInSlot;
    }

    @Inject(method = "insertItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/ItemHandlerHelper;canItemStacksStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", remap = false, shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void featherOpt$addFasterCountCheck(int slot, ItemStack stack, boolean simulate, CallbackInfoReturnable<ItemStack> cir) {
        if (featherOpt$caughtStackInSlot.stackSize >= Math.min(featherOpt$caughtStackInSlot.getMaxStackSize(), inv.getInventoryStackLimit())) {
            cir.setReturnValue(stack);
        }
    }
}
