/*
 * Project: FlagCommands
 * Class: gg.uhc.uhc.commands.TeamNameTabCompleter
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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.StringUtil;

import java.util.List;

public class TeamNameTabComplete extends OptionsTabComplete {

    protected final Scoreboard scoreboard;

    public TeamNameTabComplete(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, String complete, String[] others) {
        return StringUtil.copyPartialMatches(complete, Iterables.transform(scoreboard.getTeams(), TEAM_NAME), Lists.<String>newArrayList());
    }

    protected static final Function<Team, String> TEAM_NAME = new Function<Team, String>() {
        @Override
        public String apply(Team input) {
            return input.getName();
        }
    };
}
