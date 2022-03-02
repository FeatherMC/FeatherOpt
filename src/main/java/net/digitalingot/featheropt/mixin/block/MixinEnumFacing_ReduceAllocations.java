package net.digitalingot.featheropt.mixin.block;

import net.digitalingot.featheropt.helpers.Constants;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import scala.collection.immutable.Stream;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Mixin(EnumFacing.Plane.class)
public class MixinEnumFacing_ReduceAllocations {


    /**
     * @author FeatherOpt
     * @reason Reduce allocations by not creating a new array
     */
    @Overwrite
    public EnumFacing[] facings() {
        switch ((EnumFacing.Plane) (Object) this) {
            case HORIZONTAL:
                return Constants.HORIZONTAL_FACINGS;
            case VERTICAL:
                return Constants.VERTICAL_FACINGS;
            default:
                throw new IllegalStateException("Someone's been tampering with the universe!");
        }
    }

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
