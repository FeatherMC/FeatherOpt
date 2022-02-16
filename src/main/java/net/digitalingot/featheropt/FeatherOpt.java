package net.digitalingot.featheropt;

import net.digitalingot.featheropt.config.ConfigurationProvider;
import net.digitalingot.featheropt.config.FallbackConfigurationProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "featheropt", name = "FeatherOpt", version = "1.0.0-SNAPSHOT", clientSideOnly = true)
public class FeatherOpt {

    private ConfigurationProvider configurationProvider = new FallbackConfigurationProvider();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Initialized FeatherOpt!");
    }

    public ConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }

    public void setConfigurationProvider(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }
}
