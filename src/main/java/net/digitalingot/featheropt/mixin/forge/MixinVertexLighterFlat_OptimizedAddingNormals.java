package net.digitalingot.featheropt.mixin.forge;

import net.digitalingot.featheropt.helpers.IVertexLighterFlat;
import net.digitalingot.featheropt.helpers.VertexLighterFlatHook;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.QuadGatheringTransformer;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/dda7bd5d42e66e60563be9d83904c4f2bb76baea
 * and
 * https://github.com/MinecraftForge/MinecraftForge/commit/1aae18d4bc28c0436ef2b5f243f7aa25d8d1c649
 *
 * @author bs2609 & ichttt
 */
@Mixin(VertexLighterFlat.class)
public abstract class MixinVertexLighterFlat_OptimizedAddingNormals extends QuadGatheringTransformer implements IVertexLighterFlat {
    @Shadow(remap = false)
    protected int posIndex;
    @Shadow(remap = false)
    protected int normalIndex;
    @Shadow(remap = false)
    protected int colorIndex;
    @Shadow(remap = false)
    protected int lightmapIndex;

    @Unique
    protected VertexFormat featherOpt$baseFormat;

    /**
     * @author bs2609
     * @reason Optimise adding normal element to vertex formats
     */
    @Overwrite(remap = false)
    public void setParent(IVertexConsumer parent) {
        super.setParent(parent);
        setVertexFormat(parent.getVertexFormat());
    }

    @Override
    public void setVertexFormat(VertexFormat format) {
        if (Objects.equals(format, featherOpt$baseFormat)) return;
        this.featherOpt$baseFormat = format;
        super.setVertexFormat(VertexLighterFlatHook.withNormal(format));
        featherOpt$updateIndices();
    }

    @Unique
    private void featherOpt$updateIndices() {
        for (int i = 0; i < getVertexFormat().getElementCount(); i++) {
            switch (getVertexFormat().getElement(i).getUsage()) {
                case POSITION:
                    posIndex = i;
                    break;
                case NORMAL:
                    normalIndex = i;
                    break;
                case COLOR:
                    colorIndex = i;
                    break;
                case UV:
                    if (getVertexFormat().getElement(i).getIndex() == 1) {
                        lightmapIndex = i;
                    }
                    break;
                default:
            }
        }
        if (posIndex == -1) {
            throw new IllegalArgumentException("vertex lighter needs format with position");
        }
        if (lightmapIndex == -1) {
            throw new IllegalArgumentException("vertex lighter needs format with lightmap");
        }
        if (colorIndex == -1) {
            throw new IllegalArgumentException("vertex lighter needs format with color");
        }
    }

}
