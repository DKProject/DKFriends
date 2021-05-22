package net.pretronic.dkfriends.minecraft.gui.pages;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.minecraft.gui.Layout;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.gui.pane.ListPane;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;

public class FriendPage {

    public static void register(ElementList<GuiContext,FriendPageContext> elements){
        elements.addElement(new ListPane<GuiContext,FriendPageContext, Friend>(FriendPageContext::getFriends, Slots.range(0,4)){
            @Override
            protected ItemStack create(FriendPageContext context, int slot, Friend friend) {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(friend.getFriendId());
                return ItemStack.newItemStack(Material.BOOKSHELF)
                        .setDisplayName(Text.translateAlternateColorCodes('&',player.getDisplayName()));
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.slot(52)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.STONE);
            }

            @Override
            public void handleClick(FriendPageContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getFriends().previousPage();
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.slot(53)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.STONE);
            }

            @Override
            public void handleClick(FriendPageContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getFriends().nextPage();
            }
        });

        Layout.register(elements);
    }
}
