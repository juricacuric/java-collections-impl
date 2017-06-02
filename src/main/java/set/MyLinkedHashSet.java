package set;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public class MyLinkedHashSet<T> implements Set<T> {

    private static final int DEFAULT_SIZE = 12;
    private static final double EXPAND_RATIO = 0.75;

    private int size;
    private Object[] array;
    private MyNode<T> first, last = null;

    public MyLinkedHashSet() {
        this.array = new Object[DEFAULT_SIZE];
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
        return new MyIterator<>(first);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int currentIndex = 0;
        for (T t : this) {
            array[currentIndex++] = t;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] array = (T1[]) Array.newInstance(a.getClass(), size);
        int currentIndex = 0;
        for (T t : this) {
            array[currentIndex++] = (T1) t;
        }
        return array;
    }

    @Override
    public boolean add(T t) {
        expandSetIfNeeded(size + 1);
        MyNode<T> node = new MyNode<>(t);
        boolean isAdded = addElement(array, node);
        if (isAdded) {
            updateFirstAndLastRef(node);
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
                        removeNode(temp);
                    } else {
                        last.setNext(temp.getNext());
                        removeNode(temp);
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
            MyNode<T> node = new MyNode<>((T) o);
            boolean isAdded = addElement(array, node);
            if (isAdded) {
                updateFirstAndLastRef(node);
                size++;
            }
            areAllAdded = areAllAdded && isAdded;

        }
        return areAllAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return createNewSetWithCondition(c::contains);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return createNewSetWithCondition(t -> !c.contains(t));
    }

    private boolean createNewSetWithCondition(Predicate<T> predicate) {
        Object[] tempArray = new Object[array.length];
        int tempSize = 0;
        MyNode<T> iterationNode = first;
        MyNode<T> tempFirst = null;
        MyNode<T> tempLast = null;
        while (iterationNode != null) {
            if (predicate.test(iterationNode.getValue())) {
                MyNode<T> nodeToInsert = new MyNode<>(iterationNode.getValue());
                addElement(tempArray, nodeToInsert);
                tempSize++;
                if (tempFirst == null) {
                    tempFirst = nodeToInsert;
                    tempLast = nodeToInsert;
                } else {
                    tempLast.setNext(nodeToInsert);
                    nodeToInsert.setPrevious(tempLast);
                    tempLast = nodeToInsert;
                }
            }

            iterationNode = iterationNode.getNext();
        }
        first = tempFirst;
        last = tempLast;
        array = tempArray;
        boolean isChanged = size != tempSize;
        size = tempSize;
        return isChanged;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_SIZE];
        size = 0;
    }

    private void updateFirstAndLastRef(MyNode<T> node) {
        if (first == null || last == null) {
            first = node;
            last = node;
        } else {
            last.setNext(node);
            node.setPrevious(last);
            last = node;
        }
    }

    private void removeNode(MyNode<T> node) {
        if (node.equals(first)) {
            first = first.getNext();
        }
        else if (node.equals(last)) {
            last = last.getPrevious();
        } else {
            node.getPrevious().setNext(node.getNext());
        }
    }

    private void expandSetIfNeeded(int newSize) {
        if ((newSize) / (double) array.length >= EXPAND_RATIO) {
            Object[] tempArray = new Object[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    addElement(tempArray, (MyNode<T>) array[i]);
                }
            }
            array = tempArray;
        }
    }

    private int getValueIndex(Object value) {
        return value.hashCode() % array.length;
    }

    private boolean addElement(Object[] array, MyNode<T> node) {
        int index = getValueIndex(node.getValue());
        if (array[index] == null) {
            array[index] = node;
        } else {
            MyNode<T> temp = (MyNode<T>) array[index];
            MyNode<T> last = (MyNode<T>) array[index];
            while (temp != null) {
                if (temp.getValue().equals(node.getValue())) {
                    return false;
                }
                last = temp;
                temp = temp.getNext();
            }
            last.setNext(node);
        }
        return true;
    }

    private static class MyNode<T> {

        private T value;
        private MyNode<T> next;
        private MyNode<T> previous;

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

        public MyNode<T> getPrevious() {
            return previous;
        }

        public void setPrevious(MyNode<T> previous) {
            this.previous = previous;
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        private MyNode<T> current;

        public MyIterator(MyNode<T> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            MyNode<T> next = current;
            current = next.getNext();
            return next.getValue();
        }
    }
}
