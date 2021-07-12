package net.pretronic.dkfriends.minecraft.gui.screens.settings;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.dkfriends.minecraft.gui.Layout;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.input.types.MaxStringLengthPlayerTextInputValidator;
import org.mcnative.runtime.api.service.entity.living.Player;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.EmptyScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

public class SettingsScreen {

    public static void register(ElementList<GuiContext, EmptyScreenContext> elements){

        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.line(0)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.GOLDEN_HELMET)
                        .setDisplayName(Text.of("Friend settings", TextColor.GREEN))
                        .setLore(Text.of("Click here to change friend settings", TextColor.GRAY));
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.root().getGui().openScreen(context.root().getPlayer(), "settings-friend");
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.IRON_CHESTPLATE)
                        .setDisplayName(Text.of("Clan settings", TextColor.AQUA))
                        .setLore(Text.of("Click here to change clan settings", TextColor.GRAY));
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {

            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.CAKE)
                        .setDisplayName(Text.of("Party invites", TextColor.DARK_PURPLE))
                        .setLore(Text.of("Click here to change party invitation settings", TextColor.GRAY));
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {

            }
        });

        Layout.register(elements);
    }
}
