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

public class PartySettingsScreen {

    public static void register(ElementList<GuiContext, EmptyScreenContext> elements){
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.lines(0,3)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });

        //Party invitation
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(13)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.CAKE)
                        .setDisplayName(Text.of("Party invitations", TextColor.YELLOW))
                        .setLore(Text.of("Allow or disallow party invitations", TextColor.GRAY));
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.PARTY_ALLOW_INVITATIONS, "all");
                if(settingEnabled) {
                    return ItemStack.newItemStack(Material.LIME_DYE)
                            .setDisplayName(Text.of("✓", TextColor.GREEN));
                } else {
                    return ItemStack.newItemStack(Material.LIME_DYE)
                            .setDisplayName(Text.of("❌", TextColor.RED));
                }
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                boolean settingEnabled = player.getActionSetting(PlayerSettings.PARTY_ALLOW_INVITATIONS, "all");
                player.setActionSetting(PlayerSettings.PARTY_ALLOW_INVITATIONS, "all", !settingEnabled);
                reopenInventory(context);
            }
        });
    }

    private static void reopenInventory(EmptyScreenContext context) {
        context.root().getGui().openScreen(context.root().getPlayer(), "settings-party");
    }
}
