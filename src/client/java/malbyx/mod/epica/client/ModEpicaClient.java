package malbyx.mod.epica.client;

import malbyx.mod.epica.ModEntities;
import malbyx.mod.epica.ModMenuType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ModEpicaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MenuScreens.register(ModMenuType.CRAFTING_CRAFT, CraftingCraftScreen::new);

		EntityRenderers.register(ModEntities.FAST_CART, (context) -> new FastCartEntityRenderer(context, ModEntityModelLayers.FASTCART));
	}
}