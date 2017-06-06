package set;

import org.junit.Before;

import java.util.Comparator;
import java.util.TreeMap;

public class MyTreeSetTest extends SetTest {

    @Before
    public void setUp() {
        Comparator<Integer> comparator = (o1, o2) -> {
            if (o1 < o2) {
                return -1;
            } else if (o1 > o2) {
                return 1;
            } else {
                return 0;
            }
        };
        new TreeMap<>();
        set = new MyTreeSet<>(comparator);
    }
}
