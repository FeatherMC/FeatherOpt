package net.digitalingot.featheropt.mixin.block;

import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Mixin(EnumFacing.Plane.class)
public class MixinEnumFacing_ReduceIteratorAllocations {

    /**
     * @author FeatherOpt
     * @reason Reduce allocations when calling {@link EnumFacing.Plane#iterator()} by not creating a new {@link EnumFacing} array
     */
    @Overwrite
    public Iterator<EnumFacing> iterator() {
        if ((Object) this == EnumFacing.Plane.HORIZONTAL) {
            return new Iterator<EnumFacing>() {
                private int index;

                @Override
                public boolean hasNext() {
                    return index < 4;
                }

                @Override
                public EnumFacing next() {
                    switch (index++) {
                        case 0:
                            return EnumFacing.NORTH;
                        case 1:
                            return EnumFacing.EAST;
                        case 2:
                            return EnumFacing.SOUTH;
                        case 3:
                            return EnumFacing.WEST;
                        default:
                            throw new NoSuchElementException();
                    }
                }
            };
        } else {
            return new Iterator<EnumFacing>() {
                private int index;

                @Override
                public boolean hasNext() {
                    return index < 2;
                }

                @Override
                public EnumFacing next() {
                    switch (index++) {
                        case 0:
                            return EnumFacing.UP;
                        case 1:
                            return EnumFacing.DOWN;
                        default:
                            throw new NoSuchElementException();
                    }
                }
            };
        }
    }


}
