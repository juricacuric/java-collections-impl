package set;

import org.junit.Before;

import java.util.Comparator;

public class MyTreeSetTest extends SetTest {

    @Before
    public void setUp() {
        Comparator<Integer> comparator = (o1, o2) -> {
            if (o1.intValue() < o2.intValue()) {
                return -1;
            } else if (o1.intValue() > o2.intValue()) {
                return 1;
            } else {
                return 0;
            }
        };
        set = new MyTreeSet<>(comparator);
    }
}
