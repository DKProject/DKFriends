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

    public static MessageComponent<?> PREFIX = Text.ofMessageKey("dkfriends.prefix");

    public static MessageComponent<?> ERROR_ONLY_PLAYER = Text.ofMessageKey("dkfriends.error.onlyPlayer");
    public static MessageComponent<?> ERROR_PLAYER_NOT_FOUND = Text.ofMessageKey("dkfriends.error.player.notFound");
    public static MessageComponent<?> ERROR_PLAYER_NOT_ONLINE = Text.ofMessageKey("dkfriends.error.player.notOnline");
    public static MessageComponent<?> ERROR_PLAYER_NOT_SELF = Text.ofMessageKey("dkfriends.error.player.notSelf");

    public static MessageComponent<?> ERROR_FRIEND_ALREADY = Text.ofMessageKey("dkfriends.error.friend.already");
    public static MessageComponent<?> ERROR_FRIEND_NOT = Text.ofMessageKey("dkfriends.error.friend.not");
    public static MessageComponent<?> ERROR_FRIEND_EMPTY = Text.ofMessageKey("dkfriends.error.friend.empty");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_ALREADY = Text.ofMessageKey("dkfriends.error.friend.request.already");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_NOT = Text.ofMessageKey("dkfriends.error.friend.request.not");
    public static MessageComponent<?> ERROR_FRIEND_REQUEST_EMPTY = Text.ofMessageKey("dkfriends.error.friend.request.empty");

    public static MessageComponent<?> COMMAND_FRIEND_ADD_SUCCESS = Text.ofMessageKey("dkfriends.command.friend.add.success");

    public static MessageComponent<?> COMMAND_FRIEND_JUMP_ALREADY = Text.ofMessageKey("dkfriends.command.friend.jump.already");
    public static MessageComponent<?> COMMAND_FRIEND_JUMP_SUCCESS = Text.ofMessageKey("dkfriends.command.friend.jump.success");
    public static MessageComponent<?> COMMAND_FRIEND_MSG = Text.ofMessageKey("dkfriends.command.msg");

}
