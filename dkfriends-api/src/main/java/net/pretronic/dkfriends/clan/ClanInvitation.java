package net.pretronic.dkfriends.clan;

import java.util.UUID;

public interface ClanInvitation {

    UUID getClanId();

    Clan getClan();

    UUID getPlayerId();

    UUID getInviterId();

    long getInvitationTime();


    ClanMember join();

}
