package net.digitalingot.featheropt;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.GlobalProperties;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class FeatherOptTweaker implements ITweaker {

    public FeatherOptTweaker() {
        if (!isMixinAlreadySetup()) {
            Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch");
            List<ITweaker> tweaks = (List<ITweaker>) Launch.blackboard.get("Tweaks");
            try {
                Class<?> clazz = Class.forName("org.spongepowered.asm.launch.MixinTweaker", true, Launch.classLoader);
                ITweaker mixinTweaker = (ITweaker) clazz.getDeclaredConstructor().newInstance();
                tweaks.add(mixinTweaker);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isMixinAlreadySetup() {
        if (GlobalProperties.get(GlobalProperties.Keys.INIT) != null) {
            return true;
        }

        List<String> tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        return tweakClasses.contains("org.spongepowered.asm.launch.MixinTweaker");
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {

    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {

    }

    @Override
    public String getLaunchTarget() {
        return null;
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
