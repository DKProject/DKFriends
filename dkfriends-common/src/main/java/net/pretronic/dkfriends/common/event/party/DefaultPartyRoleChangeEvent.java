package net.pretronic.dkfriends.common.event.party;

import net.pretronic.dkfriends.api.event.party.PartyRoleChangeEvent;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public class DefaultPartyRoleChangeEvent implements PartyRoleChangeEvent {

    private final PartyMember member;
    private final PartyRole newRole;

    public DefaultPartyRoleChangeEvent(PartyMember member, PartyRole newRole) {
        this.member = member;
        this.newRole = newRole;
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
    public PartyMember getMember() {
        return member;
    }

    @Override
    public PartyRole getNewRole() {
        return newRole;
    }
}
