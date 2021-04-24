package net.pretronic.dkfriends.minecraft.config;

import net.pretronic.libraries.command.command.configuration.CommandConfiguration;

public class DKFriendsConfig {

    public static CommandConfiguration COMMAND_FRIENDS = CommandConfiguration.newBuilder()
            .name("friends")
            .permission("dkbans.command.friends")
            .create();

}
