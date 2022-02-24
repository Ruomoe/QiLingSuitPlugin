package cc.canyi.qilingsuit.command.sub;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.command.IQiLingSuitCommand;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements IQiLingSuitCommand {
    @Override
    public void runCommand(String[] args, CommandSender sender) {
        if(sender.isOp()) {
            Bukkit.getScheduler().cancelTasks(QiLingSuitPlugin.getInstance());
            QiLingSuitPlugin.getTaskCache().setOver(true);
            ConfigUtils.updateAllSuitFromConfig();
            PlayerSuitUpdateTask suitUpdateTask = new PlayerSuitUpdateTask();
            QiLingSuitPlugin.setTaskCache(suitUpdateTask);
            Bukkit.getScheduler().runTaskTimer(QiLingSuitPlugin.getInstance(), QiLingSuitPlugin.getTaskCache(), 20L, QiLingSuitPlugin.getCheckTime());
            sender.sendMessage("§6[§a§l✔§6] §a插件全重载完成.");
        }
    }
    @Override
    public void register(String path) {

    }
}
