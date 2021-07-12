package net.pretronic.dkfriends.minecraft.gui.screens.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.profile.GameProfileLoader;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.data.SkullItemData;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

import java.net.URI;

public class FriendProfileScreen {

    public static void register(ElementList<GuiContext, FriendProfileScreenContext> elements) {
        elements.addElement(new BasicElement<GuiContext, FriendProfileScreenContext>(Slots.lines(0,2)) {
            @Override
            protected ItemStack create(FriendProfileScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE)
                        .setDisplayName(Text.parse("§a"));
            }
        });
        elements.addElement(new BasicElement<GuiContext, FriendProfileScreenContext>(Slots.slot(10)) {
            @Override
            protected ItemStack create(FriendProfileScreenContext context) {
                MinecraftPlayer target = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedFriend().getFriendId());
                return ItemStack.newItemStack(target.isOnline() ? Material.PLAYER_HEAD : Material.SKELETON_SKULL)
                        .setDisplayName(Text.parse(target.getDisplayName()))
                        .getData(SkullItemData.class, data -> {
                           if(target.isOnline()) {
                               data.setGameProfile(target.getGameProfile());
                           }
                        });
            }
        });
        elements.addElement(new BasicElement<GuiContext, FriendProfileScreenContext>(Slots.slot(11)) {
            @Override
            protected ItemStack create(FriendProfileScreenContext friendPageContext) {
                return ItemStack.newItemStack(Material.IRON_CHESTPLATE).setDisplayName(Text.of("Invite to clan", TextColor.AQUA));
            }

            @Override
            public void handleClick(FriendProfileScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = event.getOnlinePlayer().getAs(DKFriendsPlayer.class);
                if(player.isInClan()) {
                    player.getClan().sendInvitation(player, context.getSelectedFriend().getFriendId());
                }
            }
        });
        elements.addElement(new BasicElement<GuiContext, FriendProfileScreenContext>(Slots.slot(15)) {
            @Override
            protected ItemStack create(FriendProfileScreenContext friendPageContext) {
                Friend friend = friendPageContext.getSelectedFriend();
                String displayName = friend.isFavorite() ? "§cRemove as favorite" : "§aAdd as favorite";
                GameProfileLoader profileLoader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.parse(displayName))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profileLoader.getGameProfile(
                                URI.create("http://textures.minecraft.net/texture/2f8aa0f659534e8ca566bd27ed61c5f8ce1e4b1f8514a19271d7a6325a840cb"))));
            }

            @Override
            public void handleClick(FriendProfileScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                Friend friend = context.getSelectedFriend();
                friend.setFavorite(!friend.isFavorite());
            }
        });
        elements.addElement(new BasicElement<GuiContext, FriendProfileScreenContext>(Slots.slot(16)) {
            @Override
            protected ItemStack create(FriendProfileScreenContext friendPageContext) {
                return ItemStack.newItemStack(Material.BARRIER).setDisplayName(Text.of("Remove friend", TextColor.RED));
            }

            @Override
            public void handleClick(FriendProfileScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                Friend friend = context.getSelectedFriend();
                friend.remove();
                context.root().getGui().open(context.root().getPlayer(), "friends");
            }
        });
    }
}
