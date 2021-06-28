package net.pretronic.dkfriends.minecraft.commands;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.Iterators;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;
import org.mcnative.runtime.api.text.components.MessageComponent;

import java.util.*;
import java.util.function.Predicate;

public class CommandUtil {

    public static boolean isPlayerCheck(CommandSender sender, MessageComponent<?> prefix){
        if(sender instanceof OnlineMinecraftPlayer) return true;
        sender.sendMessage(Messages.ERROR_ONLY_PLAYER, VariableSet.create()
                .addDescribed("prefix",prefix));
        return false;
    }

    public static boolean isSelfCheck(MessageComponent<?> prefix,CommandSender sender,String target){
        if(sender.getName().equalsIgnoreCase(target)){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_SELF,VariableSet.create().add("prefix",prefix));
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

    public static Collection<String> completeFriends(CommandSender sender,String[] arguments){
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);
        if(arguments.length == 0){
            return Iterators.map(player.getFriends(), request -> request.getFriend().getName());
        }else  if(arguments.length == 1){
            return Iterators.map(player.getFriends(), request -> request.getFriend().getName()
                    , request -> request.getFriend().getName().toLowerCase().startsWith(arguments[0].toLowerCase()));
        }else {
            return Collections.emptyList();
        }
    }

    public static Collection<String> completeOnlineFriends(CommandSender sender,String[] arguments){
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);
        if(arguments.length == 0){
            return Iterators.map(player.getFriends(), request -> request.getFriend().getName()
                    , request -> request.getFriend().isOnline());
        }else  if(arguments.length == 1){
            return Iterators.map(player.getFriends(), request -> request.getFriend().getName()
                    , request -> request.getFriend().getName().toLowerCase().startsWith(arguments[0].toLowerCase())
                            && request.getFriend().isOnline());
        }else {
            return Collections.emptyList();
        }
    }

    public static Collection<String> completePartyMembers(CommandSender sender,String[] arguments){
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);
        Party party = player.getParty();
        if(party == null) return Collections.emptyList();
        if(arguments.length == 0){
            return Iterators.map(party.getMembers(), request -> request.getPlayer().getName()
                    , request -> request.getPlayer().isOnline());
        }else  if(arguments.length == 1){
            return Iterators.map(party.getMembers(), request -> request.getPlayer().getName()
                    , request -> request.getPlayer().getName().toLowerCase().startsWith(arguments[0].toLowerCase())
                            && request.getPlayer().isOnline());
        }else {
            return Collections.emptyList();
        }
    }

    public static Collection<String> completeClanMembers(CommandSender sender,String[] arguments){
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);
        Clan clan = player.getClan();
        if(clan == null) return Collections.emptyList();
        if(arguments.length == 0){
            return Iterators.map(clan.getMembers(), request -> request.getPlayer().getName()
                    , request -> request.getPlayer().isOnline());
        }else  if(arguments.length == 1){
            return Iterators.map(clan.getMembers(), request -> request.getPlayer().getName()
                    , request -> request.getPlayer().getName().toLowerCase().startsWith(arguments[0].toLowerCase())
                            && request.getPlayer().isOnline());
        }else {
            return Collections.emptyList();
        }
    }

    public static List<String> completeOnlinePlayer(CommandSender sender,String[] args) {
        Collection<? extends OnlineMinecraftPlayer> players;
        if(McNative.getInstance().getPlatform().isProxy() && McNative.getInstance().isNetworkAvailable()){
            if(sender instanceof ConnectedMinecraftPlayer){
                players = ((ConnectedMinecraftPlayer) sender).getServer().getOnlinePlayers();
            }else{
                players = McNative.getInstance().getLocal().getConnectedPlayers();
            }
        }else{
            players = McNative.getInstance().getLocal().getConnectedPlayers();
        }
        if(args.length == 0){
            return Iterators.map(players, OnlineMinecraftPlayer::getName);
        }else if(args.length == 1){
            return Iterators.map(players, OnlineMinecraftPlayer::getName
                    , player -> player.getName().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT)));
        }else return Collections.emptyList();
    }

}
