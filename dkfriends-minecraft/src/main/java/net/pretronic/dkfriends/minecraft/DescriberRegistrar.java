package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.common.clan.DefaultClan;
import net.pretronic.dkfriends.common.clan.DefaultClanInvitation;
import net.pretronic.dkfriends.common.clan.DefaultClanMember;
import net.pretronic.dkfriends.common.party.DefaultParty;
import net.pretronic.dkfriends.common.party.DefaultPartyInvitation;
import net.pretronic.dkfriends.common.party.DefaultPartyMember;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayer;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.dkfriends.common.player.friend.DefaultFriendRequest;
import net.pretronic.libraries.message.bml.variable.describer.VariableDescriber;
import net.pretronic.libraries.message.bml.variable.describer.VariableDescriberRegistry;
import org.mcnative.runtime.api.McNative;

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

        VariableDescriber<DefaultDKFriendsPlayer> playerDescriber = VariableDescriberRegistry.registerDescriber(DefaultDKFriendsPlayer.class);
        playerDescriber.setForwardFunction(player -> McNative.getInstance().getPlayerManager().getPlayer(player.getId()));//@Todo optimize with holder caching

        VariableDescriberRegistry.registerDescriber(DefaultClan.class);
        VariableDescriberRegistry.registerDescriber(DefaultClanInvitation.class);

        VariableDescriber<DefaultClanMember> clanMemberDescriber = VariableDescriberRegistry.registerDescriber(DefaultClanMember.class);
        clanMemberDescriber.setForwardFunction(DefaultClanMember::getPlayer);
    }

}
