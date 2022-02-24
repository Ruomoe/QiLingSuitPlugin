package cc.canyi.qilingsuit.command.sub;

import cc.canyi.qilingsuit.command.IQiLingSuitCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand implements IQiLingSuitCommand {
    private static final String[] helps = new String[] {
            "§6§l------§9&l启灵套装&a&l插件§6§l------",
            "&a使用 &5/qilingsuit help &3-> &e查看指令帮助",
            "&a使用 &5/qilingsuit stats &3-> &e打开状态栏GUI",
            "&a使用 &5/qilingsuit create <suitName> &3-> &e创建一个套装",
            "&a使用 &5/qilingsuit edit <suitName> &3-> &e可视化修改一个套装",
            "&a使用 &5/qilingsuit reload &3-> &e全重载插件"
    };

    @Override
    public void runCommand(String[] args, CommandSender sender) {
        for(String help : helps)
            sender.sendMessage(help);
    }

    @Override
    public void register(String path) {

    }
}
