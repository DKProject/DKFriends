package net.pretronic.dkfriends.minecraft.gui.screens.settings;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
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

public class ClanSettingsScreen {

    public static void register(ElementList<GuiContext, EmptyScreenContext> elements){
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.lines(0,3)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });

        //Clan chat
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(11)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.BOOK)
                        .setDisplayName(Text.of("Show clan chat", TextColor.YELLOW))
                        .setLore(Text.of("Allow or disallow clan chat", TextColor.GRAY));
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(20)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_ALLOW_MESSAGES, "all");
                if(settingEnabled) {
                    return ItemStack.newItemStack(Material.LIME_DYE)
                            .setDisplayName(Text.of("✓", TextColor.GREEN));
                } else {
                    return ItemStack.newItemStack(Material.RED_DYE)
                            .setDisplayName(Text.of("❌", TextColor.RED));
                }
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_ALLOW_MESSAGES, "all");
                player.setActionSetting(PlayerSettings.CLAN_ALLOW_MESSAGES, "all", !settingEnabled);
                reopenInventory(context);
            }
        });
        //Notifications
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(13)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.PAPER)
                        .setDisplayName(Text.of("Show notifications", TextColor.YELLOW))
                        .setLore(Text.of("Enable or disable notifications", TextColor.GRAY));
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_NOTIFICATIONS, "all");
                if(settingEnabled) {
                    return ItemStack.newItemStack(Material.LIME_DYE)
                            .setDisplayName(Text.of("✓", TextColor.GREEN));
                } else {
                    return ItemStack.newItemStack(Material.RED_DYE)
                            .setDisplayName(Text.of("❌", TextColor.RED));
                }
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_NOTIFICATIONS, "all");
                player.setActionSetting(PlayerSettings.CLAN_NOTIFICATIONS, "all", !settingEnabled);
                reopenInventory(context);
            }
        });
        //Jumping
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(15)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.ENDER_PEARL)
                        .setDisplayName(Text.of("Allow jumping", TextColor.YELLOW))
                        .setLore(Text.of("Choose whether your clan members are able to jump to you", TextColor.GRAY));
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(24)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_ALLOW_JUMP, "all");
                if(settingEnabled) {
                    return ItemStack.newItemStack(Material.LIME_DYE)
                            .setDisplayName(Text.of("✓", TextColor.GREEN));
                } else {
                    return ItemStack.newItemStack(Material.RED_DYE)
                            .setDisplayName(Text.of("❌", TextColor.RED));
                }
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.CLAN_ALLOW_JUMP, "all");
                player.setActionSetting(PlayerSettings.CLAN_ALLOW_JUMP, "all", !settingEnabled);
                reopenInventory(context);
            }
        });
    }

    private static void reopenInventory(EmptyScreenContext context) {
        context.root().getGui().openScreen(context.root().getPlayer(), "settings-clan");
    }
}
