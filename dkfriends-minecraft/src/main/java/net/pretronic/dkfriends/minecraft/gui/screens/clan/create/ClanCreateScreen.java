package net.pretronic.dkfriends.minecraft.gui.screens.clan.create;

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
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

public class ClanCreateScreen {

    public static void register(DKFriends dkFriends, ElementList<GuiContext, EmptyScreenContext> elements){

        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.line(0)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });
        elements.addElement(new BasicElement<GuiContext, EmptyScreenContext>(Slots.of(22)) {
            @Override
            protected ItemStack create(EmptyScreenContext context) {
                return ItemStack.newItemStack(Material.ANVIL)
                        .setDisplayName(Text.of("Create a clan", TextColor.AQUA))
                        .setLore(Text.of("Click here to create a new clan", TextColor.GRAY));
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                ConnectedMinecraftPlayer player = context.root().getPlayer();
                ((Player)player).closeInventory();
                player.requestTextInput("Type in your clan name. Write abort to abort the process", "name", clanName -> {
                    if(clanName.equalsIgnoreCase("abort")) return;
                    player.requestTextInput("Type in your clan tag. Write abort to abort the process", "name", clanTag -> {
                        if(clanName.equalsIgnoreCase("abort")) return;
                        Clan clan = dkFriends.getClanManager().createClan(clanName, clanTag);

                        if(clan == null) {
                            player.sendMessage(Messages.COMMAND_CLAN_CREATE_ALREADY_EXISTS, VariableSet.create()
                                    .add("name", clanName)
                                    .add("tag", clanTag));
                            return;
                        }
                        clan.addMember(player.getUniqueId(), ClanRole.LEADER);

                        player.sendMessage(Messages.COMMAND_CLAN_CREATE, VariableSet.create().addDescribed("clan", clan));
                    }, new MaxStringLengthPlayerTextInputValidator(16));
                }, new MaxStringLengthPlayerTextInputValidator(16));
            }
        });

        Layout.register(elements);
    }
}
