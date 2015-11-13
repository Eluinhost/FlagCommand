FlagCommands
============

Simple library for Bukkit commands.

# OptionCommand

Extending option command allows you to use 'flags' in commands. Uses [joptsimple](https://pholser.github.io/jopt-simple/)
API for registering and parsing flags.

Extend `OptionCommand`, register your flags in the constructor and implement `OptionCommand#runCommand(CommandSender sender, OptionSet options)`

If the user passes in invalid flags they will be told what they did wrong. Also automatically registers `-?,-h,--help`
to show a help message with all of the available flags and their descriptions.

Registers tab complete for any argument starting with a - and completes with flag options, any non dashed args are passed
into the method `OptionCommand#runTabComplete(CommandSender sender, String[] args)` for you to extend and fill out.

For the command:

`/teleport -s -w world -c 0,0 ghowden Eluinhost`

Where:

`-s` - is no-arg flag meaning silent (don't send a message to teleported players)
`-w` - is the world to target
`-c` - are the coords to target, if not provided uses spawn of the world
`other arguments` - are the players to teleport, if none provided teleports all online

[Example source](src/example/java/ExampleOptionCommand.java)

# SubcommandCommand

Allows easy registering/tab completing of subroutes in a command. When an invalid subroute is called the command will
show a usage method. When a valid subroute is called the subroute arg is stripped off and the subroute executor is called
as regular. Also runs tab complete and allows registering of tab completers separate from command executors

```java
class Example extends SubcommandCommand {
		public Example() {
				registerSubcommand("on", new OnCommand());
				registerSubcommand("off", new OffCommand());
		}
}
```

```java
SubcommandCommand sub = new SubcommandCommand();
sub.register("on", new OnCommand());
sub.register("off", new OffCommand());

getCommand("something").setExecutor(sub);
```

If you register something against SubcommandCommand.NO_ARG_SPECIAL then it will no longer show help when ran without an argument
and will instead run the specified executor