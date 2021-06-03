package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.event.clan.ClanInviteEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberDemoteEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberJoinEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberKickEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberPromoteEvent;
import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.event.friend.FriendRemoveEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestDenyEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.api.event.party.*;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationDenyEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInviteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.event.EventPriority;
import net.pretronic.libraries.event.Listener;
import net.pretronic.libraries.event.execution.ExecutionType;
import net.pretronic.libraries.event.network.NetworkListener;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.network.component.server.MinecraftServer;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.text.components.MessageComponent;

import java.util.ArrayList;
import java.util.Collection;

public class PerformListener {

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onFriendRequestSend(FriendRequestSendEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getRequest().getReceiverId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_REQUEST, VariableSet.create().addDescribed("player",event.getRequest().getReceiver()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onFriendRequestDeny(FriendRequestDenyEvent event){
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getRequest().getRequesterId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_DENY, VariableSet.create().addDescribed("player",event.getPlayer()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onFriendAdd(FriendAddEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_ADD, VariableSet.create().addDescribed("player",event.getFriend().getFriend()));
        }

        ConnectedMinecraftPlayer player2 = McNative.getInstance().getLocal().getConnectedPlayer(event.getFriend().getFriendId());
        if(player2 !=  null){
            player2.sendMessage(Messages.FRIEND_ADD, VariableSet.create().addDescribed("player",event.getPlayer()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onFriendRemove(FriendRemoveEvent event){
        if(event.isCancelled()) return;

        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_REMOVE, VariableSet.create().addDescribed("player",event.getFriend().getFriend()));
        }

        ConnectedMinecraftPlayer player2 = McNative.getInstance().getLocal().getConnectedPlayer(event.getFriend().getFriendId());
        if(player2 !=  null){
            player2.sendMessage(Messages.FRIEND_REMOVE, VariableSet.create().addDescribed("player",event.getPlayer()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onClanInvite(ClanInviteEvent event) {
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getInvitation().getPlayerId());
        if(player != null) {
            player.sendMessage(Messages.CLAN_INVITE, VariableSet.create().addDescribed("invitation", event.getInvitation()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onClanMemberKick(ClanMemberKickEvent event) {
        if(event.isCancelled()) return;

        Collection<ConnectedMinecraftPlayer> players = getConnectedClanPlayers(event.getClan());
        if(!players.isEmpty()){
            VariableSet variables = VariableSet.create()
                    .addDescribed("member", event.getMember())
                    .addDescribed("executor", event.getExecutor());

            for (ConnectedMinecraftPlayer connectedMinecraftPlayer : players) {
                if(connectedMinecraftPlayer.getUniqueId().equals(event.getMemberId())) {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_KICK, variables);
                } else {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_KICK_OTHER, variables);
                }
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onClanMemberPromote(ClanMemberPromoteEvent event) {
        Collection<ConnectedMinecraftPlayer> players = getConnectedClanPlayers(event.getClan());
        if(!players.isEmpty()){
            VariableSet variables = VariableSet.create()
                    .addDescribed("member", event.getMember())
                    .addDescribed("executor", event.getExecutor());

            for (ConnectedMinecraftPlayer connectedMinecraftPlayer : players) {
                if(connectedMinecraftPlayer.getUniqueId().equals(event.getMemberId())) {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_PROMOTE, variables);
                } else {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_PROMOTE_OTHER, variables);
                }
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onClanMemberDemote(ClanMemberDemoteEvent event) {
        Collection<ConnectedMinecraftPlayer> players = getConnectedClanPlayers(event.getClan());
        if(!players.isEmpty()) {
            VariableSet variables = VariableSet.create()
                    .addDescribed("member", event.getMember())
                    .addDescribed("executor", event.getExecutor());

            for (ConnectedMinecraftPlayer connectedMinecraftPlayer : players) {
                if(connectedMinecraftPlayer.getUniqueId().equals(event.getMemberId())) {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_DEMOTE, variables);
                } else {
                    connectedMinecraftPlayer.sendMessage(Messages.CLAN_DEMOTE_OTHER, variables);
                }
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onClanMemberJoin(ClanMemberJoinEvent event) {
        if(event.isCancelled()) return;

        Collection<ConnectedMinecraftPlayer> players = getConnectedClanPlayers(event.getMember().getClan());
        if(!players.isEmpty()){
            VariableSet variables = VariableSet.create()
                    .addDescribed("member", event.getMember());

            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(Messages.CLAN_JOIN,variables);
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onPartyInvite(PartyInviteEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player !=  null){
            player.sendMessage(Messages.PARTY_INVITE, VariableSet.create()
                    .addDescribed("invitation",event.getInvitation()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyInvitationDenyEvent event) {
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getInvitation().getInviterId());
        if(player != null){
            player.sendMessage(Messages.PARTY_DENIED, VariableSet.create()
                    .addDescribed("player",event.getPlayer()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyJoinEvent event){
        if(event.isCancelled()) return;

        MinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        ConnectedMinecraftPlayer cPlayer = player.getAsConnectedPlayer();

        Collection<ConnectedMinecraftPlayer> players = getConnectedPartyPlayers(event.getParty());
        if(!players.isEmpty() || cPlayer != null){
            VariableSet variables = VariableSet.create().addDescribed("player",player);

            if(cPlayer != null){
                cPlayer.sendMessage(Messages.PARTY_JOIN,variables);
            }

            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(Messages.PARTY_JOIN,variables);
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyLeaveEvent event) {
        if (event.isCancelled()) return;
        Collection<ConnectedMinecraftPlayer> players = getConnectedPartyPlayers(event.getParty());
        if(!players.isEmpty()){
            MinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
            VariableSet variables = VariableSet.create()
                    .addDescribed("executor",event.getExecutor())
                    .addDescribed("player",player);
            MessageComponent<?> message = Messages.PARTY_LEAVE;
            if(event.getCause().equals(Party.KICK_LEAVE_CAUSE)) message = Messages.PARTY_KICK;
            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(message,variables);
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyDeleteEvent event) {
        if (event.isCancelled()) return;
        Collection<ConnectedMinecraftPlayer> players = getConnectedPartyPlayers(event.getParty());
        if(!players.isEmpty()){
            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(Messages.PARTY_DELETE);
            }
        }
    }


    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyMessageEvent event) {
        if(event.isCancelled() && event.getChannel().equals(Party.DEFAULT_MESSAGE_CHANNEL)) return;
        Collection<ConnectedMinecraftPlayer> players = getConnectedPartyPlayers(event.getParty());
        if(!players.isEmpty()){
            MinecraftPlayer sender = McNative.getInstance().getLocal().getConnectedPlayer(event.getSenderId());
            VariableSet variables = VariableSet.create()
                    .addDescribed("player",sender)
                    .addDescribed("message",event.getMessage());
            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(Messages.PARTY_MESSAGE,variables);
            }
        }
    }

    @Listener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    @NetworkListener(priority = EventPriority.HIGHEST,execution = ExecutionType.ASYNC)
    public void onParty(PartyTeleportEvent event) {
        if(event.isCancelled() && event.getType().equals(Party.DEFAULT_TELEPORT_TYPE)) return;
        Collection<ConnectedMinecraftPlayer> players = getConnectedPartyPlayers(event.getParty());
        if(!players.isEmpty()){
            MinecraftServer target = McNative.getInstance().getNetwork().getServer(event.getTarget());
            if(target == null) return;
            VariableSet variables = VariableSet.create().addDescribed("target",target);
            for (ConnectedMinecraftPlayer member : players) {
                member.sendMessage(Messages.PARTY_TELEPORT,variables);
            }
        }
    }

    private Collection<ConnectedMinecraftPlayer> getConnectedPartyPlayers(Party party){
        Collection<ConnectedMinecraftPlayer> players = new ArrayList<>();
        for (PartyMember member : party.getMembers()) {
            ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(member.getPlayerId());
            if(player != null) players.add(player);
        }
        return players;
    }

    private Collection<ConnectedMinecraftPlayer> getConnectedClanPlayers(Clan clan){
        Collection<ConnectedMinecraftPlayer> players = new ArrayList<>();
        for (ClanMember member : clan.getMembers()) {
            ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(member.getPlayerId());
            if(player != null) players.add(player);
        }
        return players;
    }
}
