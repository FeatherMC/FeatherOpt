package net.digitalingot.featheropt.helpers;

import com.google.common.base.Optional;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraftforge.client.model.IModelPart;
import net.minecraftforge.client.model.TRSRTransformation;

public class ForgeHooksClientHook {

    public static Optional<TRSRTransformation> applyTransform(ModelRotation rotation, Optional<? extends IModelPart> part) {
        if (part.isPresent()) return Optional.absent();
        return Optional.of(TRSRTransformationHook.from(rotation));
    }


}
