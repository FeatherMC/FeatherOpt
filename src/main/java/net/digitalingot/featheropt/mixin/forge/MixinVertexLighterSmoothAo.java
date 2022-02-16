package net.digitalingot.featheropt.mixin.forge;

import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;
import net.minecraftforge.client.model.pipeline.VertexLighterSmoothAo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/dac053415b8187c5b13f3350d718e20d1bf53496
 *
 * @author bs2609
 */
@Mixin(VertexLighterSmoothAo.class)
public class MixinVertexLighterSmoothAo extends VertexLighterFlat {

    /**
     * @author bs2609
     * @reason Use faster lightmap
     */
    @Overwrite(remap = false)
    protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
        x *= 2.0F;
        y *= 2.0F;
        z *= 2.0F;
        float l2 = x * x + y * y + z * z;
        float ax;
        if (l2 > 5.98F) {
            ax = (float) Math.sqrt(5.98F / l2);
            x *= ax;
            y *= ax;
            z *= ax;
        }

        ax = x > 0.0F ? x : -x;
        float ay = y > 0.0F ? y : -y;
        float az = z > 0.0F ? z : -z;
        float e1 = 1.0001F;
        if (ax > 1.9999F && ay <= e1 && az <= e1) {
            x = x < 0.0F ? -1.9999F : 1.9999F;
        } else if (ay > 1.9999F && az <= e1 && ax <= e1) {
            y = y < 0.0F ? -1.9999F : 1.9999F;
        } else if (az > 1.9999F && ax <= e1 && ay <= e1) {
            z = z < 0.0F ? -1.9999F : 1.9999F;
        }

        ax = x > 0.0F ? x : -x;
        ay = y > 0.0F ? y : -y;
        az = z > 0.0F ? z : -z;
        if (ax <= e1 && ay + az > 2.9999F) {
            float s = 2.9999F / (ay + az);
            y *= s;
            z *= s;
        } else if (ay <= e1 && az + ax > 2.9999F) {
            float s = 2.9999F / (az + ax);
            z *= s;
            x *= s;
        } else if (az <= e1 && ax + ay > 2.9999F) {
            float s = 2.9999F / (ax + ay);
            x *= s;
            y *= s;
        } else if (ax + ay + az > 3.9999F) {
            float s = 3.9999F / (ax + ay + az);
            x *= s;
            y *= s;
            z *= s;
        }

        float[][][][] blockLight = this.blockInfo.getBlockLight();
        float[][][][] skyLight = this.blockInfo.getSkyLight();
        float bl = 0.0F;
        float sl = 0.0F;
        float s = 0.0F;

        for (int ix = 0; ix <= 1; ++ix) {
            for (int iy = 0; iy <= 1; ++iy) {
                for (int iz = 0; iz <= 1; ++iz) {
                    float vx = x * (float) (1 - ix * 2);
                    float vy = y * (float) (1 - iy * 2);
                    float vz = z * (float) (1 - iz * 2);
                    float s3 = vx + vy + vz + 4.0F;
                    float sx = vy + vz + 3.0F;
                    float sy = vz + vx + 3.0F;
                    float sz = vx + vy + 3.0F;
                    float bx = (2.0F * vx + vy + vz + 6.0F) / (s3 * sy * sz * (vx + 2.0F));
                    s += bx;
                    bl += bx * blockLight[0][ix][iy][iz];
                    sl += bx * skyLight[0][ix][iy][iz];
                    float by = (2.0F * vy + vz + vx + 6.0F) / (s3 * sz * sx * (vy + 2.0F));
                    s += by;
                    bl += by * blockLight[1][ix][iy][iz];
                    sl += by * skyLight[1][ix][iy][iz];
                    float bz = (2.0F * vz + vx + vy + 6.0F) / (s3 * sx * sy * (vz + 2.0F));
                    s += bz;
                    bl += bz * blockLight[2][ix][iy][iz];
                    sl += bz * skyLight[2][ix][iy][iz];
                }
            }
        }

        bl /= s;
        sl /= s;
        lightmap[0] = MathHelper.clamp_float(bl, 0.0F, 0.0073243305F);
        lightmap[1] = MathHelper.clamp_float(sl, 0.0F, 0.0073243305F);
    }
}
