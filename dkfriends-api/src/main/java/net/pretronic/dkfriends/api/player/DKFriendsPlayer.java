package net.pretronic.dkfriends.api.player;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DKFriendsPlayer {

    UUID getId();

    String getName();

    boolean isOnline();

    boolean hasPermission(String permission);


    Collection<Friend> getFriends();

    List<Friend> getSortedFriends();


    Friend getFriend(UUID friendId);

    boolean isFriend(UUID friendId);


    Friend addFriend(UUID friendId);


    void removeFriend(UUID friendId);

    void removeFriend(DKFriendsPlayer friend);

    void removeFriend(Friend friend);

    void clearFriends();


    Collection<FriendRequest> getFriendRequests();


    FriendRequest getFriendRequest(UUID requesterId);

    boolean hasFriendRequest(UUID requesterId);

    boolean hasFriendRequest(DKFriendsPlayer player);


    FriendRequest sendFriendRequest(UUID requesterId);

    FriendRequest sendFriendRequest(DKFriendsPlayer requester);


    FriendRequest sendFriendRequest(UUID requesterId,String message);

    FriendRequest sendFriendRequest(DKFriendsPlayer requester,String message);

    void acceptFriendRequest(UUID requesterId);

    void acceptFriendRequest(DKFriendsPlayer requester);

    void acceptFriendRequest(FriendRequest request);


    void denyFriendRequest(UUID requesterId);

    void denyFriendRequest(DKFriendsPlayer requester);

    void denyFriendRequest(FriendRequest request);


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

    ClanInvitation getClanInvitation(String clanName);

    Clan getClan();

    ClanMember getClanMember();

    boolean isInClan();

    boolean leaveClan();


    int getMaxPartySize();

    int getMaxFriends();


    void setSetting(String key, Object value);

    Object getSetting(String key);


    void setActionSetting(String key,String group,boolean value);

    void setActionSetting(String key,boolean value);

    boolean isActionAllow(String setting,DKFriendsPlayer target);

}
