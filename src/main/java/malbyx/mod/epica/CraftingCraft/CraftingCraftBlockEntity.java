package malbyx.mod.epica.CraftingCraft;

import malbyx.mod.epica.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.BiFunction;

public class CraftingCraftBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider, StackedContentsCompatible {
    private final int WIDTH = 3;
    private final int HEIGHT = 3;

    public CraftingCraftBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRAFTING_CRAFT_BLOCK_ENTITY, pos, state);

        this.CONTAINER_SIZE = WIDTH * HEIGHT;
        this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    }

    public NonNullList<ItemStack> items;

    public int CONTAINER_SIZE;

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, items);
        super.saveAdditional(output);
    }

    @Override
    @NonNull
    public Component getDisplayName() {
        return Component.translatable("block.mod-epica.crafting_craft");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CraftingCraftMenu(containerId, inventory, new ContainerLevelAccess() {
            @Override
            public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> biFunction) {
                return Optional.empty();
            }
        }, this);
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents) {
        for(ItemStack itemStack : this.items) {
            stackedItemContents.accountSimpleStack(itemStack);
        }
    }

    public CraftingInput asCraftInput() {
        return this.asPositionedCraftInput().input();
    }

    public CraftingInput.Positioned asPositionedCraftInput() {
        return CraftingInput.ofPositioned(this.getWIDTH(), this.getHEIGHT(), this.getItems());
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }
}
