import cc.canyi.qilingsuit.utils.AttrFactory;
import cc.canyi.qilingsuit.utils.AttrStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAttrStringUtils {
    @Test
    public void testGetAttrFactory() {
        AttrFactory factory = AttrStringUtils.getAttrFactory();
        Assertions.assertNotEquals(factory, null);
    }
}
