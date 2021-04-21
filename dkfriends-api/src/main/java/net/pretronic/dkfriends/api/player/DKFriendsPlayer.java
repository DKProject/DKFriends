package net.pretronic.dkfriends.api.player;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;

import java.util.Collection;
import java.util.UUID;

public interface DKFriendsPlayer {

    Collection<Friend> getFriends();

    Collection<Friend> getOnlineFriends();

    Friend getFriend(UUID friendId);

    boolean isFriend(UUID friendId);


    Friend addFriend(UUID friendId);

    void removeFriend(UUID friendId);


    Collection<FriendRequest> getFriendRequests();

    FriendRequest sendFriendRequest(UUID playerId);

    FriendRequest sendFriendRequest(UUID playerId,String message);

    void acceptAllFriendRequests();

    void denyAllFriendRequests();



    Collection<PlayerBlock> getBlockedPlayers();

    PlayerBlock getBlockedPlayer(UUID playerId);

    boolean isBlocked(UUID playerId);

    PlayerBlock blockPlayer(UUID playerId);

    void unblockPlayer(UUID playerId);



    Collection<PartyInvitation> getPartyInvitations();

    Party getParty();

    PartyMember getPartyMember();

    boolean isInParty();

    void setParty(Party party);

    Party createParty();

    void leaveParty();


    Collection<ClanInvitation> getClanInvitations();

    Clan getClan();

    boolean isInClan();

    void setClan(Clan clan);

    void leaveClan();

}
