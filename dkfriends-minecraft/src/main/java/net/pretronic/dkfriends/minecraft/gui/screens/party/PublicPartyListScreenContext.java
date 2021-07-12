package net.pretronic.dkfriends.minecraft.gui.screens.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.player.MinecraftDKFriendsPlayer;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.pane.ArrayListPaginationSource;

import java.util.ArrayList;

public class PublicPartyListScreenContext extends ScreenContext<GuiContext> {

    private final ArrayListPaginationSource<Party> parties;

    public PublicPartyListScreenContext(GuiContext guiContext, Screen<GuiContext, ?> page) {
        super(guiContext, page);
        MinecraftDKFriendsPlayer player = (MinecraftDKFriendsPlayer) guiContext.getPlayer().getAs(DKFriendsPlayer.class);
        this.parties = new ArrayListPaginationSource<>(new ArrayList<>(player.getDKFriends().getPartyManager().getPublicParties()),36);
    }

    public ArrayListPaginationSource<Party> getParties() {
        return parties;
    }
}
