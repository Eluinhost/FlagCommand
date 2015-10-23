/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.tab.FixedValuesTabComplete
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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FixedValuesTabComplete extends OptionsTabComplete {

    protected final Set<String> values;

    public FixedValuesTabComplete(Collection<String> values) {
        this.values = ImmutableSet.copyOf(values);
    }

    public FixedValuesTabComplete(String... values) {
        this.values = ImmutableSet.copyOf(values);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, String toComplete, String[] others) {
        return StringUtil.copyPartialMatches(toComplete, values, Lists.<String>newArrayList());
    }
}
