package net.digitalingot.featheropt;

import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

public class FeatherOptAccessTransformer extends AccessTransformer {

    public FeatherOptAccessTransformer() throws IOException {
        super("featheropt_at.cfg");
    }

}
