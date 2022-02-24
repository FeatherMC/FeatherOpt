package net.digitalingot.featheropt.helpers;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class LightUtilHook {

    /**
     * See https://github.com/MinecraftForge/MinecraftForge/blob/0d34b02f8821bf114a7ee8e298af6263e7abb8d0/src/main/java/net/minecraftforge/client/model/pipeline/LightUtil.java
     *
     * @author Minecraft Forge
     */
    public static int[] generateMapping(VertexFormat from, VertexFormat to) {
        int fromCount = from.getElementCount();
        int toCount = to.getElementCount();
        int[] eMap = new int[fromCount];

        for (int e = 0; e < fromCount; e++) {
            VertexFormatElement expected = from.getElement(e);
            int e2;
            for (e2 = 0; e2 < toCount; e2++) {
                VertexFormatElement current = to.getElement(e2);
                if (expected.getUsage() == current.getUsage() && expected.getIndex() == current.getIndex()) {
                    break;
                }
            }
            eMap[e] = e2;
        }
        return eMap;
    }

}
