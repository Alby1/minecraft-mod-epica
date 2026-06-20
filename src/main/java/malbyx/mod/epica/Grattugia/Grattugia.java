package malbyx.mod.epica.Grattugia;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Grattugia extends Item {
    public Grattugia(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return new ItemStack(this);
    }
}
