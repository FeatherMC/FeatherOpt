package net.digitalingot.featheropt.mixin.world;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Backported from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/22ea5a23ad2a2ff8660a353c7da7f8e2cda1b37a
 *
 * @author AEnterprise
 */
@Mixin(World.class)
public class MixinWorld_FastCollisionCheck {

}
