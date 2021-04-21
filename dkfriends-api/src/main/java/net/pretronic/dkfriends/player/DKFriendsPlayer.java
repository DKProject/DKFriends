package net.pretronic.dkfriends.player;

import net.pretronic.dkfriends.clan.Clan;
import net.pretronic.dkfriends.clan.ClanInvitation;
import net.pretronic.dkfriends.party.Party;
import net.pretronic.dkfriends.party.PartyInvitation;
import net.pretronic.dkfriends.party.PartyMember;
import net.pretronic.dkfriends.player.friend.Friend;
import net.pretronic.dkfriends.player.friend.FriendRequest;

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
