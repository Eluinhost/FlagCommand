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
