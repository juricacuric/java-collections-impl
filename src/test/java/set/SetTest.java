package set;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public abstract class SetTest {

    protected Set<Integer> set;

    @Test
    public void size_emptySet() {
        Assert.assertEquals(0, set.size());
    }

    @Test
    public void size_hasElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void isEmpty_emptySet() {
        Assert.assertEquals(true, set.isEmpty());
    }

    @Test
    public void isEmpty_hasElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        Assert.assertEquals(false, set.isEmpty());
    }

    @Test
    public void add_differentElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        Assert.assertEquals(5, set.size());
    }

    @Test
    public void add_sameElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);
        set.add(2);
        set.add(3);
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void contains_emptySet() {
        Assert.assertEquals(false, set.contains(1));
    }

    @Test
    public void contains_nonExistingElement() {
        set.add(1);
        set.add(2);
        set.add(3);
        Assert.assertEquals(false, set.contains(4));
    }

    @Test
    public void contains_existingElement() {
        set.add(1);
        set.add(2);
        set.add(3);
        Assert.assertEquals(true, set.contains(2));
    }

    @Test
    public void clear_emptySet() {
        set.clear();
        Assert.assertEquals(true, set.isEmpty());
    }

    @Test
    public void clear_hasElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.clear();
        Assert.assertEquals(true, set.isEmpty());
    }

    @Test
    public void remove_emptySet() {
        set.remove(1);
        Assert.assertEquals(true, set.isEmpty());
    }

    @Test
    public void remove_nonExistingElement() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.remove(4);
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void remove_existingElement() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.remove(3);
        Assert.assertEquals(2, set.size());
    }

    @Test
    public void containsAll_empty() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        Assert.assertEquals(true, set.containsAll(helpSet));
    }

    @Test
    public void containsAll_hasAll() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);
        Assert.assertEquals(true, set.containsAll(helpSet));
    }

    @Test
    public void containsAll_hasSome() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);
        helpSet.add(5);
        Assert.assertEquals(false, set.containsAll(helpSet));
    }

    @Test
    public void addAll_addToEmptySet() {
        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);
        helpSet.add(5);

        set.addAll(helpSet);
        Assert.assertEquals(3, set.size());
    }

    @Test
    public void addAll_addNonExistingElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(5);
        helpSet.add(6);
        helpSet.add(7);

        set.addAll(helpSet);
        Assert.assertEquals(7, set.size());
    }

    @Test
    public void addAll_addExistingElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(7);
        helpSet.add(3);

        set.addAll(helpSet);
        Assert.assertEquals(5, set.size());
    }

    @Test
    public void retainAll_emptyRetainCollection() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();

        set.retainAll(helpSet);
        Assert.assertEquals(0, set.size());
    }

    @Test
    public void retainAll_retainCollectionWithElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);

        set.retainAll(helpSet);
        Assert.assertEquals(2, set.size());
        Assert.assertEquals(true, set.contains(1));
        Assert.assertEquals(true, set.contains(2));
        Assert.assertEquals(false, set.contains(3));
        Assert.assertEquals(false, set.contains(4));
    }

    @Test
    public void retainAll_retainCollectionIdentical() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);
        helpSet.add(3);
        helpSet.add(4);

        set.retainAll(helpSet);
        Assert.assertEquals(4, set.size());
    }

    @Test
    public void toArray_emptySet() {
        Object[] objects = set.toArray();

        Assert.assertEquals(0, objects.length);
    }

    @Test
    public void toArray_setHasElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Object[] objects = set.toArray();
        boolean containsAll = true;
        for (Object o : objects) {
            containsAll = containsAll && set.contains(o);
        }

        Assert.assertEquals(true, containsAll);
    }

    @Test
    public void removeAll_emptyRemoveCollection() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();

        set.removeAll(helpSet);
        Assert.assertEquals(4, set.size());
    }

    @Test
    public void removeAll_removeCollectionWithElements() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);

        set.removeAll(helpSet);
        Assert.assertEquals(2, set.size());
        Assert.assertEquals(false, set.contains(1));
        Assert.assertEquals(false, set.contains(2));
        Assert.assertEquals(true, set.contains(3));
        Assert.assertEquals(true, set.contains(4));
    }

    @Test
    public void removeAll_removeCollectionIdentical() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        Set<Integer> helpSet = new HashSet<>();
        helpSet.add(1);
        helpSet.add(2);
        helpSet.add(3);
        helpSet.add(4);

        set.removeAll(helpSet);
        Assert.assertEquals(0, set.size());
    }

    @Test
    public void iterator_test() {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        int counter = 0;
        for (Integer i : set) {
            counter++;
        }

        Assert.assertEquals(counter, set.size());
    }


}

