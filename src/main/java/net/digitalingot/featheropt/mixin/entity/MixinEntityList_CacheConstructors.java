package net.digitalingot.featheropt.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Idea backport from Forge 1.12.2:
 * https://github.com/MinecraftForge/MinecraftForge/commit/50bf03b82b72866bd54e796a37707d45bcbf3d80
 */
@Mixin(EntityList.class)
public class MixinEntityList_CacheConstructors {

    @Unique
    private static final Map<Class<? extends Entity>, Constructor<?>> FEATHER_OPT$CLASS_TO_CONSTRUCTOR = new HashMap<>();

    @Redirect(method = "createEntityByName", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;", remap = false))
    private static Constructor<?> featherOpt$createEntityByName$cacheConstructor(Class<? extends Entity> instance, Class<?>[] parameterTypes) {
        return featherOpt$getConstructor(instance);
    }

    @Redirect(method = "createEntityFromNBT", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;", remap = false))
    private static Constructor<?> featherOpt$createEntityFromNBT$cacheConstructor(Class<? extends Entity> instance, Class<?>[] parameterTypes) {
        return featherOpt$getConstructor(instance);
    }

    @Redirect(method = "createEntityByID", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;", remap = false))
    private static Constructor<?> featherOpt$createEntityByID$cacheConstructor(Class<? extends Entity> instance, Class<?>[] parameterTypes) {
        return featherOpt$getConstructor(instance);
    }

    private static Constructor<?> featherOpt$getConstructor(Class<? extends Entity> entityClass) {
        Constructor<?> constructor = FEATHER_OPT$CLASS_TO_CONSTRUCTOR.get(entityClass);
        if (constructor == null) {
            try {
                constructor = entityClass.getConstructor(World.class);
                FEATHER_OPT$CLASS_TO_CONSTRUCTOR.put(entityClass, constructor);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return constructor;
    }

}
