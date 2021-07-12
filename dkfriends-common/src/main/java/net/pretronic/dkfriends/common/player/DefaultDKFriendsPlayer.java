package net.pretronic.dkfriends.common.player;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.event.friend.FriendRemoveEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestAcceptEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestDenyEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.PlayerBlock;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.friend.DefaultFriendAddEvent;
import net.pretronic.dkfriends.common.event.friend.DefaultFriendRemoveEvent;
import net.pretronic.dkfriends.common.event.friend.request.DefaultFriendRequestAcceptEvent;
import net.pretronic.dkfriends.common.event.friend.request.DefaultFriendRequestDenyEvent;
import net.pretronic.dkfriends.common.event.friend.request.DefaultFriendRequestSendEvent;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.dkfriends.common.player.friend.DefaultFriendRequest;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.annonations.Internal;

import java.util.*;

public abstract class DefaultDKFriendsPlayer implements DKFriendsPlayer {

    private final DefaultDKFriends dkFriends;
    private final UUID uniqueId;

    public DefaultDKFriendsPlayer(DefaultDKFriends dkFriends, UUID uniqueId) {
        this.dkFriends = dkFriends;
        this.uniqueId = uniqueId;
    }

    private Collection<FriendRequest> friendRequests;
    private Collection<Friend> friends;

    @Override
    public UUID getId() {
        return uniqueId;
    }

    @Override
    public Collection<Friend> getFriends() {
        return Collections.unmodifiableCollection(getOrLoadFriends());
    }

    @Override
    public List<Friend> getSortedFriends() {
        List<Friend> friends = new ArrayList<>(getOrLoadFriends());
        friends.sort((o1, o2) -> {
            if(o1.getFriend().isOnline()){
                if(o2.getFriend().isOnline()){
                    if(o1.isFavorite()) return -1;
                    else return 1;
                }else{
                    return -1;
                }
            }else{
                if(o2.getFriend().isOnline()){
                    return 1;
                }else{
                    if(o1.isFavorite()) return -1;
                    else return 1;
                }
            }
        });
        return Collections.unmodifiableList(friends);
    }

    @Override
    public Friend getFriend(UUID friendId) {
        return Iterators.findOne(getOrLoadFriends(), friend -> friend.getFriendId().equals(friendId));
    }

    @Override
    public boolean isFriend(UUID friendId) {
        return getFriend(friendId) != null;
    }

    @Override
    public Friend addFriend(UUID friendId) {
        if(isFriend(friendId)) throw new IllegalArgumentException("Already friends");

        Friend friend = new DefaultFriend(dkFriends,this,friendId,System.currentTimeMillis(),false,null);

        FriendAddEvent event = new DefaultFriendAddEvent(friend);
        dkFriends.getEventBus().callEvent(FriendAddEvent.class,event);
        if(event.isCancelled()) return null;

        //@Todo optimize
        dkFriends.getStorage().getFriends().insert()
                .set("PlayerId",uniqueId)
                .set("FriendId",friendId)
                .set("Favorite",friend.isFavorite())
                .set("Relation",friend.getRelation())
                .set("Time",friend.getFriendSince())
                .execute();
        dkFriends.getStorage().getFriends().insert()
                .set("PlayerId",friendId)
                .set("FriendId",uniqueId)
                .set("Favorite",friend.isFavorite())
                .set("Relation",friend.getRelation())
                .set("Time",friend.getFriendSince())
                .execute();

        this.friends.add(friend);
        DKFriendsPlayer player = dkFriends.getPlayerManager().getLoadedPlayer(friendId);
        if(player instanceof DefaultDKFriendsPlayer) {
            ((DefaultDKFriendsPlayer) player).addFriend(new DefaultFriend(dkFriends,player, uniqueId
                    ,friend.getFriendSince(),friend.isFavorite(),friend.getRelation()));
        }

        FriendRequest request = getFriendRequest(friendId);
        if(request != null) deleteRequest(request);

        return friend;
    }

    @Internal
    public void addFriend(Friend friend){
        if(friends != null) this.friends.add(friend);
    }

    @Override
    public void removeFriend(UUID friendId) {
        removeFriend(Iterators.findOne(this.friends, friend -> friend.getFriendId().equals(friendId)));
    }

    @Override
    public void removeFriend(DKFriendsPlayer friend) {
        removeFriend(friend.getId());
    }

