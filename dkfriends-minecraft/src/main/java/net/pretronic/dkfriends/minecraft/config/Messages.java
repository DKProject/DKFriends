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

    public static MessageComponent<?> ERROR_PARTY_NOT = Text.ofMessageKey("dkfriends.error.party.not");
    public static MessageComponent<?> ERROR_PARTY_ALREADY = Text.ofMessageKey("dkfriends.error.party.already");
    public static MessageComponent<?> ERROR_PARTY_NOT_ALLOWED = Text.ofMessageKey("dkfriends.error.party.notAllowed");
    public static MessageComponent<?> ERROR_PARTY_NOT_MEMBER = Text.ofMessageKey("dkfriends.error.party.notMember");
    public static MessageComponent<?> ERROR_PARTY_INVITATION_NOT = Text.ofMessageKey("dkfriends.error.party.invitation.not");

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

    public static MessageComponent<?> COMMAND_PARTY_CREATED = Text.ofMessageKey("dkfriends.command.party.created");
    public static MessageComponent<?> COMMAND_PARTY_INVITED = Text.ofMessageKey("dkfriends.command.party.invited");
    public static MessageComponent<?> COMMAND_PARTY_INFO = Text.ofMessageKey("dkfriends.command.party.info");

    public static MessageComponent<?> PARTY_INVITE = Text.ofMessageKey("dkfriends.party.invite");

}
