package cc.canyi.qilingsuit.utils;

public class Logger {
    /**
     * 输出信息并带有插件前缀
     * @param message 信息
     */
    public static void systemOutWithPrefix(String message) {
        System.out.println("[QiLingSuit] >>> " + message);
    }

    /**
     * 输出错误信息并带插件前缀
     * @param message 错误信息
     */
    public static void systemOutErrorWithPrefix(String message) {
        System.out.println("[QiLingSuit-ERROR] >>> " + message);
    }
}
