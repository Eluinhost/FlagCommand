/*
 * Project: FlagCommands
 * Class: ExampleOptionCommand
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

import com.google.common.collect.ImmutableList;
import gg.uhc.flagcommands.commands.OptionCommand;
import gg.uhc.flagcommands.converters.DoubleConverter;
import gg.uhc.flagcommands.converters.OnlinePlayerConverter;
import gg.uhc.flagcommands.converters.WorldConverter;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class ExampleOptionCommand extends OptionCommand {

    protected static final String RESPONSE = ChatColor.AQUA + "Teleported %d players to %s %d:%d:%d";
    protected static final String TELEPORTED_MESSAGE = ChatColor.AQUA + "You were teleported to %s %d:%d:%d";

    protected final OptionSpec<Player> playerSpec;
    protected final OptionSpec<World> worldSpec;
    protected final OptionSpec<Double> coordsSpec;
    protected final OptionSpec<Void> silentSpec;

    public ExampleOptionCommand() {
        worldSpec = parser
                .acceptsAll(ImmutableList.of("w", "world"), "The world to teleport into")
                .withRequiredArg()
                .withValuesConvertedBy(new WorldConverter())
                .required();

        coordsSpec = parser
                .acceptsAll(ImmutableList.of("c", "coords"), "The coordinates to teleport into format `x,y,z`, defaults to the spawn of the given world")
                .withRequiredArg()
                .withValuesSeparatedBy(',')
                .withValuesConvertedBy(new DoubleConverter().setType("x,y,z"));

        silentSpec = parser
                .acceptsAll(ImmutableList.of("s", "silent"), "Does not send a message to the teleported players");

        playerSpec = parser
                .nonOptions("List of players to teleport, if none provided all will be teleported")
                .withValuesConvertedBy(new OnlinePlayerConverter());
    }

    @Override
    protected boolean runCommand(CommandSender sender, OptionSet options) {
        World world = worldSpec.value(options);

        Location loc = world.getSpawnLocation();

        // if it has coords provided move the location
        if (options.has(coordsSpec)) {
            List<Double> coords = coordsSpec.values(options);

            if (coords.size() != 3) {
                sender.sendMessage(ChatColor.RED + "Must provide 3 coordinates `x,y,z`");
                return true;
            }

            loc.setX(coords.get(0));
            loc.setY(coords.get(1));
            loc.setZ(coords.get(2));
        }

        Collection<? extends Player> players = playerSpec.values(options);

        // if none provided use all online
        if (players.size() == 0) {
            players = Bukkit.getOnlinePlayers();
        }

        boolean silent = options.has(silentSpec);
        String message = String.format(TELEPORTED_MESSAGE, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());

        for (Player player : players) {
            player.teleport(loc);

            if (!silent) {
                player.sendMessage(message);
            }
        }

        sender.sendMessage(String.format(RESPONSE, players.size(), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ()));
        return true;
    }
}
