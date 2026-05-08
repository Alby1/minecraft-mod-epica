package malbyx.mod.epica;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CraftingCraftMenu extends AbstractCraftingMenu {

    private static final int CRAFTING_GRID_WIDTH = 3;
    private static final int CRAFTING_GRID_HEIGHT = 3;
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_COUNT = 9;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    private final ContainerLevelAccess access;
    private final Player player;
    private boolean placingRecipe;

    public CraftingCraftMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public CraftingCraftMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(MenuType.CRAFTING, i, 3, 3);
        this.access = containerLevelAccess;
        this.player = inventory.player;
        this.addResultSlot(this.player, 124, 35);
        this.addCraftingGridSlots(30, 17);
        this.addStandardInventorySlots(inventory, 8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {

        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack clicked = stack.copy();


        if (slotIndex < craftSlots.getContainerSize()) {
            if (!this.moveItemStackTo(stack, craftSlots.getContainerSize(), this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, 0, craftSlots.getContainerSize(), false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return clicked;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Blocks.CRAFTING_TABLE);
    }

    protected static void slotChangedCraftingGrid(AbstractContainerMenu abstractContainerMenu, ServerLevel serverLevel, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer, @Nullable RecipeHolder<CraftingRecipe> recipeHolder) {
        CraftingInput craftingInput = craftingContainer.asCraftInput();
        ServerPlayer serverPlayer = (ServerPlayer)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> optional = serverLevel.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInput, serverLevel, recipeHolder);
        if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipeHolder2 = (RecipeHolder)optional.get();
            CraftingRecipe craftingRecipe = (CraftingRecipe)recipeHolder2.value();
            if (resultContainer.setRecipeUsed(serverPlayer, recipeHolder2)) {
                ItemStack itemStack2 = craftingRecipe.assemble(craftingInput, serverLevel.registryAccess());
                if (itemStack2.isItemEnabled(serverLevel.enabledFeatures())) {
                    itemStack = itemStack2;
                }
            }
        }

        resultContainer.setItem(0, itemStack);
        abstractContainerMenu.setRemoteSlot(0, itemStack);
        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(abstractContainerMenu.containerId, abstractContainerMenu.incrementStateId(), 0, itemStack));
    }

    public void slotsChanged(Container container) {
        if (!this.placingRecipe) {
            var level = this.player.level();
            if (level instanceof ServerLevel serverLevel)
                slotChangedCraftingGrid(this, serverLevel, this.player, (CraftingContainer)this.craftSlots, this.resultSlots, (RecipeHolder)null);
        }

    }

    public Slot getResultSlot() {
        return (Slot)this.slots.get(0);
    }

    public List<Slot> getInputGridSlots() {
        return this.slots.subList(1, 10);
    }

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    protected Player owner() {
        return this.player;
    }
}
