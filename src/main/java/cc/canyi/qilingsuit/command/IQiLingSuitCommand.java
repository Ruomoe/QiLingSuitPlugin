package cc.canyi.qilingsuit.command;

import org.bukkit.command.CommandSender;

public interface IQiLingSuitCommand {
    void runCommand(String[] args, CommandSender sender);

    void register(String path);
}
