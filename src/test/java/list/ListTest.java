package list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class ListTest {

    protected List<Integer> list;

    @Test
    public void emptyList_size() {
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void emptyList_addFirst() {
        list.add(1);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void list_addMultipleElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(4, list.size());
    }

    @Test
    public void list_addList() {
        List<Integer> listForAdding = new ArrayList<>();
        listForAdding.add(1);
        listForAdding.add(2);
        listForAdding.add(3);
        listForAdding.add(4);

        list.addAll(listForAdding);

        Assert.assertEquals(4, list.size());
    }

    @Test
    public void emptyList_isEmpty() {
        Assert.assertEquals(true, list.isEmpty());
    }

    @Test
    public void list_isEmpty() {
        list.add(1);
        Assert.assertEquals(false, list.isEmpty());
    }

    @Test
    public void emptyList_remove() {
        Assert.assertEquals(false, list.remove(new Integer(1)));
    }

    @Test
    public void remove_removeFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(new Integer(1));
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(2, list.get(0).intValue());
        Assert.assertEquals(3, list.get(1).intValue());
        Assert.assertEquals(4, list.get(2).intValue());
    }

    @Test
    public void remove_removeFromMiddle() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(new Integer(2));
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(3, list.get(1).intValue());
        Assert.assertEquals(4, list.get(2).intValue());
    }

    @Test
    public void remove_removeLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(new Integer(4));
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void removeAll_emptyList() {
        List<Integer> listForRemoval = new ArrayList<>();
        list.removeAll(listForRemoval);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void list_removeEmptyList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> listForRemoval = new ArrayList<>();
        list.removeAll(listForRemoval);
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void list_removeList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> listForRemoval = new ArrayList<>();
        listForRemoval.add(1);
        listForRemoval.add(2);
        list.removeAll(listForRemoval);

        Assert.assertEquals(3, list.get(0).intValue());
        Assert.assertEquals(4, list.get(1).intValue());
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void contains_empty() {
        Assert.assertEquals(false, list.contains(1));
    }

    @Test
    public void contains_noElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Assert.assertEquals(false, list.contains(5));
    }

    @Test
    public void contains_middleElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Assert.assertEquals(true, list.contains(3));
    }

    @Test
    public void toArray_hasElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Object[] objects = list.toArray();
        Assert.assertEquals(list.size(), objects.length);
        Assert.assertEquals(true, list.contains(1));
        Assert.assertEquals(true, list.contains(2));
        Assert.assertEquals(true, list.contains(3));
        Assert.assertEquals(true, list.contains(4));
    }

    @Test
    public void containsAll_empty() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        Assert.assertEquals(true, list.containsAll(helpList));
    }

    @Test
    public void containsAll_hasAll() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(1);
        helpList.add(2);
        Assert.assertEquals(true, list.containsAll(helpList));
    }

    @Test
    public void containsAll_hasSome() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(1);
        helpList.add(2);
        helpList.add(5);
        Assert.assertEquals(false, list.containsAll(helpList));
    }

    @Test
    public void containsAll_hasNone() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(5);
        helpList.add(6);
        helpList.add(7);
        Assert.assertEquals(false, list.containsAll(helpList));
    }

    @Test
    public void retainAll_differentList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(5);
        helpList.add(6);
        helpList.add(7);

        list.retainAll(helpList);
        Assert.assertEquals(0, list.size());
        Assert.assertEquals(true, list.isEmpty());
    }

    @Test
    public void retainAll_sameList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(1);
        helpList.add(2);
        helpList.add(3);
        helpList.add(4);

        list.retainAll(helpList);
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void retainAll_similarList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(1);
        helpList.add(2);

        list.retainAll(helpList);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void clear_ListWithFewElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.clear();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void get_ListWithFewElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
        Assert.assertEquals(4, list.get(3).intValue());
    }

    @Test
    public void indexOf_nonExistingElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(-1, list.indexOf(7));
    }

    @Test
    public void indexOf_existingElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(0, list.indexOf(1));
        Assert.assertEquals(1, list.indexOf(2));
        Assert.assertEquals(2, list.indexOf(3));
        Assert.assertEquals(3, list.indexOf(4));
    }

    @Test
    public void lastIndexOf_nonExistingElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(-1, list.lastIndexOf(5));
    }

    @Test
    public void lastIndexOf_existingElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(0, list.lastIndexOf(1));
        Assert.assertEquals(1, list.lastIndexOf(2));
        Assert.assertEquals(2, list.lastIndexOf(3));
        Assert.assertEquals(3, list.lastIndexOf(4));
    }

    @Test
    public void lastIndexOf_existingElementRepeating() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Assert.assertEquals(4, list.lastIndexOf(1));
        Assert.assertEquals(5, list.lastIndexOf(2));
        Assert.assertEquals(6, list.lastIndexOf(3));
        Assert.assertEquals(7, list.lastIndexOf(4));
    }

    @Test
    public void removeIndex_removeFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(0);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(2, list.get(0).intValue());
        Assert.assertEquals(3, list.get(1).intValue());
        Assert.assertEquals(4, list.get(2).intValue());
    }

    @Test
    public void removeIndex_removeFromMiddle() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(2);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(4, list.get(2).intValue());
    }

    @Test
    public void removeIndex_removeLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.remove(3);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void subList_firstFewElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> subList = list.subList(0, 2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals(1, subList.get(0).intValue());
        Assert.assertEquals(2, subList.get(1).intValue());
    }

    @Test
    public void subList_lastFewElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> subList = list.subList(2, 4);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals(3, subList.get(0).intValue());
        Assert.assertEquals(4, subList.get(1).intValue());
    }

    @Test
    public void subList_entireList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> subList = list.subList(0, 4);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals(1, subList.get(0).intValue());
        Assert.assertEquals(2, subList.get(1).intValue());
        Assert.assertEquals(3, subList.get(2).intValue());
        Assert.assertEquals(4, subList.get(3).intValue());
    }

    @Test
    public void set_firstElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.set(0, 5);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(5, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
        Assert.assertEquals(4, list.get(3).intValue());
    }

    @Test
    public void set_middleElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.set(2, 5);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(5, list.get(2).intValue());
        Assert.assertEquals(4, list.get(3).intValue());
    }

    @Test
    public void set_lastElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.set(3, 5);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
        Assert.assertEquals(5, list.get(3).intValue());
    }

    @Test
    public void addIndex_beginning() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.add(0, 5);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(5, list.get(0).intValue());
        Assert.assertEquals(1, list.get(1).intValue());
        Assert.assertEquals(2, list.get(2).intValue());
        Assert.assertEquals(3, list.get(3).intValue());
        Assert.assertEquals(4, list.get(4).intValue());
    }

    @Test
    public void addIndex_middle() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.add(2, 5);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(5, list.get(2).intValue());
        Assert.assertEquals(3, list.get(3).intValue());
        Assert.assertEquals(4, list.get(4).intValue());
    }

    @Test
    public void addIndex_end() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.add(4, 5);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
        Assert.assertEquals(4, list.get(3).intValue());
        Assert.assertEquals(5, list.get(4).intValue());
    }

    @Test
    public void addAll_beginning() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(5);
        helpList.add(6);
        helpList.add(7);

        list.addAll(0, helpList);
        Assert.assertEquals(7, list.size());
        Assert.assertEquals(5, list.get(0).intValue());
        Assert.assertEquals(6, list.get(1).intValue());
        Assert.assertEquals(7, list.get(2).intValue());
        Assert.assertEquals(1, list.get(3).intValue());
        Assert.assertEquals(2, list.get(4).intValue());
        Assert.assertEquals(3, list.get(5).intValue());
        Assert.assertEquals(4, list.get(6).intValue());
    }

    @Test
    public void addAll_middle() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(5);
        helpList.add(6);
        helpList.add(7);

        list.addAll(2, helpList);
        Assert.assertEquals(7, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(5, list.get(2).intValue());
        Assert.assertEquals(6, list.get(3).intValue());
        Assert.assertEquals(7, list.get(4).intValue());
        Assert.assertEquals(3, list.get(5).intValue());
        Assert.assertEquals(4, list.get(6).intValue());
    }

    @Test
    public void addAll_end() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> helpList = new ArrayList<>();
        helpList.add(5);
        helpList.add(6);
        helpList.add(7);

        list.addAll(3, helpList);
        Assert.assertEquals(7, list.size());
        Assert.assertEquals(1, list.get(0).intValue());
        Assert.assertEquals(2, list.get(1).intValue());
        Assert.assertEquals(3, list.get(2).intValue());
        Assert.assertEquals(5, list.get(3).intValue());
        Assert.assertEquals(6, list.get(4).intValue());
        Assert.assertEquals(7, list.get(5).intValue());
        Assert.assertEquals(4, list.get(6).intValue());
    }

    @Test
    public void iterator_emptyCollection() {
        int counter = 0;

        for (Integer i : list) {
            counter++;
        }
        Assert.assertEquals(0, counter);
    }

    @Test
    public void iterator_collectionHasElements() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        int counter = 0;

        for (Integer i : list) {
            counter++;
        }
        Assert.assertEquals(4, counter);
    }
}
