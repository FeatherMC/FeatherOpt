package net.digitalingot.featheropt.mixin.forge;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/7f337cf2309631bccd2d6c573c1a348f48f067f3
 *
 * @author ichttt
 */
@Mixin(value = ASMDataTable.class, remap = false)
public class MixinASMDataTable {

    @Shadow(remap = false)
    private Map<ModContainer, SetMultimap<String, ASMDataTable.ASMData>> containerAnnotationData;

    @Shadow(remap = false)
    private List<ModContainer> containers;

    @Shadow(remap = false)
    private SetMultimap<String, ASMDataTable.ASMData> globalAnnotationData;

    /**
     * @author ichttt
     * @reason Compute ASMDataTable in parallel
     */
    @Overwrite(remap = false)
    public SetMultimap<String, ASMDataTable.ASMData> getAnnotationsFor(ModContainer container) {
        if (containerAnnotationData == null) {
            Map<ModContainer, SetMultimap<String, ASMDataTable.ASMData>> map = containers.parallelStream()
                    .map(cont -> Pair.of(cont, ImmutableSetMultimap.copyOf(Multimaps.filterValues(globalAnnotationData, input -> cont.getSource().equals(input.getCandidate().getModContainer())))))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
            containerAnnotationData = ImmutableMap.copyOf(map);

        }
        return containerAnnotationData.get(container);
    }
}
