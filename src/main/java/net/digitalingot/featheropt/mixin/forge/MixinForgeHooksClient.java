package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/d3ad8aed76d6248deb5e6c38633b41693f46561e
 * and
 * https://github.com/MinecraftForge/MinecraftForge/commit/14f3120eedfee3bff4499ca64a62834c6e7ce3b7
 */
@Mixin(ForgeHooksClient.class)
public class MixinForgeHooksClient {

    @Shadow(remap = false)
    private static int skyX;

    @Shadow(remap = false)
    private static int skyRGBMultiplier;

    @Shadow(remap = false)
    private static boolean skyInit;

    @Shadow(remap = false)
    private static int skyZ;

    /**
     * @reason Fixes sky color blending for render distances > 16 and reduces allocations because of a wrong center.getZ() call
     * @author Minecraft Forge & tterrag & bbs2609
     */
    @Overwrite(remap = false)
    public static int getSkyBlendColour(World world, BlockPos center) {
        if (center.getX() == skyX && center.getZ() == skyZ && skyInit) {
            return skyRGBMultiplier;
        }
        skyInit = true;

        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && ranges.length > 0) {
            distance = ranges[MathHelper.clamp_int(settings.renderDistanceChunks, 0, ranges.length - 1)];
        }

        int r = 0;
        int g = 0;
        int b = 0;

        int divider = 0;
        for (int x = -distance; x <= distance; ++x) {
            for (int z = -distance; z <= distance; ++z) {
                BlockPos pos = center.add(x, 0, z);
                BiomeGenBase biome = world.getBiomeGenForCoords(pos);
                int colour = biome.getSkyColorByTemp(biome.getFloatTemperature(pos));
                r += (colour & 0xFF0000) >> 16;
                g += (colour & 0x00FF00) >> 8;
                b += colour & 0x0000FF;
                divider++;
            }
        }

        int multiplier = (r / divider & 255) << 16 | (g / divider & 255) << 8 | b / divider & 255;

        skyX = center.getX();
        skyZ = center.getZ();
        skyRGBMultiplier = multiplier;
        return skyRGBMultiplier;
    }
}
