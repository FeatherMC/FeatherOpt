package net.digitalingot.featheropt.mixin.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockState.StateImplementation.class)
public class MixinBlockState_FasterLookup {

    @Shadow
    @Final
    private ImmutableMap<IProperty<?>, Comparable<?>> properties;

    @Shadow
    @Final
    private Block block;

    @Shadow
    protected ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;

    /**
     * @author FeatherOpt
     * @reason Instead of doing {@link java.util.HashMap#containsKey(Object)} and then {@link java.util.HashMap#get(Object)},
     * we can do {@link java.util.HashMap#get(Object)} and a null-check
     */
    @Overwrite
    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        Object prop = this.properties.get(property);
        if (prop == null) {
            throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
        } else {
            return property.getValueClass().cast(prop);
        }
    }

    /**
     * @author FeatherOpt
     * @reason Instead of doing {@link java.util.HashMap#containsKey(Object)} and then {@link java.util.HashMap#get(Object)},
     * we can do {@link java.util.HashMap#get(Object)} and a null-check
     */
    @Overwrite
    public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
        Object prop = this.properties.get(property);
        if (prop == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
        } else if (!property.getAllowedValues().contains(value)) {
            throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
        } else {
            return (IBlockState) (prop == value ? this : (IBlockState) this.propertyValueTable.get(property, value));
        }
    }

}
