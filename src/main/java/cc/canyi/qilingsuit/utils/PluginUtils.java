package cc.canyi.qilingsuit.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class PluginUtils {
    /**
     * 判断插件是否存在
     * @param pluginName 插件名称
     * @return 是否存在
     */
    public static boolean pluginIsActive(String pluginName) {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins()).anyMatch(plugin -> plugin.getName().equals(pluginName));
//        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }

    public static Plugin getPlugin(String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }
}
