package net.digitalingot.featheropt.helpers;

import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.TRSRTransformation;

import java.util.EnumMap;
import java.util.Map;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/blob/0b5a6a3b031816ca46d4bc405a0062781dec914b/src/main/java/net/minecraftforge/client/model/ForgeBlockStateV1.java
 *
 * @author Minecraft Forge
 */
public class TRSRTransformationHook {

    public static TRSRTransformation from(ItemTransformVec3f transform) {
        return transform.equals(ItemTransformVec3f.DEFAULT) ? TRSRTransformation.identity() : new TRSRTransformation(transform);
    }

    public static TRSRTransformation from(ModelRotation rotation) {
        return Cache.get(rotation);
    }

    public static TRSRTransformation from(EnumFacing facing) {
        return Cache.get(getRotation(facing));
    }

    public static ModelRotation getRotation(EnumFacing facing) {
        switch (facing) {
            case DOWN:
                return ModelRotation.X90_Y0;
            case UP:
                return ModelRotation.X270_Y0;
            case NORTH:
                return ModelRotation.X0_Y0;
            case SOUTH:
                return ModelRotation.X0_Y180;
            case WEST:
                return ModelRotation.X0_Y270;
            case EAST:
                return ModelRotation.X0_Y90;
        }
        throw new IllegalArgumentException(String.valueOf(facing));
    }

    private static final class Cache {
        private static final Map<ModelRotation, TRSRTransformation> rotations = new EnumMap<>(ModelRotation.class);

        static {
            rotations.put(ModelRotation.X0_Y0, TRSRTransformation.identity());
        }

        static TRSRTransformation get(ModelRotation rotation) {
            return rotations.computeIfAbsent(rotation, r -> new TRSRTransformation(ForgeHooksClient.getMatrix(r)));
        }
    }

}
