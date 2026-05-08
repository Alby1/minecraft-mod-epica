package malbyx.mod.epica;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class ModEntities {
    public static final EntityType<FastCartEntity> FAST_CART = register(
            "fastcart",
            EntityType.Builder.<FastCartEntity>of(FastCartEntity::new, MobCategory.MISC)
                    .sized(0.98f, 0.7f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        ModEpica.LOGGER.info("Registering EntityTypes for " + ModEpica.MOD_ID);
    }

    public static void registerAttributes() {

    }

    public static void initialize() {}
}
