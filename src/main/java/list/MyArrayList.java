package list;

import java.lang.reflect.Array;
import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final int DEFAULT_SIZE = 12;

    private int size;
    private Object[] array;

    public MyArrayList() {
        array = new Object[DEFAULT_SIZE];
    }

    public MyArrayList(int size) {
        if (size < 1) {
            throw new IllegalArgumentException();
        }
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
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {

        return new MyIterator<>(0);
    }

    @Override
    public Object[] toArray() {
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] tempArray = (T1[]) Array.newInstance(a.getClass(), size);
        System.arraycopy(array, 0, tempArray, 0, size);
        return tempArray;
    }

    @Override
    public boolean add(T t) {
        expandArrayIfNeeded();
        array[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }
        if (array[size - 1].equals(o)) {
            array[--size] = null;
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                array[--size] = null;
                return true;
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
        resizeArrayForCollection(c);
        Object[] objects = c.toArray();
        System.arraycopy(objects, 0, array, size, objects.length);
        size += size + objects.length;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        resizeArrayForCollection(c);
        Object[] objects = c.toArray();
        System.arraycopy(array, index, array, index + objects.length, size - index);
        for (int i = 0; i < objects.length; i++) {
            array[i + index] = objects[i];
            size++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] tempArray = new Object[size];
        int currentIndex = 0;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                tempArray[currentIndex++] = array[i];
            }
        }
        array = tempArray;
        size = currentIndex;
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Object[] tempArray = new Object[size];
        int currentIndex = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                tempArray[currentIndex++] = array[i];
            }
        }
        array = tempArray;
        size = currentIndex;
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        checkIfIndexValid(index);
        array[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        checkIfIndexValid(index);
        expandArrayIfNeeded();
        if (size >= array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        if (index == 0) {
            System.arraycopy(array, 0, array, 1, size);
        } else {
            System.arraycopy(array, 0, array, 0, index - 1);
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIfIndexValid(index);
        T element = (T) array[index];
        if (index == 0) {
            System.arraycopy(array, 1, array, 0, size - 1);
        } else {
            System.arraycopy(array, 0, array, 0, index - 1);
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
        array[--size] = null;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkIfIndexValid(fromIndex);
        checkIfIndexValid(toIndex);
        List<T> subList = new MyArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add((T) array[i]);
        }
        return subList;
    }

    private void resizeArrayForCollection(Collection<? extends T> c) {
        if (size + c.size() >= array.length) {
            array = Arrays.copyOf(array, size + c.size() + 1);
        }
    }

    private void expandArrayIfNeeded() {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
    }

    private void checkIfIndexValid(int index) {
        if (index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        private int currentIndex;

        public MyIterator(int currentIndex) {
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
}
