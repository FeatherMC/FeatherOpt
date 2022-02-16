package net.digitalingot.featheropt;

import net.digitalingot.featheropt.config.ConfigurationProvider;
import net.digitalingot.featheropt.config.FallbackConfigurationProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.logging.Logger;

@Mod(modid = "featheropt", name = "FeatherOpt", version = "1.0.0-SNAPSHOT", clientSideOnly = true)
public class FeatherOpt {

    public static final Logger LOGGER = Logger.getLogger("FeatherOpt");
    private ConfigurationProvider configurationProvider = new FallbackConfigurationProvider();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initialized FeatherOpt!");
    }

    public ConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }

    public void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }
}
