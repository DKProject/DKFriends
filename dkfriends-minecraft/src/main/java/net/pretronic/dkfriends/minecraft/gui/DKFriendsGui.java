package net.pretronic.dkfriends.minecraft.gui;

import net.pretronic.dkfriends.minecraft.gui.screens.clan.ClanListScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.ClanListScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.ClanMemberProfileScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.clan.ClanMemberProfileScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendListScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendListScreenContext;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendSettingsScreen;
import net.pretronic.dkfriends.minecraft.gui.screens.friend.FriendSettingsScreenContext;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;

public class DKFriendsGui {

    public static void register(){
        GuiManager manager = McNative.getInstance().getRegistry().getService(GuiManager.class);

        manager.createGui("dkfriends", GuiContext.class, builder -> {
            builder.setDefaultPage("friendPage");
            builder.createScreen("friends", 54, FriendListScreenContext.class, context -> "§eFriends", FriendListScreen::register);
            builder.createScreen("friendSettings", 27, FriendSettingsScreenContext.class, context -> {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedFriend().getPlayerId());
                return "§7Friend settings for " + player.getDisplayName();
            }, FriendSettingsScreen::register);
            builder.registerPage("friendPage", "friends");

            builder.createScreen("clanMembers", 54, ClanListScreenContext.class, context -> "§8Clan menu", ClanListScreen::register);
            builder.createScreen("clanMemberProfile", 27, ClanMemberProfileScreenContext.class, context -> {
                MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(context.getSelectedClanMember().getPlayerId());
                return player.getDisplayName() + " §8(§7"+context.getSelectedClanMember().getRole()+"§8)";
            }, ClanMemberProfileScreen::register);
            builder.registerPage("clanPage", "clanMembers");
        });
    }

}
