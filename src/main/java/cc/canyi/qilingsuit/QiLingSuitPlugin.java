package cc.canyi.qilingsuit;

import cc.canyi.qilingsuit.api.QiLingSuitPapi;
import cc.canyi.qilingsuit.command.QiLingSuitCommands;
import cc.canyi.qilingsuit.gui.QiLingSuitStatsGui;
import cc.canyi.qilingsuit.listener.QiLingSuitListener;
import cc.canyi.qilingsuit.suit.SuitLoreSetting;
import cc.canyi.qilingsuit.task.PlayerSuitUpdateTask;
import cc.canyi.qilingsuit.utils.AttrStringUtils;
import cc.canyi.qilingsuit.utils.ConfigUtils;
import cc.canyi.qilingsuit.utils.PluginUtils;
import lombok.Getter;
import lombok.Setter;
import cc.canyi.qilingsuit.api.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class QiLingSuitPlugin extends JavaPlugin {
    @Getter
    private static QiLingSuitPlugin instance;

    @Getter
    private static String root;
    @Getter
    @Setter
    private static boolean debug;
    @Getter
    @Setter
    private static long checkTime;

    @Getter
    @Setter
    private static PlayerSuitUpdateTask taskCache;

    @Getter
    private static final Random random = new Random();

    @Getter
    private static final int PLUGIN_ID = 14299;

    @Getter
    private static Metrics metrics;

    @Getter
    @Setter
    private static SuitLoreSetting suitLoreSetting;

    @Override
    public void onEnable() {
        //init instance
        instance = this;

        root = this.getDataFolder().getAbsolutePath();
        this.saveDefaultConfig();

        //init buff HashMap
        AttrStringUtils.initBuffHashMap();

        //Config init all suit
        ConfigUtils.init();

        //Register bukkit command
        Bukkit.getPluginCommand("qilingsuit").setExecutor(new QiLingSuitCommands());
        //init commands
        QiLingSuitCommands.init();

        //Register listener
        Bukkit.getPluginManager().registerEvents(new QiLingSuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new QiLingSuitStatsGui.GuiListener(), this);

        if(PluginUtils.pluginIsActive("PlaceholderAPI")) {
            //Loaded PlaceholderAPI
            boolean register = new QiLingSuitPapi().register();
            this.getLogger().info("PlaceholderAPI hooked " + (register ? "success" : "failed"));
        }

        this.getLogger().info("Running Metrics...");
        long time = System.currentTimeMillis();
        metrics = new Metrics(this, PLUGIN_ID);
        this.getLogger().info("Running Metrics success, cast " + (System.currentTimeMillis() - time) + "ms");

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
