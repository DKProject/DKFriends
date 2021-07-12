package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.common.clan.DefaultClan;
import net.pretronic.dkfriends.common.clan.DefaultClanInvitation;
import net.pretronic.dkfriends.common.clan.DefaultClanMember;
import net.pretronic.dkfriends.common.party.DefaultParty;
import net.pretronic.dkfriends.common.party.DefaultPartyInvitation;
import net.pretronic.dkfriends.common.party.DefaultPartyMember;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.player.MinecraftDKFriendsPlayer;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.dkfriends.common.player.friend.DefaultFriendRequest;
import net.pretronic.libraries.message.bml.variable.describer.VariableDescriber;
import net.pretronic.libraries.message.bml.variable.describer.VariableDescriberRegistry;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.function.Function;

public class DescriberRegistrar {

    public static void register(){
        VariableDescriberRegistry.registerDescriber(DefaultFriend.class);
        VariableDescriberRegistry.registerDescriber(DefaultFriendRequest.class);
        VariableDescriberRegistry.registerDescriber(DefaultPartyInvitation.class);

        VariableDescriber<DefaultParty> partyDescriber = VariableDescriberRegistry.registerDescriber(DefaultParty.class);
        partyDescriber.registerFunction("public", DefaultParty::isPublic);

        VariableDescriber<DefaultPartyMember> memberDescriber = VariableDescriberRegistry.registerDescriber(DefaultPartyMember.class);
        memberDescriber.setForwardFunction(DefaultPartyMember::getPlayer);

        VariableDescriber<DefaultFriend> friendDescriber = VariableDescriberRegistry.registerDescriber(DefaultFriend.class);
        friendDescriber.setForwardFunction(DefaultFriend::getFriend);
        friendDescriber.registerFunction("isFavorite", DefaultFriend::isFavorite);

        VariableDescriber<MinecraftDKFriendsPlayer> playerDescriber = VariableDescriberRegistry.registerDescriber(MinecraftDKFriendsPlayer.class);
        playerDescriber.setForwardFunction(MinecraftDKFriendsPlayer::getMinecraftPlayer);
        playerDescriber.registerFunction("isOnline", MinecraftDKFriendsPlayer::isOnline);
        playerDescriber.registerFunction("lastLogin", player -> DKFriendsConfig.FORMAT_DATE.format(player.getMinecraftPlayer().getUniqueId()));

        VariableDescriberRegistry.registerDescriber(DefaultClan.class);
        VariableDescriberRegistry.registerDescriber(DefaultClanInvitation.class);

        VariableDescriber<DefaultClanMember> clanMemberDescriber = VariableDescriberRegistry.registerDescriber(DefaultClanMember.class);
        clanMemberDescriber.setForwardFunction(DefaultClanMember::getPlayer);
    }

}
