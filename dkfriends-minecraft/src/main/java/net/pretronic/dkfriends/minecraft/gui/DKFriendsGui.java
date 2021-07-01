package net.pretronic.dkfriends.minecraft.gui;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.create.ClanCreateScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.list.ClanListScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.list.ClanListScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.profile.ClanMemberProfileScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.profile.ClanMemberProfileScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendListScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendListScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendSettingsScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendSettingsScreenContext;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.EmptyScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

import java.util.function.Consumer;

public class DKFriendsGui {

    public static void register(DKFriends dkFriends){
        GuiManager manager = McNative.getInstance().getRegistry().getService(GuiManager.class);

        manager.createGui("dkfriends", GuiContext.class, builder -> {
            builder.setDefaultPage("friendPage");
            builder.createScreen("friends", 54, FriendListScreenContext.class, context -> Text.of("Friends", TextColor.GREEN), FriendListScreen::register);
            builder.createScreen("friendSettings", 27, FriendSettingsScreenContext.class, context -> {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedFriend().getFriendId());
                return Text.parse("§7Settings for " + player.getDisplayName());
            }, FriendSettingsScreen::register);
            builder.registerPage("friendPage", "friends");

            builder.createScreen("clanMembers", 54, ClanListScreenContext.class, context -> Text.of("Clan menu", TextColor.AQUA), ClanListScreen::register);
            builder.createScreen("clanMemberProfile", 27, ClanMemberProfileScreenContext.class, context -> {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedClanMember().getPlayerId());
                return Text.parse( player.getDisplayName() + " §8(§7"+context.getSelectedClanMember().getRole()+"§8)");
            }, ClanMemberProfileScreen::register);
            builder.createScreen("clanCreate", 54, EmptyScreenContext.class, context -> Text.of("Clan menu", TextColor.AQUA),
                    elementList -> ClanCreateScreen.register(dkFriends, elementList));
            builder.registerPage("clanPage", context -> {
                DKFriendsPlayer player = context.getPlayer().getAs(DKFriendsPlayer.class);
                if(player.getClan() == null) return "clanCreate";
                else return "clanMembers";
            });
        });
    }

}
