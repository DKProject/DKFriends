package net.pretronic.dkfriends.minecraft.gui.screens.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
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
import org.mcnative.runtime.api.text.format.TextColor;

import java.net.URI;

public class PublicPartyList {

    public static void register(ElementList<GuiContext, PublicPartyListScreenContext> elements){

        elements.addElement(new ListPane<GuiContext, PublicPartyListScreenContext, Party>(PublicPartyListScreenContext::getParties, Slots.range(0,4)){
            @Override
            protected ItemStack create(PublicPartyListScreenContext context, int slot, Party party) {
                ItemStack itemStack = ItemStack.newItemStack(Material.CAKE).setDisplayName(Text.of(party.getTopic(), TextColor.GRAY));
                MinecraftPlayer leader = McNative.getInstance().getPlayerManager().getPlayer(party.getLeader().getPlayerId());
                itemStack.addLore(Text.of("Leader", TextColor.GRAY), Text.of(": ", TextColor.DARK_GRAY), Text.parse(leader.getDisplayName()));
                StringBuilder lore = new StringBuilder().append("§7Members§8: ");
                boolean first =  true;
                for (PartyMember member : party.getSortedMembers()) {
                    if(!first) {
                        lore.append("§8,");
                    } else first = false;
                    MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(member.getPlayerId());
                    lore.append(player.getDisplayName());
                }
                itemStack.addLore(Text.parse(lore.toString()));
                return itemStack;
            }
        });

        elements.addElement(new BasicElement<GuiContext, PublicPartyListScreenContext>(Slots.slot(52)) {
            @Override
            protected ItemStack create(PublicPartyListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Previous page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(PublicPartyListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getParties().previousPage();
            }
        });

        elements.addElement(new BasicElement<GuiContext, PublicPartyListScreenContext>(Slots.slot(53)) {
            @Override
            protected ItemStack create(PublicPartyListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/f32ca66056b72863e98f7f32bd7d94c7a0d796af691c9ac3a9136331352288f9"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Next page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(PublicPartyListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getParties().nextPage();
            }
        });

        Layout.register(elements);
    }
}
