package net.pretronic.dkfriends.minecraft.gui.screens.clan.list;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
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

import java.net.URI;

public class ClanListScreen {

    public static void register(ElementList<GuiContext, ClanListScreenContext> elements){

        elements.addElement(new BasicElement<GuiContext, ClanListScreenContext>(Slots.of(0,1,2,3,5,6,7,8)) {
            @Override
            protected ItemStack create(ClanListScreenContext context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });
        elements.addElement(new BasicElement<GuiContext, ClanListScreenContext>(Slots.of(4)) {
            @Override
            protected ItemStack create(ClanListScreenContext context) {
                Clan clan = context.getClan();
                return ItemStack.newItemStack(Material.IRON_CHESTPLATE).setDisplayName(Text.parse("§7"+clan.getName()+" §8[§e"+clan.getTag()+"§8]"));
            }
        });

        elements.addElement(new ListPane<GuiContext, ClanListScreenContext, ClanMember>(ClanListScreenContext::getClanMembers, Slots.range(1,4)){
            @Override
            protected ItemStack create(ClanListScreenContext context, int slot, ClanMember clanMember) {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(clanMember.getPlayerId());
                String roleDisplay = " &7("+clanMember.getRole().toString()+")";
                if(player.isOnline()) {
                    return ItemStack.newItemStack(Material.PLAYER_HEAD)
                            .setDisplayName(Text.parse(player.getDisplayName() + roleDisplay))
                            .getData(SkullItemData.class, data -> data.setGameProfile(player.getGameProfile()));
                } else {
                    return ItemStack.newItemStack(Material.SKELETON_SKULL)
                            .setDisplayName(Text.parse(player.getName() + roleDisplay));
                }
            }

            @Override
            public void handleClick(ClanListScreenContext context, MinecraftPlayerInventoryClickEvent event, ClanMember clanMember) {
                if(context.root().getPlayer().getUniqueId().equals(clanMember.getPlayerId())) return;
                context.root().getGui().openScreen(context.root().getPlayer(), "clanMemberProfile", clanMember);
            }
        });

        elements.addElement(new BasicElement<GuiContext, ClanListScreenContext>(Slots.slot(52)) {
            @Override
            protected ItemStack create(ClanListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Previous page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(ClanListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getClanMembers().previousPage();
            }
        });

        elements.addElement(new BasicElement<GuiContext, ClanListScreenContext>(Slots.slot(53)) {
            @Override
            protected ItemStack create(ClanListScreenContext context) {
                GameProfileLoader loader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile profile = loader.getGameProfile(URI.create("http://textures.minecraft.net/texture/f32ca66056b72863e98f7f32bd7d94c7a0d796af691c9ac3a9136331352288f9"));
                return ItemStack.newItemStack(Material.PLAYER_HEAD).setDisplayName(Text.of("Next page"))
                        .getData(SkullItemData.class, data -> data.setGameProfile(profile));
            }

            @Override
            public void handleClick(ClanListScreenContext context, MinecraftPlayerInventoryClickEvent event) {
                context.getClanMembers().nextPage();
            }
        });

        Layout.register(elements);
    }
}
