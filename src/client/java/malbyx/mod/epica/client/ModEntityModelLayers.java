package malbyx.mod.epica.client;

import malbyx.mod.epica.ModEpica;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
    public static final ModelLayerLocation FASTCART = createMain("fastcart");

    private static ModelLayerLocation createMain(String name) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, name), "main");
    }

    public static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FASTCART, FastCartEntityModel::getTexturedModelData);
    }
}