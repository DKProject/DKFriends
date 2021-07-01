package net.pretronic.dkfriends.minecraft.gui.screens.clan.profile;

import net.pretronic.dkfriends.api.clan.ClanMember;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;

public class ClanMemberProfileScreenContext extends ScreenContext<GuiContext> {

    private final ClanMember selectedClanMember;

    public ClanMemberProfileScreenContext(GuiContext guiContext, Screen<GuiContext, ?> page, ClanMember selectedClanMember) {
        super(guiContext, page);
        this.selectedClanMember = selectedClanMember;
    }

    public ClanMember getSelectedClanMember() {
        return selectedClanMember;
    }
}
