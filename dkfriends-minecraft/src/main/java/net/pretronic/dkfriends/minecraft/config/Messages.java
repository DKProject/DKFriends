/*
 * (C) Copyright 2020 The DKBans Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Philipp Elvin Friedhoff
 * @since 21.06.20, 17:26
 * @web %web%
 *
 * The DKBans Project is under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package net.pretronic.dkfriends.minecraft.config;

import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.components.MessageComponent;

public class Messages {

    public static MessageComponent<?> PREFIX_FRIEND = Text.ofMessageKey("dkfriends.prefix.friend");
    public static MessageComponent<?> PREFIX_PARTY = Text.ofMessageKey("dkfriends.prefix.party");
    public static MessageComponent<?> PREFIX_CLAN = Text.ofMessageKey("dkfriends.prefix.clan");


    public static MessageComponent<?> ERROR_ONLY_PLAYER = Text.ofMessageKey("dkfriends.error.onlyPlayer");
    public static MessageComponent<?> ERROR_NO_PAGE = Text.ofMessageKey("dkfriends.error.noPage");
    public static MessageComponent<?> ERROR_PLAYER_NOT_FOUND = Text.ofMessageKey("dkfriends.error.player.notFound");
    public static MessageComponent<?> ERROR_PLAYER_NOT_ONLINE = Text.ofMessageKey("dkfriends.error.player.notOnline");
    public static MessageComponent<?> ERROR_PLAYER_NOT_SELF = Text.ofMessageKey("dkfriends.error.player.notSelf");

    public static MessageComponent<?> ERROR_FRIEND_ALREADY = Text.ofMessageKey("dkfriends.error.friend.already");
    public static MessageComponent<?> ERROR_FRIEND_NOT = Text.ofMessageKey("dkfriends.error.friend.not");
    public static MessageComponent<?> ERROR_FRIEND_EMPTY = Text.ofMessageKey("dkfriends.error.friend.empty");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_ALREADY = Text.ofMessageKey("dkfriends.error.friend.request.already");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_NOT = Text.ofMessageKey("dkfriends.error.friend.request.not");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_EMPTY = Text.ofMessageKey("dkfriends.error.friend.request.empty");

    public static final MessageComponent<?> ERROR_CLAN_ALREADY_IN_CLAN_SELF = Text.ofMessageKey("dkfriends.error.clan.alreadyInClan.self");
    public static final MessageComponent<?> ERROR_CLAN_ALREADY_IN_CLAN_TARGET = Text.ofMessageKey("dkfriends.error.clan.alreadyInClan.target");
    public static final MessageComponent<?> ERROR_CLAN_NOT_IN_CLAN = Text.ofMessageKey("dkfriends.error.clan.notInClan");
    public static final MessageComponent<?> ERROR_CLAN_NOT_SAME_CLAN = Text.ofMessageKey("dkfriends.error.clan.notSameClan");
    public static final MessageComponent<?> ERROR_CLAN_MEMBER_NOT_ALLOWED = Text.ofMessageKey("dkfriends.error.clan.member.notAllowed");
    public static final MessageComponent<?> ERROR_CLAN_NOT_EXISTS = Text.ofMessageKey("dkfriends.error.clan.notExists");
    public static final MessageComponent<?> ERROR_CLAN_NAME_ALREADY_EXISTS = Text.ofMessageKey("dkfriends.error.clan.name.alreadyExists");
    public static final MessageComponent<?> ERROR_CLAN_TAG_ALREADY_EXISTS = Text.ofMessageKey("dkfriends.error.clan.tag.alreadyExists");
    public static final MessageComponent<?> ERROR_CLAN_MEMBER_NO_INVITATION_SPECIFIC = Text.ofMessageKey("dkfriends.error.clan.member.noInvitationSpecific");
    public static final MessageComponent<?> ERROR_CLAN_MEMBER_ALREADY_INVITED = Text.ofMessageKey("dkfriends.error.clan.member.alreadyInvited");

    public static MessageComponent<?> ERROR_PARTY_NOT = Text.ofMessageKey("dkfriends.error.party.not");
    public static MessageComponent<?> ERROR_PARTY_ALREADY = Text.ofMessageKey("dkfriends.error.party.already");
    public static MessageComponent<?> ERROR_PARTY_NOT_ALLOWED = Text.ofMessageKey("dkfriends.error.party.notAllowed");
    public static MessageComponent<?> ERROR_PARTY_NOT_MEMBER = Text.ofMessageKey("dkfriends.error.party.notMember");
    public static MessageComponent<?> ERROR_PARTY_INVITATION_NOT = Text.ofMessageKey("dkfriends.error.party.invitation.not");

    public static MessageComponent<?> COMMAND_FRIEND_HELP= Text.ofMessageKey("dkfriends.command.friend.help");
    public static MessageComponent<?> COMMAND_FRIEND_ADD_SUCCESS = Text.ofMessageKey("dkfriends.command.friend.add.success");
    public static MessageComponent<?> COMMAND_FRIEND_LIST = Text.ofMessageKey("dkfriends.command.friend.list");
    public static MessageComponent<?> COMMAND_FRIEND_REQUESTS = Text.ofMessageKey("dkfriends.command.friend.requests");
    public static MessageComponent<?> COMMAND_FRIEND_FAVORITE_MARK = Text.ofMessageKey("dkfriends.command.friend.favorite.mark");
    public static MessageComponent<?> COMMAND_FRIEND_FAVORITE_UNMARKT = Text.ofMessageKey("dkfriends.command.friend.favorite.unmark");

    public static MessageComponent<?> COMMAND_FRIEND_JUMP_ALREADY = Text.ofMessageKey("dkfriends.command.friend.jump.already");
    public static MessageComponent<?> COMMAND_FRIEND_JUMP_SUCCESS = Text.ofMessageKey("dkfriends.command.friend.jump.success");
    public static MessageComponent<?> COMMAND_FRIEND_MSG = Text.ofMessageKey("dkfriends.command.friend.msg");

    public static MessageComponent<?> FRIEND_REQUEST = Text.ofMessageKey("dkfriends.friend.request");
    public static MessageComponent<?> FRIEND_DENY = Text.ofMessageKey("dkfriends.friend.deny");
    public static MessageComponent<?> FRIEND_ADD = Text.ofMessageKey("dkfriends.friend.add");
    public static MessageComponent<?> FRIEND_REMOVE = Text.ofMessageKey("dkfriends.friend.remove");
    public static MessageComponent<?> FRIEND_LOGIN = Text.ofMessageKey("dkfriends.friend.login");
    public static MessageComponent<?> FRIEND_LOGOUT = Text.ofMessageKey("dkfriends.friend.logout");

    public static MessageComponent<?> FRIEND_LOGIN_INFO_REQUESTS = Text.ofMessageKey("dkfriends.friend.loginInfo.requests");
    public static MessageComponent<?> FRIEND_LOGIN_INFO_ONE = Text.ofMessageKey("dkfriends.friend.loginInfo.one");
    public static MessageComponent<?> FRIEND_LOGIN_INFO_TWO = Text.ofMessageKey("dkfriends.friend.loginInfo.two");
    public static MessageComponent<?> FRIEND_LOGIN_INFO_THREE = Text.ofMessageKey("dkfriends.friend.loginInfo.three");
    public static MessageComponent<?> FRIEND_LOGIN_INFO_MORE = Text.ofMessageKey("dkfriends.friend.loginInfo.more");



    public static final MessageComponent<?> CLAN_INVITE = Text.ofMessageKey("dkfriends.clan.invite");

    public static final MessageComponent<?> CLAN_KICK = Text.ofMessageKey("dkfriends.clan.kick");
    public static final MessageComponent<?> CLAN_KICK_OTHER = Text.ofMessageKey("dkfriends.clan.kick.other");

    public static final MessageComponent<?> CLAN_PROMOTE = Text.ofMessageKey("dkfriends.clan.promote");
    public static final MessageComponent<?> CLAN_PROMOTE_OTHER = Text.ofMessageKey("dkfriends.clan.promote.other");

    public static final MessageComponent<?> CLAN_DEMOTE = Text.ofMessageKey("dkfriends.clan.promote");
    public static final MessageComponent<?> CLAN_DEMOTE_OTHER = Text.ofMessageKey("dkfriends.clan.promote.other");

    public static final MessageComponent<?> CLAN_JOIN = Text.ofMessageKey("dkfriends.clan.join");


    public static final MessageComponent<?> COMMAND_CLAN_HELP = Text.ofMessageKey("dkfriends.command.clan.help");

    public static final MessageComponent<?> COMMAND_CLAN_CREATE_ALREADY_EXISTS = Text.ofMessageKey("dkfriends.command.clan.create.alreadyExists");
    public static final MessageComponent<?> COMMAND_CLAN_CREATE = Text.ofMessageKey("dkfriends.command.clan.create");

    public static final MessageComponent<?> COMMAND_CLAN_INVITE = Text.ofMessageKey("dkfriends.command.clan.invite");

    public static final MessageComponent<?> COMMAND_CLAN_LEAVE = Text.ofMessageKey("dkfriends.command.clan.leave");

    public static final MessageComponent<?> COMMAND_CLAN_PARTY = Text.ofMessageKey("dkfriends.command.clan.party");

    public static final MessageComponent<?> COMMAND_CLAN_INFO = Text.ofMessageKey("dkfriends.command.clan.info");

    public static final MessageComponent<?> COMMAND_CLAN_DELETE = Text.ofMessageKey("dkfriends.command.clan.delete");

    public static final MessageComponent<?> COMMAND_CLAN_PROMOTE = Text.ofMessageKey("dkfriends.command.clan.promote");

    public static final MessageComponent<?> COMMAND_CLAN_DEMOTE = Text.ofMessageKey("dkfriends.command.clan.demote");

    public static final MessageComponent<?> COMMAND_CLAN_KICK = Text.ofMessageKey("dkfriends.command.clan.kick");

    public static final MessageComponent<?> COMMAND_CLAN_RENAME = Text.ofMessageKey("dkfriends.command.clan.rename");

    public static final MessageComponent<?> COMMAND_CLAN_RETAG = Text.ofMessageKey("dkfriends.command.clan.retag");

    public static final MessageComponent<?> COMMAND_CLAN_ACCEPT = Text.ofMessageKey("dkfriends.command.clan.accept");

    public static final MessageComponent<?> COMMAND_CLAN_DENY = Text.ofMessageKey("dkfriends.command.clan.deny");

    public static MessageComponent<?> COMMAND_PARTY_HELP = Text.ofMessageKey("dkfriends.command.party.help");
    public static MessageComponent<?> COMMAND_PARTY_CREATED = Text.ofMessageKey("dkfriends.command.party.created");
    public static MessageComponent<?> COMMAND_PARTY_INVITED = Text.ofMessageKey("dkfriends.command.party.invited");
    public static MessageComponent<?> COMMAND_PARTY_INFO = Text.ofMessageKey("dkfriends.command.party.info");

    public static MessageComponent<?> PARTY_INVITE = Text.ofMessageKey("dkfriends.party.invite");
    public static MessageComponent<?> PARTY_DENIED = Text.ofMessageKey("dkfriends.party.denied");
    public static MessageComponent<?> PARTY_JOIN = Text.ofMessageKey("dkfriends.party.join");
    public static MessageComponent<?> PARTY_LEAVE = Text.ofMessageKey("dkfriends.party.leave");
    public static MessageComponent<?> PARTY_KICK = Text.ofMessageKey("dkfriends.party.kick");
    public static MessageComponent<?> PARTY_DELETE = Text.ofMessageKey("dkfriends.party.delete");
    public static MessageComponent<?> PARTY_MESSAGE = Text.ofMessageKey("dkfriends.party.message");
    public static MessageComponent<?> PARTY_TELEPORT = Text.ofMessageKey("dkfriends.party.teleport");

}
