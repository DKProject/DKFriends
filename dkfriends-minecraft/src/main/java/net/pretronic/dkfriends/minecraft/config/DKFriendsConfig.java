package net.pretronic.dkfriends.minecraft.config;

import net.pretronic.libraries.command.command.configuration.CommandConfiguration;

public class DKFriendsConfig {

    public static CommandConfiguration COMMAND_FRIEND = CommandConfiguration.newBuilder()
            .name("friend")
            .aliases("friends")
            .permission("dkbans.command.friend")
            .create();

    public static CommandConfiguration COMMAND_PARTY = CommandConfiguration.newBuilder()
            .name("party")
            .aliases("parties")
            .permission("dkbans.command.party")
            .create();

    public static CommandConfiguration COMMAND_CLAN = CommandConfiguration.newBuilder()
            .name("clan")
            .permission("dkbans.command.clan")
            .create();
}
