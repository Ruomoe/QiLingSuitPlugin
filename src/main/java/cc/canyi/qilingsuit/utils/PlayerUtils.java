package cc.canyi.qilingsuit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {

    public static void runCommand(Player player, List<String> commands) {
        commands.forEach(command -> runCommand(player, command));
    }
    public static void runCommand(Player player, String command) {
        if(command.startsWith("[console]")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(9));
        }else if(command.startsWith("[op]")) {
            boolean isOp = player.isOp();
            player.setOp(true);
            player.chat("/" + command.substring(4));
            player.setOp(isOp);
        }else {
            player.chat("/" + command);
        }
    }
}
