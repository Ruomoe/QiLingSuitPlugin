package cc.canyi.qilingsuit.command;

import cc.canyi.qilingsuit.command.sub.HelpCommand;
import cc.canyi.qilingsuit.command.sub.ReloadCommand;
import cc.canyi.qilingsuit.command.sub.StatsCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;


public class QiLingSuitCommands implements CommandExecutor {
    private static final HashMap<String, IQiLingSuitCommand> commands = new HashMap<>();

    public static void addCommand(String path, IQiLingSuitCommand command) {
        command.register(path);
        commands.put(path, command);
    }

    /**
     * 初始化所有指令
     */
    public static void init() {
        addCommand("reload", new ReloadCommand());
        addCommand("stats", new StatsCommand());
        addCommand("help", new HelpCommand());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("qilingsuit")) {
            if(args.length == 0) {
                if(commands.containsKey("help"))
                    commands.get("help").runCommand(args, sender);
                return true;
            }
            String path = args[0];
            if (commands.containsKey(path)) {
                commands.get(path).runCommand(args, sender);
                return true;
            }
        }
        return true;
    }

}