package list;

import org.junit.Before;

public class MyArrayListTest extends ListTest {

    @Before
    public void setUp() {
        list = new MyArrayList<>(1);
    }
}
