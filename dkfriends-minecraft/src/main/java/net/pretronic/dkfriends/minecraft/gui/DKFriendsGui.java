package net.pretronic.dkfriends.minecraft.gui;

import net.pretronic.dkfriends.minecraft.gui.pages.FriendPage;
import net.pretronic.dkfriends.minecraft.gui.pages.FriendPageContext;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;

public class DKFriendsGui {

    public static void register(){
        GuiManager manager = McNative.getInstance().getRegistry().getService(GuiManager.class);

        manager.createGui("dkfriends", GuiContext.class, builder -> {
            builder.setDefaultPage("friends");
            builder.createPage("friends", 54, FriendPageContext.class,context -> "§aProfile",FriendPage::register);
        });
    }

}
