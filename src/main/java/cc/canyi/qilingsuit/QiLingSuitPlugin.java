package cc.canyi.qilingsuit;

import cc.canyi.qilingsuit.command.QiLingSuitCommands;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.AttrStringUtils;
import cc.canyi.qilingsuit.utils.ConfigUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class QiLingSuitPlugin extends JavaPlugin {
    @Getter
    private static QiLingSuitPlugin instance;

    @Getter
    private static String root;
    @Getter
    private static boolean debug;
    @Getter
    @Setter
    private static long checkTime;

    @Getter
    @Setter
    private static PlayerSuitUpdateTask taskCache;

    @Override
    public void onEnable() {
        //init instance
        instance = this;

        root = this.getDataFolder().getAbsolutePath();
        this.saveDefaultConfig();

        //check debug mode.
        debug = this.getConfig().getBoolean("DebugMode");

        //init buff HashMap
        AttrStringUtils.initBuffHashMap();

        //Config init all suit
        ConfigUtils.init();

        //Register bukkit command
        Bukkit.getPluginCommand("qilingsuit").setExecutor(new QiLingSuitCommands());

        this.getLogger().info("QiLingSuitPlugin Enabled.");

        //Run task with Bukkit scheduler
        taskCache = new PlayerSuitUpdateTask();
        Bukkit.getScheduler().runTaskTimer(this, taskCache, checkTime, checkTime);

        Bukkit.getConsoleSender().sendMessage(
                "\n§b   ____     _     __     _                     _____            _    __           ______                __  __    _ \n" +
                "§b  / __ \\   (_)   / /    (_)   ____    ____ _  / ___/  __  __   (_)  / /_         / ____/  ____ _   ____ \\ \\/ /   (_)\n" +
                "§b / / / /  / /   / /    / /   / __ \\  / __ `/  \\__ \\  / / / /  / /  / __/ ______ / /      / __ `/  / __ \\ \\  /   / / \n" +
                "§b/ /_/ /  / /   / /___ / /   / / / / / /_/ /  ___/ / / /_/ /  / /  / /_  /_____// /___   / /_/ /  / / / / / /   / /  \n" +
                "§b\\___\\_\\ /_/   /_____//_/   /_/ /_/  \\__, /  /____/  \\__,_/  /_/   \\__/         \\____/   \\__,_/  /_/ /_/ /_/   /_/   \n" +
                "§b                                   /____/                                                                           "
        );
        Bukkit.getConsoleSender().sendMessage(" ");


    }


}