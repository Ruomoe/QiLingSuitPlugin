package cc.canyi.qilingsuit.utils;

import org.bukkit.Bukkit;

public class PluginUtils {
    /**
     * 判断插件是否存在
     * @param pluginName 插件名称
     * @return 是否存在
     */
    public static boolean pluginIsActive(String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }
}
