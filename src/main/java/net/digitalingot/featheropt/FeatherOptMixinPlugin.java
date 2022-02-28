package net.digitalingot.featheropt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.client.MinecraftForgeClient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FeatherOptMixinPlugin implements IMixinConfigPlugin {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String CONDITIONS_SOURCE = "mixins.featheropt.cond.json";

    private static final ConditionalSolver SOLVER = new ConditionalSolver(token -> {
        if ("patcher".equals(token)) {
            return External.isPatcherInstalled() ? 1 : 0;
        }

        throw new IllegalArgumentException("Unknown token: " + token);
    });

    private final List<String> extraMixins = Lists.newArrayList();

    @Override
    public void onLoad(String mixinPackage) {
        for (Map.Entry<String, String> entry : loadConditionals().entrySet()) {
            if (SOLVER.solve(entry.getValue())) {
                FeatherOpt.LOGGER.debug("Adding mixin: " + entry.getKey());
                extraMixins.add(entry.getKey());
            }
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return extraMixins;
    }

    @Override
    public void preApply(String targetClassName, org.spongepowered.asm.lib.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, org.spongepowered.asm.lib.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @NotNull
    private static Map<String, String> loadConditionals() {
        try {
            return loadConditionals0();
        } catch (Throwable throwable) {
            FeatherOpt.LOGGER.warn("Failed to load conditionals, falling back to default. Cause: " + throwable);
            return Maps.newHashMap();
        }
    }

    @NotNull
    private static Map<String, String> loadConditionals0() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream sourceStream = loader.getResourceAsStream(CONDITIONS_SOURCE);
             InputStreamReader sourceReader = new InputStreamReader(Objects.requireNonNull(sourceStream))) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            return GSON.fromJson(sourceReader, type);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load conditionals", e);
        }
    }

    private static class ConditionalSolver {
        private final TokenProvider tokenProvider;

        public ConditionalSolver(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
        }

        public boolean solve(String expr) {
            String[] split = expr.split("==");
            String token = split[0].trim();
            int tokenValue = tokenProvider.getToken(token);
            int value = Integer.parseInt(split[1].trim());

            return tokenValue == value;
        }
    }

    private interface TokenProvider {
        Integer getToken(String token);
    }
}
