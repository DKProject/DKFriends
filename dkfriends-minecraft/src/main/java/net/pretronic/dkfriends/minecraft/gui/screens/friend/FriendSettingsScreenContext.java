package net.pretronic.dkfriends.minecraft.gui.screens.friend;

import net.pretronic.dkfriends.api.player.friend.Friend;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;

public class FriendSettingsScreenContext extends ScreenContext<GuiContext> {

    private final Friend selectedFriend;

    public FriendSettingsScreenContext(GuiContext guiContext, Screen<GuiContext, ?> page, Friend selectedFriend) {
        super(guiContext, page);
        this.selectedFriend = selectedFriend;
    }

    public Friend getSelectedFriend() {
        return selectedFriend;
    }
}
