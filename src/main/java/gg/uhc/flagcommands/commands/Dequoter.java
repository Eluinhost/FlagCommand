/*
 * Project: FlagCommands
 * Class: gg.uhc.flagcommands.commands.Dequoter
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
import com.google.common.collect.Lists;

import java.util.List;

class Dequoter {

    protected static final char ESCAPE = '\\';
    protected static final char QUOTE = '"';
    protected static final char SPACE = ' ';

    protected boolean inQuote;
    protected boolean escaped;
    protected StringBuilder builder;
    protected List<String> dequoted;

    protected void processChar(char character) {
        switch (character) {
            case QUOTE:
                if (escaped) {
                    stopEscape(QUOTE);
                } else if (inQuote) {
                    inQuote = false;
                    addNewDequoted();
                } else {
                    inQuote = true;
                }
                break;
            case SPACE:
                if (escaped) {
                    stopEscape(SPACE);
                } else if (inQuote) {
                    builder.append(SPACE);
                } else {
                    addNewDequoted();
                }
                break;
            case ESCAPE:
                if (escaped) {
                    stopEscape(ESCAPE);
                } else {
                    escaped = true;
                }
                break;
            default:
                if (escaped) {
                    stopEscape(ESCAPE);
                }
                builder.append(character);
        }
    }

    protected void stopEscape(char character) {
        builder.append(character);
        escaped = false;
    }

    protected void addNewDequoted() {
        if (builder.length() != 0) {
            dequoted.add(builder.toString());
            builder.setLength(0);
        }
    }

    public String[] dequote(String[] args) {
        char[] process = Joiner.on(" ").join(args).toCharArray();

        // reset vars
        inQuote = false;
        escaped = false;
        builder = new StringBuilder();
        dequoted = Lists.newArrayListWithCapacity(args.length);

        for (char proces : process) {
            processChar(proces);
        }

        // handle final token
        addNewDequoted();

        return dequoted.toArray(new String[dequoted.size()]);
    }
}
