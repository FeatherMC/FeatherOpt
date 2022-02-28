package net.digitalingot.featheropt.helpers;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.TRSRTransformation;

import javax.vecmath.Vector3f;
import java.util.EnumMap;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/blob/0b5a6a3b031816ca46d4bc405a0062781dec914b/src/main/java/net/minecraftforge/client/model/ForgeBlockStateV1.java
 *
 * @author Minecraft Forge
 */
public class ForgeBlockStateV1Hook {

    private static final ImmutableMap<String, IModelState> TRANSFORMS;

    static {
        ImmutableMap.Builder<String, IModelState> builder = ImmutableMap.builder();

        builder.put("identity", TRSRTransformation.identity());

        // block/block
        {
            EnumMap<TransformType, TRSRTransformation> map = new EnumMap<>(TransformType.class);
            map.put(TransformType.GUI, get(0, 0, 0, 30, 225, 0, 0.625f));
            map.put(TransformType.GROUND, get(0, 3, 0, 0, 0, 0, 0.25f));
            map.put(TransformType.FIXED, get(0, 0, 0, 0, 0, 0, 0.5f));
            map.put(TransformType.THIRD_PERSON, get(0, 2.5f, 0, 75, 45, 0, 0.375f));
            map.put(TransformType.FIRST_PERSON, get(0, 0, 0, 0, 225, 0, 0.4f));
            builder.put("forge:default-block", new SimpleModelState(ImmutableMap.copyOf(map)));
        }

        // item/generated
        {
            EnumMap<TransformType, TRSRTransformation> map = new EnumMap<>(TransformType.class);
            map.put(TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            map.put(TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            map.put(TransformType.THIRD_PERSON, get(0, 3, 1, 0, 0, 0, 0.55f));
            map.put(TransformType.FIRST_PERSON, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            map.put(TransformType.FIXED, get(0, 0, 0, 0, 180, 0, 1));
            builder.put("forge:default-item", new SimpleModelState(ImmutableMap.copyOf(map)));
        }

        // item/handheld
        {
            EnumMap<TransformType, TRSRTransformation> map = new EnumMap<>(TransformType.class);
            map.put(TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
            map.put(TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
            map.put(TransformType.THIRD_PERSON, get(0, 4, 0.5f, 0, -90, 55, 0.85f));
            map.put(TransformType.FIRST_PERSON, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
            map.put(TransformType.FIXED, get(0, 0, 0, 0, 180, 0, 1));
            builder.put("forge:default-tool", new SimpleModelState(ImmutableMap.copyOf(map)));
        }


        TRANSFORMS = builder.build();
    }

    public static IModelState get(String transform) {
        return TRANSFORMS.get(transform);
    }

    private static TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s)
    {
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                new Vector3f(tx / 16, ty / 16, tz / 16),
                TRSRTransformation.quatFromYXZDegrees(new Vector3f(ax, ay, az)),
                new Vector3f(s, s, s),
                null));
    }

}
