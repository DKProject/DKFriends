package net.pretronic.dkfriends.minecraft.gui.screens.party;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.pane.ArrayListPaginationSource;

public class PartyListScreenContext extends ScreenContext<GuiContext> {

    private final Party party;
    private final ArrayListPaginationSource<PartyMember> partyMembers;

    public PartyListScreenContext(GuiContext guiContext, Screen<GuiContext, ?> page) {
        super(guiContext, page);
        this.party = guiContext.getPlayer().getAs(DKFriendsPlayer.class).getParty();
        this.partyMembers = new ArrayListPaginationSource<>(party.getSortedMembers(),27);
    }

    public Party getParty() {
        return party;
    }

    public ArrayListPaginationSource<PartyMember> getPartyMembers() {
        return partyMembers;
    }
}