    @Override
    public void removeFriend(Friend friend) {
        if(!friend.getPlayerId().equals(uniqueId)) throw new IllegalArgumentException("Friend does not belong to this player");

        FriendRemoveEvent event = new DefaultFriendRemoveEvent(friend);
        dkFriends.getEventBus().callEvent(FriendRemoveEvent.class,event);
        if(event.isCancelled()) return;

        dkFriends.getStorage().getFriends().delete().where("PlayerId",uniqueId).where("FriendId",friend.getFriendId()).execute();
        dkFriends.getStorage().getFriends().delete().where("PlayerId",friend.getFriendId()).where("FriendId",uniqueId).execute();

        this.friends.remove(friend);
        DKFriendsPlayer player = dkFriends.getPlayerManager().getLoadedPlayer(friend.getFriendId());
        if(player instanceof DefaultDKFriendsPlayer) {
            ((DefaultDKFriendsPlayer)player).removeFriendInternal(friend.getFriendId());
        }
    }

    @Internal
    public void removeFriendInternal(UUID uniqueId){
        if(this.friends != null) Iterators.removeOne(this.friends, friend -> friend.getFriendId().equals(uniqueId));
    }

    @Override
    public void clearFriends() {
        for (Friend friend : getOrLoadFriends()) removeFriend(friend);
    }

    @Override
    public Collection<FriendRequest> getFriendRequests() {
        return Collections.unmodifiableCollection(getOrLoadFriendRequests());
    }

    @Override
    public FriendRequest getFriendRequest(UUID requesterId) {
        return Iterators.findOne(getOrLoadFriendRequests(), request -> request.getRequesterId().equals(requesterId));
    }

    @Override
    public boolean hasFriendRequest(UUID requesterId) {
        return getFriendRequest(requesterId) != null;
    }

    @Override
    public boolean hasFriendRequest(DKFriendsPlayer player) {
        return hasFriendRequest(player.getId());
    }

    @Override
    public FriendRequest sendFriendRequest(UUID requesterId) {
        return sendFriendRequest(requesterId,null);
    }

    @Override
    public FriendRequest sendFriendRequest(DKFriendsPlayer player) {
        return sendFriendRequest(player,null);
    }

    @Override
    public FriendRequest sendFriendRequest(DKFriendsPlayer player, String message) {
        return sendFriendRequest(player.getId(),message);
    }

    @Override
    public void acceptFriendRequest(UUID requesterId) {
        FriendRequest request = getFriendRequest(requesterId);
        if(request == null) throw new IllegalArgumentException("No friend request");
        acceptFriendRequest(request);
    }

    @Override
    public void acceptFriendRequest(DKFriendsPlayer requester) {
        acceptFriendRequest(requester.getId());
    }

    @Override
    public void acceptFriendRequest(FriendRequest request) {
        if(!request.getReceiverId().equals(uniqueId)) throw new IllegalArgumentException("Request does not belong to this player");
        deleteRequest(request);

        FriendRequestAcceptEvent event = new DefaultFriendRequestAcceptEvent(request);
        dkFriends.getEventBus().callEvent(FriendRequestAcceptEvent.class,event);

        addFriend(request.getRequesterId());
    }

    @Override
    public void denyFriendRequest(UUID requesterId) {
        FriendRequest request = getFriendRequest(requesterId);
        if(request == null) throw new IllegalArgumentException("No friend request");
        denyFriendRequest(request);
    }

    @Override
    public void denyFriendRequest(DKFriendsPlayer requester) {
        denyFriendRequest(requester.getId());
    }

    @Override
    public void denyFriendRequest(FriendRequest request) {
        if(!request.getReceiverId().equals(uniqueId)) throw new IllegalArgumentException("Request does not belong to this player");
        deleteRequest(request);

        FriendRequestDenyEvent event = new DefaultFriendRequestDenyEvent(request);
        dkFriends.getEventBus().callEvent(FriendRequestDenyEvent.class,event);
    }

    private void deleteRequest(FriendRequest request){
        dkFriends.getStorage().getFriendRequests().delete()
                .where("ReceiverId",request.getReceiverId())
                .where("RequesterId",request.getRequesterId())
                .execute();
        this.friendRequests.remove(request);
    }

