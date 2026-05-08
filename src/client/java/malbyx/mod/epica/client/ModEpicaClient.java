package malbyx.mod.epica.client;

import malbyx.mod.epica.ModMenuType;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModEpicaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MenuScreens.register(ModMenuType.CRAFTING_CRAFT, CraftingCraftScreen::new);
	}
}