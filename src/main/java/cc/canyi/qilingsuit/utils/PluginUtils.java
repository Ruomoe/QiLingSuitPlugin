package cc.canyi.qilingsuit.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtils {
    /**
     * 判断插件是否存在
     * @param pluginName 插件名称
     * @return 是否存在
     */
    public static boolean pluginIsActive(String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }

    /**
     * 获取插件
     * @param pluginName 插件名字
     * @return Plugin 接口
     */
    public static Plugin getPlugin(String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName);
    }
}
