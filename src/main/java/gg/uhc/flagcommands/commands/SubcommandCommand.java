/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.commands.SubcommandCommand
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

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.command.*;
import org.bukkit.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubcommandCommand implements TabExecutor {

    protected final Map<String, CommandExecutor> commandExecutors = Maps.newHashMap();
    protected final Map<String, TabCompleter> tabCompleters = Maps.newHashMap();

    public static final String NO_ARG_SPECIAL = "<no argument>";

    /**
     * Register a TabExecutor for tab complete and command execution. Does not allow reregistering of subroutes.
     *
     * @param name the subcommand to run for
     * @param tabExecutor the tab executor to register
     *
     * @see SubcommandCommand#registerSubcommand(String, CommandExecutor)
     * @see SubcommandCommand#registerSubcommand(String, TabCompleter)
     */
    public void registerSubcommand(String name, TabExecutor tabExecutor) {
        name = name.toLowerCase();
        Preconditions.checkState(!commandExecutors.containsKey(name), "A CommandExecutor for the route `" + name + "` is already registered");
        Preconditions.checkState(!tabCompleters.containsKey(name), "A TabCompleter for the route `" + name + "` is already registered");

        commandExecutors.put(name, tabExecutor);
        tabCompleters.put(name, tabExecutor);
    }

    /**
     * Regsister a CommandExecutor to run on commands. Does not allow reregistering of subroutes.
     *
     * Register with NO_ARG_SPECIAL to override sending help when not provided.
     *
     * @param name the subcommand to run for
     * @param executor the executor to run
     *
     * @see SubcommandCommand#registerSubcommand(String, TabExecutor)
     * @see SubcommandCommand#registerSubcommand(String, TabCompleter)
     */
    public void registerSubcommand(String name, CommandExecutor executor) {
        name = name.toLowerCase();
        Preconditions.checkState(!commandExecutors.containsKey(name), "A CommandExecutor for the route `" + name + "` is already registered");

        commandExecutors.put(name, executor);
    }

    /**
     * Register a TabCompleter to run on tab complete. Does not allow reregistering of subroutes.
     *
     * @param name the subcommand to tab complete for
     * @param tabCompleter the completer to run
     *
     * @see SubcommandCommand#registerSubcommand(String, TabExecutor)
     * @see SubcommandCommand#registerSubcommand(String, CommandExecutor)
     */
    public void registerSubcommand(String name, TabCompleter tabCompleter) {
        name = name.toLowerCase();
        Preconditions.checkState(!tabCompleters.containsKey(name), "A TabCompleter for the route `" + name + "` is already registered");

        tabCompleters.put(name, tabCompleter);
    }

    protected void sendUsage(CommandSender sender, String[] args) {
        Set<String> keys = commandExecutors.keySet();

        StringBuilder builder = new StringBuilder();

        if (args.length == 0) {
            builder.append("Missing a required argument at the end of your command.");
        } else {
            builder.append("Invalid argument `")
                    .append(args[0])
                    .append("`.");
        }

        builder.append(" Allowed arguments: [")
                .append(Joiner.on(",").join(keys))
                .append("]");

        sender.sendMessage(builder.toString());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // if no args given check for a default executor
        CommandExecutor subcommand = commandExecutors.get(args.length == 0 ? NO_ARG_SPECIAL : args[0].toLowerCase());

        // invalid subcommands send usage
        if (subcommand == null) {
            sendUsage(sender, args);
            return true;
        }

        // cut subroute arg off and run subcommand
        return subcommand.onCommand(sender, command, label, Arrays.copyOfRange(args, Math.min(args.length, 1), args.length));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // if it's completing the first argument then complete from the list of subcommands
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], commandExecutors.keySet(), Lists.<String>newArrayListWithCapacity(commandExecutors.size()));
        }

        TabCompleter subcommand = tabCompleters.get(args[0].toLowerCase());

        // if the subcommand doesn't exists then just return empty
        if (subcommand == null) return ImmutableList.of();

        // cut the first arg off and run the subcommand tab complete
        return subcommand.onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
    }
}
