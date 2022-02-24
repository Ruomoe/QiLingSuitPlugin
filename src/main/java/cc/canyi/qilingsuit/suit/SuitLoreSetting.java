package cc.canyi.qilingsuit.suit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class SuitLoreSetting {
    public static enum Location {
        top, bottom, unknown;
        public static Location getLocationFromString(String name) {
            switch (name) {
                case "top": return top;
                case "bottom": return bottom;
                default: return unknown;
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public static class LoreFormat {
        private String have;
        private String notHave;
    }

    private boolean enable;
    private Location loc;
    private String loreFindTop;
    private HashMap<String, LoreFormat> formats;
}
