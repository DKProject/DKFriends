package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.event.clan.ClanDeleteEvent;
import net.pretronic.dkfriends.api.event.clan.member.*;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeNameEvent;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeStatusEvent;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeTagEvent;
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
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.clan.DefaultClan;
import net.pretronic.dkfriends.common.clan.DefaultClanManager;
import net.pretronic.dkfriends.common.party.DefaultParty;
import net.pretronic.dkfriends.common.party.DefaultPartyManager;
import net.pretronic.dkfriends.common.party.DefaultPartyMember;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayer;
import net.pretronic.dkfriends.common.player.friend.DefaultFriend;
import net.pretronic.libraries.event.EventPriority;
import net.pretronic.libraries.event.network.NetworkListener;

public class SyncListener {

    private final DKFriends dkfriends;

    public SyncListener(DKFriends dkfriends) {
        this.dkfriends = dkfriends;
    }

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
        DefaultParty party = new DefaultParty((DefaultDKFriends) dkfriends,event.getPartyId(),"Unset","Unset",false);
        party.addInternal(new DefaultPartyMember((DefaultDKFriends) dkfriends,party,event.getPlayerId(),party.getCreationTime(), PartyRole.LEADER));
        ((DefaultPartyManager)dkfriends.getPartyManager()).addParty(party);
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

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanDelete(ClanDeleteEvent event) {
        ((DefaultClanManager)dkfriends.getClanManager()).removeClanInternal(event.getClanId());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanChangeName(ClanChangeNameEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.setNameInternal(event.getNewName());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanChangeTag(ClanChangeTagEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.setTagInternal(event.getNewTag());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanChangeStatus(ClanChangeStatusEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.setStatusInternal(event.getNewStatus());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanInvite(ClanMemberInviteEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.addInvitation(event.getInvitation());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanInviteAccept(ClanMemberInvitationAcceptEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.removeInvitation(event.getInvitation().getPlayerId());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanInviteDeny(ClanMemberInvitationDenyEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.removeInvitation(event.getInvitation().getPlayerId());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanJoinEvent(ClanMemberJoinEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.addMember(event.getMember());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanLeaveEvent(ClanMemberJoinEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.removeMember(event.getMember().getPlayerId());
    }

    @NetworkListener(priority = EventPriority.LOWEST,onlyRemote = true)
    public void onClanLeaveEvent(ClanMemberRoleUpdateEvent event) {
        DefaultClan clan = ((DefaultClanManager)dkfriends.getClanManager()).getCachedClan(event.getClanId());
        if(clan != null) clan.updateRole(event.getMember().getPlayerId(),event.getNewRole());
    }


}
