package net.pretronic.dkfriends.minecraft.commands;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;
import org.mcnative.runtime.api.text.components.MessageComponent;

import java.util.Collection;
import java.util.UUID;

public class CommandUtil {

    public static boolean isPlayerCheck(CommandSender sender, MessageComponent<?> prefix){
        if(sender instanceof OnlineMinecraftPlayer) return true;
        sender.sendMessage(Messages.ERROR_ONLY_PLAYER, VariableSet.create()
                .addDescribed("prefix",prefix));
        return false;
    }

    public static boolean isSelfCheck(CommandSender sender,String target){
        if(sender.getName().equalsIgnoreCase(target)){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_SELF);
            return true;
        }
        return false;
    }

    public static MinecraftPlayer getPlayer(CommandSender sender, MessageComponent<?> prefix, String name){
        MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(name);
        if(player == null) sender.sendMessage(Messages.ERROR_PLAYER_NOT_FOUND, VariableSet.create()
                .addDescribed("name",name)
                .addDescribed("prefix",prefix));
        return player;
    }

    public static String readStringFromArguments(String[] arguments, int start){
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arguments.length; i++){
            builder.append(' ').append(arguments[i]);
        }
        return builder.substring(1);
    }

    public static boolean isInClanCheck(CommandSender commandSender, DKFriendsPlayer player) {
        if(!player.isInClan()) {
            commandSender.sendMessage(Messages.ERROR_CLAN_NOT_IN_CLAN);
            return false;
        }
        return true;
    }

    public static boolean isInPartyCheck(CommandSender commandSender, DKFriendsPlayer player) {
        if(!player.isInParty()) {
            commandSender.sendMessage(Messages.ERROR_PARTY_NOT);
        }
        return true;
    }

    public static boolean isInSameClan(CommandSender commandSender, Clan clan, MinecraftPlayer targetPlayer) {
        if(clan.getMember(targetPlayer.getUniqueId()) == null) {
            commandSender.sendMessage(Messages.ERROR_CLAN_NOT_SAME_CLAN, VariableSet.create().addDescribed("target", targetPlayer));
            return false;
        }
        return true;
    }

    public static boolean isNotInClan(CommandSender commandSender, DKFriendsPlayer player) {
        if(player.getClan() != null) {
            commandSender.sendMessage(Messages.ERROR_CLAN_ALREADY_IN_CLAN_SELF);
            return false;
        }
        return true;
    }
}
