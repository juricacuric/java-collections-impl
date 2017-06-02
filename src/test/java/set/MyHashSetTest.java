package set;

import org.junit.Before;

public class MyHashSetTest extends SetTest {

    @Before
    public void setUp() {
        set = new MyHashSet<>(1);
    }
}