    @Override
    public FriendRequest sendFriendRequest(UUID requesterId, String message) {
        if(hasFriendRequest(requesterId)) throw new IllegalArgumentException("Has already a friend request");
        if(isFriend(requesterId)) throw new IllegalArgumentException("Already friend");

        FriendRequest request = new DefaultFriendRequest(dkFriends,this,requesterId,message,System.currentTimeMillis());

        FriendRequestSendEvent event = new DefaultFriendRequestSendEvent(request);
        dkFriends.getEventBus().callEvent(FriendRequestSendEvent.class,event);
        if(event.isCancelled()) return null;

        dkFriends.getStorage().getFriendRequests().insert()
                .set("ReceiverId",uniqueId)
                .set("RequesterId",request.getRequesterId())
                .set("Message",request.getMessage())
                .set("Time",request.getRequestTime())
                .execute();

        this.friendRequests.add(request);

        return request;
    }

    @Override
    public void acceptAllFriendRequests() {
        for (FriendRequest request : getOrLoadFriendRequests()) acceptFriendRequest(request);
    }

    @Override
    public void denyAllFriendRequests() {
        for (FriendRequest request : getOrLoadFriendRequests()) denyFriendRequest(request);
    }

    @Override
    public Collection<PlayerBlock> getBlockedPlayers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlayerBlock getBlockedPlayer(UUID playerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlocked(UUID playerId) {
        return getBlockedPlayer(playerId) != null;
    }

    @Override
    public PlayerBlock blockPlayer(UUID playerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unblockPlayer(UUID playerId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<PartyInvitation> getPartyInvitations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Party getParty() {
        return dkFriends.getPartyManager().getPartyForPlayer(uniqueId);
    }

    @Override
    public PartyMember getPartyMember() {
        return dkFriends.getPartyManager().getPartyForPlayerAsMember(uniqueId);
    }

    @Override
    public boolean isInParty() {
        return getParty() != null;
    }

    @Override
    public void setParty(Party party) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Party createParty() {
        return dkFriends.getPartyManager().createParty(this);
    }

    @Override
    public void leaveParty() {
        Party party = getParty();
        if(party != null) party.leaveMember(uniqueId);
    }


    @Override
    public Collection<ClanInvitation> getClanInvitations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClanInvitation getClanInvitation(String clanName) {
        Clan clan = this.dkFriends.getClanManager().getClan(clanName);
        if(clan == null) return null;
        return clan.getInvitation(getId());
    }

    @Override
    public Clan getClan() {
        return this.dkFriends.getClanManager().getClanByPlayer(this.uniqueId);
    }

    @Override
    public ClanMember getClanMember() {
        Clan clan = getClan();
        if(clan == null) return null;
        return clan.getMember(this.uniqueId);
    }

    @Override
    public boolean isInClan() {
        return getClan() != null;
    }

    @Override
    public boolean leaveClan() {
        ClanMember member = getClanMember();
        if(member == null) return false;
        member.leave();
        return true;
    }

    @Internal
    public void addFriendRequest(FriendRequest request){
        if(this.friendRequests != null) this.friendRequests.add(request);
    }

    @Internal
    public void removeFriendRequest(FriendRequest request){
        if(this.friendRequests != null) Iterators.remove(this.friendRequests, request0 -> request0.getRequesterId().equals(request.getRequesterId()));
    }

    private Collection<Friend> getOrLoadFriends(){
        if(this.friends == null){
            this.friends = new ArrayList<>();
            dkFriends.getStorage().getFriends().find()
                    .where("PlayerId",uniqueId)
                    .execute().loadIn(this.friends, friend -> new DefaultFriend(dkFriends, DefaultDKFriendsPlayer.this
                            ,friend.getUniqueId("FriendId")
                            ,friend.getLong("Time")
                            ,friend.getBoolean("Favorite")
                            ,friend.getString("Relation")));
        }
        return this.friends;
    }

    private Collection<FriendRequest> getOrLoadFriendRequests(){
        if(this.friendRequests == null){
            this.friendRequests = new ArrayList<>();
            dkFriends.getStorage().getFriendRequests().find()
                    .where("ReceiverId",uniqueId)
                    .execute().loadIn(this.friendRequests, friend -> new DefaultFriendRequest(dkFriends,this
                    ,friend.getUniqueId("RequesterId")
                    ,friend.getString("Message")
                    ,friend.getLong("Time")
            ));
        }
        return this.friendRequests;
    }

    public DefaultDKFriends getDKFriends() {
        return dkFriends;
    }
}
