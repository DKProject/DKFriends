package net.pretronic.dkfriends.minecraft.gui.screens.party;

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

public class PartyListScreen {

    public static void register(ElementList<GuiContext, PartyListScreenContext> elements){

        elements.addElement(new BasicElement<GuiContext, PartyListScreenContext>(Slots.of(0,1,2,3,5,6,7,8)) {
            @Override
            protected ItemStack create(PartyListScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });
        elements.addElement(new BasicElement<GuiContext, PartyListScreenContext>(Slots.of(4)) {
            @Override
            protected ItemStack create(PartyListScreenContext context) {
                String leaderDisplayName = McNative.getInstance().getPlayerManager().getPlayer(context.getParty().getLeader().getPlayerId()).getDisplayName();
                return ItemStack.newItemStack(Material.CAKE).setDisplayName(Text.of("Party of " + leaderDisplayName, TextColor.DARK_PURPLE));
            }
        });

        elements.addElement(new ListPane<GuiContext, PartyListScreenContext, PartyMember>(PartyListScreenContext::getPartyMembers, Slots.range(1,4)){
            @Override
            protected ItemStack create(PartyListScreenContext context, int slot, PartyMember member) {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(member.getPlayerId());
                String roleDisplay = " &7("+member.getRole().toString()+")";
                return ItemStack.newItemStack(Material.PLAYER_HEAD)
                        .setDisplayName(Text.parse(player.getDisplayName() + roleDisplay))
                        .getData(SkullItemData.class, data -> data.setGameProfile(player.getGameProfile()));
            }
        });

        elements.addElement(new BasicElement<GuiContext, PartyListScreenContext>(Slots.slot(52)) {
            @Override
            protected ItemStack create(PartyListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Previous page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(PartyListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getPartyMembers().previousPage();
            }
        });

        elements.addElement(new BasicElement<GuiContext, PartyListScreenContext>(Slots.slot(53)) {
            @Override
            protected ItemStack create(PartyListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/f32ca66056b72863e98f7f32bd7d94c7a0d796af691c9ac3a9136331352288f9"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Next page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(PartyListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getPartyMembers().nextPage();
            }
        });

        Layout.register(elements);
    }
}
