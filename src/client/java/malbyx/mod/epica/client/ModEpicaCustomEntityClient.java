package malbyx.mod.epica.client;

import net.fabricmc.api.ClientModInitializer;

public class ModEpicaCustomEntityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntityModelLayers.registerModelLayers();
    }
}
