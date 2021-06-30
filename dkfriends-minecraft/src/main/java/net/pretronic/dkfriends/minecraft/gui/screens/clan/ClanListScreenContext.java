package net.pretronic.dkfriends.minecraft.gui.screens.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.pane.ArrayListPaginationSource;

public class ClanListScreenContext extends ScreenContext<GuiContext> {

    private final Clan clan;
    private final ArrayListPaginationSource<ClanMember> clanMembers;

    public ClanListScreenContext(GuiContext guiContext, Screen<GuiContext, ?> page) {
        super(guiContext, page);
        this.clan = guiContext.getPlayer().getAs(DKFriendsPlayer.class).getClan();
        this.clanMembers = new ArrayListPaginationSource<>(clan.getSortedMembers(),27);
    }

    public Clan getClan() {
        return clan;
    }

    public ArrayListPaginationSource<ClanMember> getClanMembers() {
        return clanMembers;
    }
}
