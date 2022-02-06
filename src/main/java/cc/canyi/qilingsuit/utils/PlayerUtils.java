package cc.canyi.qilingsuit.utils;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public static ItemStack generateItem(List<String> lores) {
        ItemStack stack = new ItemStack(Material.STONE);
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lores);
        meta.setDisplayName(QiLingSuitPlugin.class.getSimpleName());
        stack.setItemMeta(meta);
        return stack;
    }
}
