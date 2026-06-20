package malbyx.mod.epica.client.GoldCup;

import malbyx.mod.epica.CraftingCraft.CraftingCraftMenu;
import malbyx.mod.epica.GoldCup.GoldCupMenu;
import malbyx.mod.epica.ModMenuType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookSignScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class GoldCupScreen extends Screen implements MenuAccess<GoldCupMenu> {

    private GoldCupMenu menu;
    private BookSignScreen signScreen;
    private Player owner;
    private ItemStack book;
    private InteractionHand hand;
    private EditBox page;

    public static final Component TITLE = Component.translatable("book.edit.title");
    private static final Component SIGN_BOOK_LABEL = Component.translatable("book.signButton");

    protected GoldCupScreen(Component component) {
        super(component);
    }

    protected GoldCupScreen(Minecraft minecraft, Font font, Component component) {
        super(minecraft, font, component);
    }

    public GoldCupScreen(Player player, ItemStack itemStack, InteractionHand interactionHand) {
        super(TITLE);


        this.owner = player;
        this.book = itemStack;
        this.hand = interactionHand;
    }

    public GoldCupScreen(GoldCupMenu craftingMenu, Inventory inventory, Component component) {
        super(TITLE);

        this.menu = craftingMenu;
    }

    protected void init() {
        int i = this.backgroundLeft();
        int j = this.backgroundTop();
        int k = 8;
        this.page = new EditBox(this.font, 122, 134, CommonComponents.EMPTY);

        Objects.requireNonNull(this.font);
        this.addRenderableWidget(this.page);

        this.addRenderableWidget(Button.builder(SIGN_BOOK_LABEL, (button) -> {
            this.saveChanges();
            this.minecraft.setScreen((Screen)null);
        }).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.minecraft.setScreen((Screen)null);
            this.signCup();
        }).pos(this.width / 2 + 2, this.menuControlsTop()).width(98).build());
    }

    private int backgroundLeft() {
        return (this.width - 192) / 2;
    }

    private int backgroundTop() {
        return 2;
    }

    private int menuControlsTop() {
        return this.backgroundTop() + 192 + 2;
    }

    private void saveChanges() {
        /*this.updateLocalCopy();
        int i = this.hand == InteractionHand.MAIN_HAND ? this.owner.getInventory().getSelectedSlot() : 40;
        this.minecraft.getConnection().send(new ServerboundEditBookPacket(i, this.pages, Optional.empty()));*/
    }

    private void updateLocalCopy() {
        /*this.book.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(this.pages.stream().map(Filterable::passThrough).toList()));*/
    }

    private void signCup() {
        /*int i = this.hand == InteractionHand.MAIN_HAND ? this.owner.getInventory().getSelectedSlot() : 40;
        this.minecraft.getConnection().send(new ServerboundEditBookPacket(i, this.pages, Optional.of(this.titleBox.getValue().trim())));*/
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.visitText(guiGraphics.textRenderer());
    }
    private void visitText(ActiveTextCollector activeTextCollector) {
        int i = this.backgroundLeft();
        int j = this.backgroundTop();
        activeTextCollector.accept(TextAlignment.RIGHT, i + 148, j + 16, CommonComponents.EMPTY);
    }

    @Override
    public GoldCupMenu getMenu() {
        return this.menu;
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double d, double e) {
        return super.getChildAt(d, e);
    }

    @Override
    public void mouseMoved(double d, double e) {
        super.mouseMoved(d, e);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        return super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent mouseButtonEvent, double d, double e) {
        return super.mouseDragged(mouseButtonEvent, d, e);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f, double g) {
        return super.mouseScrolled(d, e, f, g);
    }

    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        return super.keyReleased(keyEvent);
    }

    @Override
    public boolean charTyped(CharacterEvent characterEvent) {
        return super.charTyped(characterEvent);
    }

    @Override
    public void setFocused(boolean bl) {
        super.setFocused(bl);
    }

    @Override
    public boolean isFocused() {
        return super.isFocused();
    }

    @Override
    public boolean shouldTakeFocusAfterInteraction() {
        return super.shouldTakeFocusAfterInteraction();
    }

    @Override
    public @Nullable ComponentPath getCurrentFocusPath() {
        return super.getCurrentFocusPath();
    }

    @Override
    public ScreenRectangle getBorderForArrowNavigation(ScreenDirection screenDirection) {
        return super.getBorderForArrowNavigation(screenDirection);
    }

    @Override
    public @Nullable ComponentPath nextFocusPath(FocusNavigationEvent focusNavigationEvent) {
        return super.nextFocusPath(focusNavigationEvent);
    }

    @Override
    public int getTabOrderGroup() {
        return super.getTabOrderGroup();
    }
}
