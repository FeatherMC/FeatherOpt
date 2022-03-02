package net.digitalingot.featheropt.config;

public class FallbackConfigurationProvider implements ConfigurationProvider {
    @Override
    public boolean isGLErrorCheckDisabled() {
        return true;
    }
}
