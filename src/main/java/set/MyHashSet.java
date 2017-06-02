package set;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<T> implements Set<T> {

    private static final int DEFAULT_SIZE = 12;
    private static final double EXPAND_RATIO = 0.75;

    private int size;
    private Object[] array;

    public MyHashSet() {
        array = new Object[DEFAULT_SIZE];
    }

    public MyHashSet(int size) {
        array = new Object[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getValueIndex(o);
        MyNode<T> node = (MyNode<T>) array[index];
        while (node != null) {
            if (node.getValue().equals(o)) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>(toArray(), 0);
    }

    @Override
    public Object[] toArray() {
        Object[] tempArray = new Object[size];
        int currentIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyNode<T> node = (MyNode<T>) array[i];
                while(node != null) {
                    tempArray[currentIndex++] = node.getValue();
                    node = node.getNext();
                }
            }
        }
        return tempArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] tempArray = (T1[]) Array.newInstance(a.getClass(), size);
        int currentIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyNode<T> node = (MyNode<T>) array[i];
                while(node != null) {
                    tempArray[currentIndex++] = (T1) node.getValue();
                    node = node.getNext();
                }
            }
        }
        return tempArray;
    }

    @Override
    public boolean add(T t) {
        expandSetIfNeeded(size + 1);
        boolean isAdded = addElement(array, t);
        if (isAdded) {
            size++;
        }
        return isAdded;
    }


    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            int index = getValueIndex(o);
            MyNode<T> temp = (MyNode<T>) array[index];
            MyNode<T> last = null;
            while (temp != null) {
                if (temp.getValue().equals(o)) {
                    if (last == null) {
                        array[index] = null;
                    } else {
                        last.setNext(temp.getNext());
                    }
                    size--;
                    return true;
                }
                last = temp;
                temp = temp.getNext();
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        expandSetIfNeeded(size + c.size());
        boolean areAllAdded = true;
        for (Object o : c) {
            boolean isAdded = addElement(array, (T) o);
            if (isAdded) {
                size++;
            }
            areAllAdded = areAllAdded && isAdded;

        }
        return areAllAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Object[] tempArray = new Object[array.length];
        int tempArraySize = 0;
        for (Object o : c) {
            if (contains(o)) {
                addElement(tempArray, (T) o);
                tempArraySize++;
            }
        }
        array = tempArray;
        boolean isChanged = size != tempArraySize;
        size = tempArraySize;
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] tempArray = new Object[array.length];
        int currentIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyNode<T> node = (MyNode<T>) array[i];
                while(node != null) {
                    if (!c.contains(node.getValue())) {
                        addElement(tempArray, node.getValue());
                        currentIndex++;
                    }
                    node = node.getNext();
                }
            }
        }
        array = tempArray;
        boolean isChanged = size != currentIndex;
        size = currentIndex;
        return isChanged;
    }

    @Override
    public void clear() {
        array = new Object[array.length];
        size = 0;
    }

    private int getValueIndex(Object value) {
        return value.hashCode() % array.length;
    }

    private boolean addElement(Object[] array, T t) {
        int index = getValueIndex(t);
        MyNode<T> node = new MyNode<>(t);
        if (array[index] == null) {
            array[index] = node;
        } else {
            MyNode<T> temp = (MyNode<T>) array[index];
            MyNode<T> last = (MyNode<T>) array[index];
            while (temp != null) {
                if (temp.getValue().equals(t)) {
                    return false;
                }
                last = temp;
                temp = temp.getNext();
            }
            last.setNext(node);
        }
        return true;
    }

    private void expandSetIfNeeded(int newSize) {
        if ((newSize) / (double) array.length >= EXPAND_RATIO) {
            Object[] tempArray = new Object[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    MyNode<T> node = (MyNode<T>) array[i];
                    addElement(tempArray, node.getValue());
                }
            }
            array = tempArray;
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        private Object[] array;
        private int currentIndex;

        public MyIterator(Object[] array, int currentIndex) {
            this.array = array;
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            return (T) array[currentIndex++];
        }
    }

    private static class MyNode<T> {

        private T value;
        private MyNode<T> next;

        public MyNode(T value) {
            this.value = value;
        }

        private int getHashCode() {
            return value.hashCode();
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public MyNode<T> getNext() {
            return next;
        }

        public void setNext(MyNode<T> next) {
            this.next = next;
        }
    }

}
