package net.digitalingot.featheropt.mixin.forge;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import net.digitalingot.featheropt.helpers.ExtendedStateImplementation;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/fff660918a6756beae15d9a14b295cb945b3f6f3
 * and
 * https://github.com/MinecraftForge/MinecraftForge/commit/0b969ef495bf6123289d636ed72e30f2bfa6db49
 *
 * @author bs2609 & LexManos
 */
@Mixin(value = ExtendedBlockState.class, remap = false)
public class MixinExtendedBlockState_ImprovedPerformance extends BlockState {

    public MixinExtendedBlockState_ImprovedPerformance(Block blockIn, IProperty... properties) {
        super(blockIn, properties);
    }

    /**
     * @author FeatherOpt
     * @reason Inject backported extended block state implementation
     */
    @Overwrite
    protected BlockState.StateImplementation createState(Block block, ImmutableMap<IProperty, Comparable> properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        if (unlistedProperties == null || unlistedProperties.isEmpty())
            return super.createState(block, properties, unlistedProperties);
        return new ExtendedStateImplementation(block, properties, unlistedProperties, null, null);
    }

}
