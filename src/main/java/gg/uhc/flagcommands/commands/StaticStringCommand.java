/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.commands.StaticStringCommand
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Graham Howden <graham_howden1 at yahoo.co.uk>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gg.uhc.flagcommands.commands;

import com.google.common.base.Optional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaticStringCommand implements CommandExecutor {

    protected Optional<String> message;

    /**
     * Creates a string command with no initial message
     */
    public StaticStringCommand() {
        this.message = Optional.absent();
    }

    /**
     * Creates a string command that returns the given message.
     *
     * @param message the message to send
     */
    public StaticStringCommand(String message) {
        this.message = Optional.fromNullable(message);
    }

    /**
     * @return the message we are sending or null if non is set
     */
    public String getMessage() {
        return message.orNull();
    }

    /**
     * Sets the message to send. If the message is null then usage is shown from plugin.yml instead.
     *
     * @param message the message to send
     */
    public void setMessage(String message) {
        this.message = Optional.fromNullable(message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (message.isPresent()) {
            sender.sendMessage(message.get());
            return true;
        }

        return false;
    }
}
