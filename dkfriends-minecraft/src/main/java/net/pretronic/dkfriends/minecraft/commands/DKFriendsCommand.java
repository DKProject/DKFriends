/*
 * (C) Copyright 2020 The DKPerms Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Davide Wietlisbach
 * @since 15.12.20, 18:39
 * @website %web%
 *
 * %license%
 */

package net.pretronic.dkfriends.minecraft.commands;

import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.plugin.MinecraftPlugin;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

public class DKFriendsCommand extends BasicCommand {

    public DKFriendsCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("dkfriends","dkfriend"));
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        sender.sendMessage(Text.newBuilder()
                .color(TextColor.DARK_GRAY).text("» ")
                .color(TextColor.GREEN).text("DKFriends")
                .color(TextColor.DARK_GRAY).text(" | ")
                .color(TextColor.GRAY).text("v")
                .color(TextColor.RED).text(((MinecraftPlugin)getOwner()).getDescription().getVersion().getName())
                .color(TextColor.GRAY).text(" by ")
                .color(TextColor.RED).text(((MinecraftPlugin)getOwner()).getDescription().getAuthor())
                .build());
    }
}
