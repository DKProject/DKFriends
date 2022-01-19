package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.dkfriends.minecraft.player.MinecraftDKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.utils.PlayerHiderVisibility;
import net.pretronic.libraries.event.Listener;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.service.entity.living.Player;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerDropItemEvent;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerInteractEvent;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerJoinEvent;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.EquipmentSlot;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.data.SkullItemData;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.service.world.block.BlockAction;

public class ServiceListener {

    @Listener
    public void onJoin(MinecraftPlayerJoinEvent event) {
        if(DKFriendsConfig.PLAYER_HIDER_ENABLED) {
            Player player = event.getPlayer();

            MinecraftDKFriendsPlayer friendsPlayer = (MinecraftDKFriendsPlayer) player.getAs(DKFriendsPlayer.class);
            player.getInventory().setItem(DKFriendsConfig.PLAYER_HIDER_SLOT, friendsPlayer.getPlayerHidingType().buildItem());
            updatePlayerVisibility(player);
        } else {
            for (ConnectedMinecraftPlayer connectedPlayer : McNative.getInstance().getLocal().getConnectedPlayers()) {
                event.getPlayer().show(connectedPlayer);
            }
        }
        if(DKFriendsConfig.PROFILE_SKULL_ENABLED) {
            Player player = event.getPlayer();

            player.getInventory().setItem(DKFriendsConfig.PROFILE_SKULL_SLOT, buildProfileSkull(player));
        }
    }



    private ItemStack buildProfileSkull(Player player) {
        return ItemStack.newItemStack(Material.PLAYER_HEAD)
                .setDisplayName(Messages.PROFILE_HEAD_DISPLAY_NAME)
                .getData(SkullItemData.class, data -> data.setGameProfile(player.getGameProfile()));
    }

    @Listener
    public void onInteract(MinecraftPlayerInteractEvent event) {
        if((event.getAction() == BlockAction.RIGHT_CLICK_AIR || event.getAction() == BlockAction.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND) {

            System.out.println("Interact " + event.getAction() + ":" + event.getItem().getMaterial().getName());
            Player player = event.getPlayer();
            int slot = player.getInventory().getHeldItemSlot();
            if(DKFriendsConfig.PLAYER_HIDER_ENABLED && slot == DKFriendsConfig.PLAYER_HIDER_SLOT) {
                event.setCancelled(true);
                MinecraftDKFriendsPlayer friendsPlayer = (MinecraftDKFriendsPlayer) player.getAs(DKFriendsPlayer.class);
                PlayerHiderVisibility hidingType = friendsPlayer.setNextPlayerHidingType();

                player.getInventory().setItem(DKFriendsConfig.PLAYER_HIDER_SLOT, hidingType.buildItem());
                updatePlayerVisibility(player);
            } else if(DKFriendsConfig.PROFILE_SKULL_ENABLED && slot == DKFriendsConfig.PROFILE_SKULL_SLOT) {
                event.setCancelled(true);
                McNative.getInstance().getRegistry().getService(GuiManager.class).getGui("dkfriends").open(player, "friendPage");
            }
        }
    }

    @Listener
    public void onInventoryClick(MinecraftPlayerInventoryClickEvent event) {
        if((DKFriendsConfig.PLAYER_HIDER_ENABLED && event.getSlot() == DKFriendsConfig.PLAYER_HIDER_SLOT)
                || (DKFriendsConfig.PROFILE_SKULL_ENABLED && event.getSlot() == DKFriendsConfig.PROFILE_SKULL_SLOT)) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onItemDrop(MinecraftPlayerDropItemEvent event) {
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if((DKFriendsConfig.PLAYER_HIDER_ENABLED && slot == DKFriendsConfig.PLAYER_HIDER_SLOT)
                || (DKFriendsConfig.PROFILE_SKULL_ENABLED && slot == DKFriendsConfig.PROFILE_SKULL_SLOT)) {
            event.setCancelled(true);
        }
    }

    public static void updatePlayerVisibility(Player player) {
        MinecraftDKFriendsPlayer friendsPlayer = (MinecraftDKFriendsPlayer) player.getAs(DKFriendsPlayer.class);
        PlayerHiderVisibility hidingType = friendsPlayer.getPlayerHidingType();
        for (ConnectedMinecraftPlayer connectedPlayer : McNative.getInstance().getLocal().getConnectedPlayers()) {
            if(hidingType == PlayerHiderVisibility.NONE) player.hide(connectedPlayer);
            else if(hidingType == PlayerHiderVisibility.ALL) player.show(connectedPlayer);
            else if(hidingType == PlayerHiderVisibility.VIP) {
                if(connectedPlayer.hasPermission("dkfriends.playerhider.vip")) {
                    player.show(connectedPlayer);
                } else {
                    player.hide(connectedPlayer);
                }
            }
        }
    }
}
