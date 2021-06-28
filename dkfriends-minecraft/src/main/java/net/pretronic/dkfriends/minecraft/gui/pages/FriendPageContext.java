package net.pretronic.dkfriends.minecraft.gui.pages;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import org.mcnative.runtime.api.service.inventory.gui.Page;
import org.mcnative.runtime.api.service.inventory.gui.Screen;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.pane.ArrayListPaginationSource;

public class FriendPageContext extends ScreenContext<GuiContext> {

    private final ArrayListPaginationSource<Friend> friends;

    public FriendPageContext(GuiContext guiContext, Screen<GuiContext, ?> page) {
        super(guiContext, page);
        this.friends = new ArrayListPaginationSource<>(guiContext.getPlayer().getAs(DKFriendsPlayer.class).getSortedFriends(),36);
    }

    public ArrayListPaginationSource<Friend> getFriends() {
        return friends;
    }
}
