package net.pretronic.dkfriends.common.player;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.PlayerBlock;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

import java.util.Collection;
import java.util.UUID;

public class DefaultDKFriendsPlayer implements DKFriendsPlayer {




    @Override
    public Collection<Friend> getFriends() {
        return null;
    }

    @Override
    public Collection<Friend> getOnlineFriends() {
        return null;
    }

    @Override
    public Friend getFriend(UUID friendId) {
        return null;
    }

    @Override
    public boolean isFriend(UUID friendId) {
        return getFriend(friendId) != null;
    }

    @Override
    public Friend addFriend(UUID friendId) {
        return null;
    }

    @Override
    public void removeFriend(UUID friendId) {

    }

    @Override
    public Collection<FriendRequest> getFriendRequests() {
        return null;
    }

    @Override
    public FriendRequest sendFriendRequest(UUID playerId) {
        return null;
    }

    @Override
    public FriendRequest sendFriendRequest(UUID playerId, String message) {
        return null;
    }

    @Override
    public void acceptAllFriendRequests() {

    }

    @Override
    public void denyAllFriendRequests() {

    }

    @Override
    public Collection<PlayerBlock> getBlockedPlayers() {
        return null;
    }

    @Override
    public PlayerBlock getBlockedPlayer(UUID playerId) {
        return null;
    }

    @Override
    public boolean isBlocked(UUID playerId) {
        return getBlockedPlayer(playerId) != null;
    }

    @Override
    public PlayerBlock blockPlayer(UUID playerId) {
        return null;
    }

    @Override
    public void unblockPlayer(UUID playerId) {

    }

    @Override
    public Collection<PartyInvitation> getPartyInvitations() {
        return null;
    }

    @Override
    public Party getParty() {
        return null;
    }

    @Override
    public PartyMember getPartyMember() {
        return null;
    }

    @Override
    public boolean isInParty() {
        return false;
    }

    @Override
    public void setParty(Party party) {

    }

    @Override
    public Party createParty() {
        return null;
    }

    @Override
    public void leaveParty() {

    }

    @Override
    public Collection<ClanInvitation> getClanInvitations() {
        return null;
    }

    @Override
    public Clan getClan() {
        return null;
    }

    @Override
    public boolean isInClan() {
        return false;
    }

    @Override
    public void setClan(Clan clan) {

    }

    @Override
    public void leaveClan() {

    }
}
