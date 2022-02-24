package cc.canyi.qilingsuit.command.sub;

import cc.canyi.qilingsuit.command.IQiLingSuitCommand;
import cc.canyi.qilingsuit.gui.QiLingSuitStatsGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements IQiLingSuitCommand {
    @Override
    public void runCommand(String[] args, CommandSender sender) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(QiLingSuitStatsGui.getStatsGui(player));
        }else sender.sendMessage("仅可以玩家执行!!");
    }

    @Override
    public void register(String path) {

    }
}
