package net.digitalingot.featheropt;

import net.digitalingot.featheropt.config.ConfigurationProvider;
import net.digitalingot.featheropt.config.FallbackConfigurationProvider;
import net.digitalingot.featheropt.mixin.client.AccessorMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "featheropt", name = "FeatherOpt", version = "1.0.0-SNAPSHOT", clientSideOnly = true)
public class FeatherOpt {

    public static final Logger LOGGER = LogManager.getLogger("FeatherOpt");
    private ConfigurationProvider configurationProvider = new FallbackConfigurationProvider();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialized FeatherOpt!");

        // fix mixin bug with static accessors: https://github.com/SpongePowered/Mixin/issues/342
        MinecraftForgeClient.getRenderLayer();
        reload();
    }

    public void reload() {
        Minecraft mc = Minecraft.getMinecraft();
        ((AccessorMinecraft) mc).setEnableGLErrorChecking(!configurationProvider.isGLErrorCheckDisabled());
    }

    public ConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }

    public void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }
}
