package malbyx.mod.epica.CraftingCraft;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CraftingCraftResultSlot extends ResultSlot {
    private CraftingCraftBlockEntity craftSlots;
    private int removeCount;
    private Player player;

    public CraftingCraftResultSlot(Player player, CraftingContainer craftingContainer, Container container, int i, int j, int k) {
        super(player, craftingContainer, container, i, j, k);
    }

    public CraftingCraftResultSlot(Player player, CraftingCraftBlockEntity craftingContainer, Container container, int i, int j, int k) {
        this(player, new UselessCraftingContainer(), container, i, j, k);

        this.craftSlots = craftingContainer;
        this.player = player;
    }

    @Override
    protected void checkTakeAchievements(ItemStack itemStack) {
        if (this.removeCount > 0) {
            itemStack.onCraftedBy(this.player, this.removeCount);
        }

        Container var3 = this.container;
        if (var3 instanceof RecipeCraftingHolder recipeCraftingHolder) {
            recipeCraftingHolder.awardUsedRecipes(this.player, this.craftSlots.getItems());
        }

        this.removeCount = 0;
    }

    @Override
    public void onTake(Player player, ItemStack itemStack) {
        this.checkTakeAchievements(itemStack);
        CraftingInput.Positioned positioned = this.craftSlots.asPositionedCraftInput();
        CraftingInput craftingInput = positioned.input();
        int i = positioned.left();
        int j = positioned.top();
        NonNullList<ItemStack> nonNullList = this.getRemainingItems(craftingInput, player.level());

        for (int k = 0; k < craftingInput.height(); ++k) {
            for (int l = 0; l < craftingInput.width(); ++l) {
                int m = l + i + (k + j) * this.craftSlots.getWIDTH();
                ItemStack itemStack2 = this.craftSlots.getItem(m);
                ItemStack itemStack3 = (ItemStack) nonNullList.get(l + k * craftingInput.width());
                if (!itemStack2.isEmpty()) {
                    this.craftSlots.removeItem(m, 1);
                    itemStack2 = this.craftSlots.getItem(m);
                }

                if (!itemStack3.isEmpty()) {
                    if (itemStack2.isEmpty()) {
                        this.craftSlots.setItem(m, itemStack3);
                    } else if (ItemStack.isSameItemSameComponents(itemStack2, itemStack3)) {
                        itemStack3.grow(itemStack2.getCount());
                        this.craftSlots.setItem(m, itemStack3);
                    } else if (!this.player.getInventory().add(itemStack3)) {
                        this.player.drop(itemStack3, false);
                    }
                }
            }
        }
    }


    private NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput, Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return (NonNullList)serverLevel.recipeAccess().getRecipeFor(RecipeType.CRAFTING, craftingInput, serverLevel).map((recipeHolder) -> ((CraftingRecipe)recipeHolder.value()).getRemainingItems(craftingInput)).orElseGet(() -> copyAllInputItems(craftingInput));
        } else {
            return CraftingRecipe.defaultCraftingReminder(craftingInput);
        }
    }

    private static NonNullList<ItemStack> copyAllInputItems(CraftingInput craftingInput) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        for(int i = 0; i < nonNullList.size(); ++i) {
            nonNullList.set(i, craftingInput.getItem(i));
        }

        return nonNullList;
    }
}
