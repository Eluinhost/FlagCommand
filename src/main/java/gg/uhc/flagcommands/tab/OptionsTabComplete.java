/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.tab.OptionsTabComplete
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

package gg.uhc.flagcommands.tab;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public abstract class OptionsTabComplete implements TabCompleter {

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return onTabComplete(sender, command, alias, args, args[args.length - 1], Arrays.copyOf(args, args.length - 1));
    }

    /**
     * Runs tab complete. Runs if:
     *
     * Completing the first argument
     * OR
     * Arguement doesn't start with -
     * OR
     * Flag doesn't have a completer in the completers map
     *
     * @return list of options, same as TabCompleter
     */
    public abstract List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, String toComplete, String[] others);

    public static final OptionsTabComplete NOTHING = new OptionsTabComplete() {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, String toComplete, String[] others) {
            return ImmutableList.of();
        }
    };
}
