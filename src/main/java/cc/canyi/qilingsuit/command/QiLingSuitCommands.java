package cc.canyi.qilingsuit.command;

import cc.canyi.qilingsuit.QiLingSuitPlugin;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class QiLingSuitCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("qilingsuit")) {
            boolean isOp = sender.isOp();
            if(args.length > 0){
                String path = args[0];
                switch (path) {
                    case "reload": {
                        if(isOp) {
                            Bukkit.getScheduler().cancelTasks(QiLingSuitPlugin.getInstance());
                            QiLingSuitPlugin.getTaskCache().setOver(true);
                            ConfigUtils.updateAllSuitFromConfig();
                            PlayerSuitUpdateTask suitUpdateTask = new PlayerSuitUpdateTask();
                            QiLingSuitPlugin.setTaskCache(suitUpdateTask);
                            Bukkit.getScheduler().runTaskTimer(QiLingSuitPlugin.getInstance(), QiLingSuitPlugin.getTaskCache(), 20L, QiLingSuitPlugin.getCheckTime());
                            sender.sendMessage("§6[§a§l✔§6] §a插件全重载完成.");
                        }
                    }
                }
            }
        }
        return true;
    }

}