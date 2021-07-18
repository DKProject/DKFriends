package net.pretronic.dkfriends.minecraft.gui.screens.friend;

import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.minecraft.gui.Layout;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.profile.GameProfile;
import org.mcnative.runtime.api.player.profile.GameProfileLoader;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.gui.pane.ListPane;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.data.SkullItemData;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.components.MessageComponent;
import org.mcnative.runtime.api.text.format.TextColor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FriendListScreen {

    public static void register(ElementList<GuiContext, FriendListScreenContext> elements){
        elements.addElement(new ListPane<GuiContext, FriendListScreenContext, Friend>(FriendListScreenContext::getFriends, Slots.range(0,4)){
            @Override
            protected ItemStack create(FriendListScreenContext context, int slot, Friend friend) {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(friend.getFriendId());

                if(player.isOnline()) {
                    return ItemStack.newItemStack(Material.PLAYER_HEAD)
                            .addLore(Text.parse("§aOnline"))
                            .setDisplayName(Text.parse(player.getDisplayName()))
                            .getData(SkullItemData.class, data -> data.setGameProfile(player.getGameProfile()));
                } else {
                    return ItemStack.newItemStack(Material.SKELETON_SKULL)
                            .addLore(Text.parse("§cOffline"))
                            .setDisplayName(Text.of(player.getName(), TextColor.DARK_GRAY));
                }
            }

            @Override
            public void handleClick(FriendListScreenContext context, MinecraftPlayerInventoryClickEvent event, Friend friend) {
                context.root().getGui().openScreen(context.root().getPlayer(), "friendProfile", friend);
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendListScreenContext>(Slots.slot(52)) {
            @Override
            protected ItemStack create(FriendListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Previous page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(FriendListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getFriends().previousPage();
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendListScreenContext>(Slots.slot(53)) {
            @Override
            protected ItemStack create(FriendListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/f32ca66056b72863e98f7f32bd7d94c7a0d796af691c9ac3a9136331352288f9"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Next page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(FriendListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getFriends().nextPage();
            }
        });

        Layout.register(elements);
    }
}
