package cc.canyi.qilingsuit.suit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class Suit {
    private final String name;
    private List<String> containPart;
    private HashMap<String, String> partNeedLoreMap;
    private boolean extendsAttr;
    private HashMap<Integer, List<String>> attrStrMap;

    private SuitPassives passives;

}
