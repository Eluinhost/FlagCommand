/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.commands.OptionCommand
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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import gg.uhc.flagcommands.tab.OptionsTabComplete;
import joptsimple.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class OptionCommand implements TabExecutor {

    protected final Set<String> argumentsNotToTabComplete = Sets.newHashSet("-[arguments]");
    protected static final Dequoter dequote = new Dequoter();

    protected final OptionParser parser;
    protected final OptionSpec<Void> forHelp;
    protected final Map<ArgumentAcceptingOptionSpec, OptionsTabComplete> completers = Maps.newHashMap();
    protected OptionsTabComplete nonOptionsTabComplete = OptionsTabComplete.NOTHING;

    public OptionCommand() {
        parser = new OptionParser();

        // add help flag for -h -? and --help
        this.forHelp = parser.acceptsAll(ImmutableList.of("h", "?", "help"), "Displays help messages").forHelp();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // parse the command
            OptionSet options = parser.parse(dequote.dequote(args));

            // if they're requesting help show help and exit
            if (options.has(forHelp)) {
                sendHelp(sender);
                return true;
            }

            // run the command with the parsed OptionSet
            return runCommand(sender, options);
        } catch (OptionException ex) {
            String message = ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage();

            // send them the problem and a hint to use the help
            sender.sendMessage(ChatColor.RED + message + ". Use flag `-?` for help");
            return true;
        }
    }

    /**
     * Handles sending help to the sender. Show all of the options with default e.t.c.
     *
     * @param sender the sender to send help to
     */
    protected void sendHelp(CommandSender sender) {
        Set<OptionSpec<?>> specs = Sets.newHashSet(parser.recognizedOptions().values());

        StringBuilder builder = new StringBuilder();
        for (OptionSpec spec : specs) {
            // use the descriptor version
            OptionDescriptor desc = (OptionDescriptor) spec;

            builder.setLength(0);
            builder.append(ChatColor.LIGHT_PURPLE);

            if (desc.isRequired()) builder.append(ChatColor.BOLD);

            if (desc.representsNonOptions()) {
                // skip if no description is given for non options
                // because non options always exists
                if ("".equals(desc.description())) continue;

                builder.append("Other arguments");
            } else {
                builder.append(desc.options());
            }

            builder.append(ChatColor.RESET);
            builder.append(ChatColor.GRAY);

            builder.append(" - ");

            if (desc.acceptsArguments()) {
                builder.append("Arg: ");
                builder.append(desc.argumentTypeIndicator());
                builder.append(" - ");
            }

            builder.append(desc.description());

            List defaults = desc.defaultValues();

            if (defaults.size() > 0) {
                builder.append(" Defaults: ");
                // hope they're string serializable
                builder.append(defaults);
            }

            sender.sendMessage(builder.toString());
        }
    }

    /**
     * The actual command method. Runs after parsing completes. Use the OptionSet provided and any saved OptionSpec
     * to use the values in the command.
     *
     * @param sender the initiator of the command
     * @param options the parsed options
     *
     * @return true/false the same as a regular CommandExecutor
     */
    protected abstract boolean runCommand(CommandSender sender, OptionSet options);

    protected List<String> tabCompleteFlags(String toComplete, String[] others) {
        // filter the args to only ones starting with a dash
        final Set<String> parameters = Sets.newHashSet(Iterables.filter(Lists.newArrayList(others), STARTS_WITH_DASH));

        Set<String> available = Sets.newHashSet();

        for (OptionSpec<?> spec : parser.recognizedOptions().values()) {
            // add the dash onto each of the options in the spec
            Iterable<String> params = Iterables.transform(spec.options(), PREPEND_DASH);

            // add to the available set if all of it's options are not in the not-to-complete list
            // and it wasn't already provided in the command
            boolean add = !Iterables.any(params, new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return argumentsNotToTabComplete.contains(input) || parameters.contains(input);
                }
            });

            if (add) {
                Iterables.addAll(available, params);
            }
        }

        // return applicable ones
        return StringUtil.copyPartialMatches(toComplete, available, Lists.<String>newArrayList());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String complete = args[args.length - 1];
        String[] others = new String[args.length - 1];
        System.arraycopy(args, 0, others, 0, args.length - 1);

        // if it's the first argument
        if (others.length == 0) {
            List<String> all = Lists.newArrayList();

            // add non-options
            all.addAll(nonOptionsTabComplete.onTabComplete(sender, command, alias, args, complete, others));

            // add all flags
            all.addAll(tabCompleteFlags(complete, others));

            return all;
        }

        if (complete.length() != 0 && complete.charAt(0) == '-') {
            return tabCompleteFlags(complete, others);
        }

        // check previous word, if it was a flag show the tab complete for that arg if we have one
        // otherwise just show generic tab complete from the nonOptions
        String previous = others[others.length - 1];

        if (previous.length() == 0 || previous.charAt(0) != '-') {
            return nonOptionsTabComplete.onTabComplete(sender, command, alias, args, complete, others);
        }

        String option = previous.substring(1);

        // handle -- flags
        if (option.length() > 1 && option.charAt(0) == '-') {
            option = option.substring(1);
        }

        OptionSpec spec = parser.recognizedOptions().get(option);

        if (spec == null || !(spec instanceof ArgumentAcceptingOptionSpec)) {
            return nonOptionsTabComplete.onTabComplete(sender, command, alias, args, complete, others);
        }

        OptionsTabComplete completer = completers.get(spec);

        if (completer == null) {
            return nonOptionsTabComplete.onTabComplete(sender, command, alias, args, complete, others);
        }

        return completer.onTabComplete(sender, command, alias, args, complete, others);
    }

    protected static final Predicate<String> STARTS_WITH_DASH = new Predicate<String>() {
        @Override
        public boolean apply(String input) {
            return input != null && input.length() > 0 && input.charAt(0) == '-';
        }
    };

    protected static final Function<String, String> PREPEND_DASH = new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input == null ? "-" : '-' + input;
        }
    };
}
