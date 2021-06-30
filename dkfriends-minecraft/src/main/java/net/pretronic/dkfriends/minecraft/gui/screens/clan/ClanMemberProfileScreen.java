package net.pretronic.dkfriends.minecraft.gui.screens.clan;

import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.ClickType;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.data.SkullItemData;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.components.MessageComponent;
import org.mcnative.runtime.api.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

public class ClanMemberProfileScreen {

    public static void register(ElementList<GuiContext, ClanMemberProfileScreenContext> elements) {
        elements.addElement(new BasicElement<GuiContext, ClanMemberProfileScreenContext>(Slots.lines(0,2)) {
            @Override
            protected ItemStack create(ClanMemberProfileScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE)
                        .setDisplayName(Text.parse("§a"));
            }
        });
        elements.addElement(new BasicElement<GuiContext, ClanMemberProfileScreenContext>(Slots.slot(13)) {
            @Override
            protected ItemStack create(ClanMemberProfileScreenContext context) {
                String roleDisplay = " &7("+context.getSelectedClanMember().getRole().toString()+")";
                MinecraftPlayer target = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedClanMember().getPlayerId());
                return ItemStack.newItemStack(target.isOnline() ? Material.PLAYER_HEAD : Material.SKELETON_SKULL)
                        .setDisplayName(Text.parse(target.getDisplayName() + roleDisplay))
                        .getData(SkullItemData.class, data -> {
                            if(target.isOnline()) {
                                data.setGameProfile(target.getGameProfile());
                            }
                        });
            }
        });
        elements.addElement(new BasicElement<GuiContext, ClanMemberProfileScreenContext>(Slots.slot(16)) {
            @Override
            protected ItemStack create(ClanMemberProfileScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                if(!context.getSelectedClanMember().canKick(player.getClanMember())) return null;
                return ItemStack.newItemStack(Material.BARRIER).setDisplayName(Text.of("Kick from clan", TextColor.RED));
            }

            @Override
            public void handleClick(ClanMemberProfileScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                if(!context.getSelectedClanMember().canKick(player.getClanMember())) return;
                context.getSelectedClanMember().kick(event.getPlayer().getAs(DKFriendsPlayer.class).getClanMember());
            }
        });
        elements.addElement(new BasicElement<GuiContext, ClanMemberProfileScreenContext>(Slots.slot(10)) {
            @Override
            protected ItemStack create(ClanMemberProfileScreenContext context) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                if(!context.getSelectedClanMember().canPromote(player.getClanMember())
                        && !context.getSelectedClanMember().canDemote(player.getClanMember())) return null;

                List<MessageComponent<?>> lore = new ArrayList<>();
                if(context.getSelectedClanMember().canPromote(player.getClanMember()) ) {
                    lore.add(Text.of("Left click to promote", TextColor.GRAY));
                } else if(context.getSelectedClanMember().canDemote(player.getClanMember())) {
                    lore.add(Text.of("Right click to demote", TextColor.GRAY));
                }
                return ItemStack.newItemStack(Material.WRITABLE_BOOK)
                        .setDisplayName(Text.of("Change role", TextColor.RED))
                        .setLore(lore);
            }

            @Override
            public void handleClick(ClanMemberProfileScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                DKFriendsPlayer player = context.root().getPlayer().getAs(DKFriendsPlayer.class);
                ClanMember playerClanMember = player.getClanMember();
                if(event.getClickType() == ClickType.LEFT && context.getSelectedClanMember().canPromote(playerClanMember)) {
                    context.getSelectedClanMember().promote(playerClanMember);
                } else if(event.getClickType() == ClickType.RIGHT && context.getSelectedClanMember().canDemote(playerClanMember)) {
                    context.getSelectedClanMember().demote(playerClanMember);
                }
            }
        });

    }
}
