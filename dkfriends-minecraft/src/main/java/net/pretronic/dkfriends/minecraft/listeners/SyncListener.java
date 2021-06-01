package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.event.friend.FriendRemoveEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestAcceptEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestDenyEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.event.party.PartyDeleteEvent;
import net.pretronic.dkfriends.api.event.party.PartyJoinEvent;
import net.pretronic.dkfriends.api.event.party.PartyLeaveEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationAcceptEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationDenyEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInviteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.party.DefaultParty;
import net.pretronic.dkfriends.common.party.DefaultPartyManager;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayer;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.libraries.event.EventPriority;
import net.pretronic.libraries.event.injection.annotations.Inject;
import net.pretronic.libraries.event.network.NetworkListener;

public class SyncListener {

    @Inject
    private DKFriends dkfriends;

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onRequestSend(FriendRequestSendEvent event) {
        DKFriendsPlayer player = dkfriends.getPlayerManager().getLoadedPlayer(event.getPlayerId());
        if(player != null){
            ((DefaultDKFriendsPlayer)player).addFriendRequest(event.getRequest());
        }
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onRequestDeny(FriendRequestDenyEvent event) {
        DKFriendsPlayer player = dkfriends.getPlayerManager().getLoadedPlayer(event.getPlayerId());
        if(player != null){
            ((DefaultDKFriendsPlayer)player).removeFriendRequest(event.getRequest());
        }
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onRequestAccept(FriendRequestAcceptEvent event) {
        DKFriendsPlayer player = dkfriends.getPlayerManager().getLoadedPlayer(event.getPlayerId());
        if(player != null){
            ((DefaultDKFriendsPlayer)player).removeFriendRequest(event.getRequest());
        }
    }


    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onFriendAdd(FriendAddEvent event) {
        DKFriendsPlayer player = dkfriends.getPlayerManager().getLoadedPlayer(event.getPlayerId());
        if(player != null){
            ((DefaultDKFriendsPlayer)player).addFriend(event.getFriend());
        }

        DKFriendsPlayer friend = dkfriends.getPlayerManager().getLoadedPlayer(event.getFriend().getFriendId());
        if(friend != null){
            ((DefaultDKFriendsPlayer)friend).addFriend(new DefaultFriend(dkfriends,friend,event.getFriend().getPlayerId()
                    ,event.getFriend().getFriendSince(),event.getFriend().isFavorite(),event.getFriend().getRelation()));
        }
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onFriendRemove(FriendRemoveEvent event) {
        DKFriendsPlayer player = dkfriends.getPlayerManager().getLoadedPlayer(event.getPlayerId());
        if(player != null){
            ((DefaultDKFriendsPlayer)player).removeFriendInternal(event.getFriend().getFriendId());
        }

        DKFriendsPlayer friend = dkfriends.getPlayerManager().getLoadedPlayer(event.getFriend().getFriendId());
        if(friend != null){
            ((DefaultDKFriendsPlayer)friend).removeFriendInternal(event.getPlayerId());
        }
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyCreate(PartyCreateEvent event) {
        ((DefaultPartyManager)dkfriends.getPartyManager()).addParty(new DefaultParty((DefaultDKFriends) dkfriends
                ,event.getPartyId(),"Unset","Unset",false));
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyDelete(PartyDeleteEvent event) {
        Party party = event.getParty();
        ((DefaultPartyManager)dkfriends.getPartyManager()).removeParty(party);
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyInvite(PartyInviteEvent event) {
        Party party = event.getParty();
        if(party != null) ((DefaultParty)party).addInvitation(event.getInvitation());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyInvite(PartyInvitationAcceptEvent event) {
        Party party = event.getParty();
        if(party != null) ((DefaultParty)party).removeInvitation(event.getInvitation());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyInvite(PartyInvitationDenyEvent event) {
        Party party = event.getParty();
        if(party != null) ((DefaultParty)party).removeInvitation(event.getInvitation());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyInvite(PartyJoinEvent event) {
        Party party = event.getParty();
        if(party != null) ((DefaultParty)party).addMember(event.getMember());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onPartyInvite(PartyLeaveEvent event) {
        Party party = event.getParty();
        if(party != null) ((DefaultParty)party).removeMember(event.getMember());
    }

}
