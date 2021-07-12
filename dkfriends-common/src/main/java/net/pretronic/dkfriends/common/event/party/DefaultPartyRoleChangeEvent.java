package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyRoleChangeEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyRoleChangeEvent implements PartyRoleChangeEvent {

    private final PartyMember member;
    private final PartyRole newRole;
    private final PartyRole oldRole;

    public DefaultPartyRoleChangeEvent(PartyMember member, PartyRole newRole, PartyRole oldRole) {
        this.member = member;
        this.newRole = newRole;
        this.oldRole = oldRole;
    }

    @Override
    public UUID getPlayerId() {
        return member.getPlayerId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return member.getPlayer();
    }

    @Override
    public Party getParty() {
        return getMember().getParty();
    }

    @Override
    public PartyMember getMember() {
        return member;
    }

    @Override
    public PartyRole getNewRole() {
        return newRole;
    }

    @Override
    public PartyRole getOldRole() {
        return oldRole;
    }
}
