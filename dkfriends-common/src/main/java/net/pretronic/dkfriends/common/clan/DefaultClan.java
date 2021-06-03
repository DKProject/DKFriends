package net.pretronic.dkfriends.common.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberInviteEvent;
import net.pretronic.dkfriends.api.event.clan.ClanMessageEvent;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeNameEvent;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeStatusEvent;
import net.pretronic.dkfriends.api.event.clan.change.ClanChangeTagEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberInvitationAcceptEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberInvitationDenyEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberJoinEvent;
import net.pretronic.dkfriends.api.event.clan.member.ClanMemberLeaveEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.clan.DefaultClanInviteEvent;
import net.pretronic.dkfriends.common.event.clan.DefaultClanMessageEvent;
import net.pretronic.dkfriends.common.event.clan.change.DefaultClanChangeNameEvent;
import net.pretronic.dkfriends.common.event.clan.change.DefaultClanChangeStatusEvent;
import net.pretronic.dkfriends.common.event.clan.change.DefaultClanChangeTagEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberAcceptEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberDenyEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberJoinEvent;
import net.pretronic.dkfriends.common.event.clan.member.DefaultClanMemberLeaveEvent;
import net.pretronic.libraries.document.Document;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.Validate;
import net.pretronic.libraries.utility.annonations.Internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultClan implements Clan {

    private final DefaultDKFriends dkFriends;

    private final UUID id;
    private String name;
    private String tag;
    private String status;
    private Document properties;

    private Collection<ClanMember> members;
    private Collection<ClanInvitation> invitations;

    public DefaultClan(DefaultDKFriends dkFriends, UUID id, String name, String tag, String status, Document properties) {
        Validate.notNull(dkFriends, id, properties, name, tag);
        this.dkFriends = dkFriends;
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.status = status;
        this.properties = properties;

        this.members = null;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean setName(String name) {
        ClanChangeNameEvent event = new DefaultClanChangeNameEvent(dkFriends,this, name);
        this.dkFriends.getEventBus().callEvent(ClanChangeNameEvent.class, event);
        if(event.isCancelled()) return false;

        this.dkFriends.getStorage().getClans().update()
                .set("Name", event.getNewName())
                .where("Id", this.id)
                .execute();
        this.name = event.getNewName();
        return true;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public boolean setTag(String tag) {
        ClanChangeTagEvent event = new DefaultClanChangeTagEvent(dkFriends,this, tag);
        this.dkFriends.getEventBus().callEvent(ClanChangeTagEvent.class, event);
        if(event.isCancelled()) return false;

        this.dkFriends.getStorage().getClans().update()
                .set("Tag", event.getNewTag())
                .where("Id", this.id)
                .execute();
        this.tag = event.getNewTag();
        return true;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public boolean setStatus(String status) {
        ClanChangeStatusEvent event = new DefaultClanChangeStatusEvent(dkFriends,this, tag);
        this.dkFriends.getEventBus().callEvent(ClanChangeStatusEvent.class, event);
        if(event.isCancelled()) return false;

        this.dkFriends.getStorage().getClans().update()
                .set("Status", event.getNewStatus())
                .where("Id", this.id)
                .execute();
        this.tag = event.getNewStatus();
        return true;
    }

    @Override
    public Document getProperties() {
        return this.properties;
    }

    @Override
    public Collection<ClanMember> getSortedMembers() {
        return getMembers();
    }

    @Override
    public Collection<ClanMember> getOnlineMembers() {
        return getMembers();
    }

    @Override
    public Collection<ClanMember> getMembers() {
        return getMembersOrLoad();
    }

    @Override
    public ClanMember getMember(UUID playerId) {
        Validate.notNull(playerId);
        return Iterators.findOne(getMembers(), member -> member.getPlayerId().equals(playerId));
    }

    @Override
    public boolean isMember(UUID playerId) {
        return getMember(playerId) != null;
    }

    @Override
    public int getSize() {
        return getMembersOrLoad().size();
    }

    @Override
    public ClanMember addMember(UUID playerId) {
        return addMember(playerId, ClanRole.GUEST);
    }

    @Override
    public ClanMember addMember(UUID playerId, ClanRole role) {
        Validate.notNull(playerId);
        if(isMember(playerId)) throw new IllegalArgumentException("Already a member of this clan (" + getId() + ")");

        long time = System.currentTimeMillis();
        ClanMember member = new DefaultClanMember(dkFriends,this,playerId, time, role);

        ClanMemberJoinEvent event = new DefaultClanMemberJoinEvent(member);
        this.dkFriends.getEventBus().callEvent(ClanMemberJoinEvent.class, event);
        if(event.isCancelled()) return null;

        this.dkFriends.getStorage().getClanMembers().insert()
                .set("ClanId",getId())
                .set("PlayerId",playerId)
                .set("Role",event.getMember().getRole().toString())
                .set("Joined", time)
                .execute();

        getMembersOrLoad().add(member);

        return member;
    }

    @Override
    public boolean removeMember(ClanMember member, String cause) {
        Validate.notNull(member, cause);
        if(!member.getClan().getId().equals(id)) {
            throw new IllegalArgumentException("Member (Id:"+member.getPlayerId() + "; ClanId: "+member.getClan().getId()
                    +") belongs not to this clan (" + getId() + ")");
        }

        ClanMemberLeaveEvent event = new DefaultClanMemberLeaveEvent(member);
        this.dkFriends.getEventBus().callEvent(ClanMemberLeaveEvent.class, event);
        if(event.isCancelled()) return false;

        this.dkFriends.getStorage().getClanMembers().delete()
                .where("ClanId", getId())
                .where("PlayerId",member.getPlayerId())
                .execute();
        getMembersOrLoad().remove(member);
        return true;
    }

    @Override
    public ClanMember acceptInvitation(ClanInvitation invitation) {
        Validate.notNull(invitation);
        if(invitation.getClanId() != getId()) {
            throw new IllegalArgumentException("Invitation is not from this clan (" + getId() + ")");
        }
        ClanMemberInvitationAcceptEvent event = new DefaultClanMemberAcceptEvent(invitation);
        this.dkFriends.getEventBus().callEvent(ClanMemberInvitationAcceptEvent.class, event);

        ClanMember member = addMember(invitation.getPlayerId());
        if(member == null) return null;
        getInvitationsOrLoad().remove(invitation);
        this.dkFriends.getStorage().getClanInvitations().delete()
                .where("ClanId", getId())
                .where("PlayerId", invitation.getPlayerId())
                .execute();
        return member;
    }

    @Override
    public void denyInvitation(ClanInvitation invitation) {
        Validate.notNull(invitation);
        if(invitation.getClanId() != getId()) {
            throw new IllegalArgumentException("Invitation is not from this clan (" + getId() + ")");
        }
        ClanMemberInvitationDenyEvent event = new DefaultClanMemberDenyEvent(this, invitation);
        this.dkFriends.getEventBus().callEvent(ClanMemberInvitationDenyEvent.class, event);

        getInvitationsOrLoad().remove(invitation);
        this.dkFriends.getStorage().getClanInvitations().delete()
                .where("ClanId", getId())
                .where("PlayerId", invitation.getPlayerId())
                .execute();
    }

    @Override
    public Collection<ClanInvitation> getInvitations() {
        return getInvitationsOrLoad();
    }

    @Override
    public ClanInvitation getInvitation(UUID playerId) {
        Validate.notNull(playerId);
        return Iterators.findOne(getInvitationsOrLoad(), invitation -> invitation.getPlayerId().equals(playerId));
    }

    @Override
    public boolean hasInvitation(UUID playerId) {
        return getInvitation(playerId) != null;
    }

    @Override
    public ClanInvitation sendInvitation(DKFriendsPlayer inviter, UUID playerId) {
        Validate.notNull(inviter, playerId);
        if(inviter.getClan().getId() != getId()) throw new IllegalArgumentException("Can not send invitation to other clan (" + getId() + ")");
        if(hasInvitation(playerId)) throw new IllegalArgumentException("Player has already an invitation to this clan (" + getId() + ")");
        if(isMember(playerId)) throw new IllegalArgumentException("Player is already an member of this clan (" + getId() + ")");

        long time = System.currentTimeMillis();
        DefaultClanInvitation invitation = new DefaultClanInvitation(this.dkFriends, getId(), playerId, inviter.getId(), time);

        ClanMemberInviteEvent event = new DefaultClanInviteEvent(invitation);
        this.dkFriends.getEventBus().callEvent(ClanMemberInviteEvent.class, event);
        if(event.isCancelled()) return null;

        this.dkFriends.getStorage().getClanInvitations().insert()
                .set("ClanId", getId())
                .set("PlayerId", playerId)
                .set("InvitedByPlayerId", inviter.getId())
                .set("Time", time)
                .execute();
        getInvitationsOrLoad().add(invitation);
        return invitation;
    }

    @Override
    public void sendMessage(String channel, String message) {
        ClanMessageEvent event = new DefaultClanMessageEvent(dkFriends,this,channel,message);
        this.dkFriends.getEventBus().callEvent(event);
    }

    private Collection<ClanInvitation> getInvitationsOrLoad() {
        if(this.invitations == null) {
            this.invitations = new ArrayList<>();

            this.dkFriends.getStorage().getClanInvitations().find().where("ClanId", getId()).execute().loadIn(this.invitations,
                    resultEntry -> new DefaultClanInvitation(this.dkFriends,
                            getId(),
                            resultEntry.getUniqueId("PlayerId"),
                            resultEntry.getUniqueId("InvitedByPlayerId"),
                            resultEntry.getLong("Time")));
        }
        return this.invitations;
    }

    private Collection<ClanMember> getMembersOrLoad() {
        if(this.members == null) {
            this.members = new ArrayList<>();
            this.dkFriends.getStorage().getClanMembers().find().where("ClanId", this.id).execute().loadIn(this.members,
                    resultEntry -> new DefaultClanMember(dkFriends,this,
                            resultEntry.getUniqueId("PlayerId"),
                            resultEntry.getLong("Joined"),
                            ClanRole.valueOf(resultEntry.getString("Role"))));
        }
        return this.members;
    }

    @Internal
    public void setNameInternal(String name){
        this.name = name;
    }

    @Internal
    public void setTagInternal(String tag){
        this.tag = tag;
    }

    @Internal
    public void setStatusInternal(String status){
        this.status = status;
    }

    @Internal
    public void addInvitation(ClanInvitation invitation){
        if(invitations != null) invitations.add(invitation);
    }

    @Internal
    public void removeInvitation(UUID playerId){
        if(invitations != null) Iterators.removeOne(this.invitations, invitation -> invitation.getPlayerId().equals(playerId));
    }

    @Internal
    public void addMember(ClanMember member){
        if(members != null) members.add(member);
    }

    @Internal
    public void removeMember(UUID playerId){
        if(members != null) Iterators.removeOne(this.members, invitation -> invitation.getPlayerId().equals(playerId));
    }

    @Internal
    public void updateRole(UUID playerId,ClanRole role){
        if(members != null) {
            ClanMember member = Iterators.findOne(this.members, invitation -> invitation.getPlayerId().equals(playerId));
            if(member != null) member.setRole(role);
        }
    }
}
