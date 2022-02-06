package cc.canyi.qilingsuit.suit;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class SuitPassives {
    private HashMap<String, Double> beAttack;
    private HashMap<String, Double> attacked;
    private List<String> equipAll;
    private List<String> takeoffFromAll;
}
