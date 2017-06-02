package list;

import java.lang.reflect.Array;
import java.util.*;

public class MySingleLinkedList<T> implements List<T> {

    private int size;
    private MyNode<T> first, last;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        if (first == null || last == null) {
            return false;
        }
        if (first.getValue().equals(o) || last.getValue().equals(o)) {
            return true;
        }
        MyNode<T> temp = first;
        while (temp != null) {
            if (temp.getValue().equals(o)) {
                return true;
            }
            temp = temp.getNext();
        }

        return false;
    }

    public Iterator<T> iterator() {
        return new MyIterator<>(first);
    }

    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        Object[] array = new Object[size];
        MyNode<T> temp = first;
        int current = 0;
        while (temp != null) {
            array[current] = temp.getValue();
            current++;
            temp = temp.getNext();
        }
        return array;
    }

    public <T1> T1[] toArray(T1[] a) {
        T1[] array = (T1[]) Array.newInstance(a.getClass(), size);
        MyNode<T> temp = first;
        int current = 0;
        while (temp != null) {
            array[current] = (T1) temp.getValue();
            current++;
            temp = temp.getNext();
        }
        return array;
    }

    public boolean add(T t) {
        MyNode<T> addNode = new MyNode<>(t);
        if (first == null) {
            first = addNode;
            last = addNode;
        } else {
            last.setNext(addNode);
            last = addNode;
        }
        size++;
        return true;
    }

    public boolean remove(Object o) {
        if (first == null) {
            return false;
        }
        boolean elementRemoved = false;
        if (first.getValue().equals(o)) {
            first = first.getNext();
            if (first == null) {
                last = null;
            }
            size--;
            return true;
        }
        MyNode<T> temp = first;
        while (temp != null && temp.getNext() != null) {
            if (temp.getNext().getValue().equals(o)) {
                temp.setNext(temp.getNext().getNext());
                if (temp.getNext() == last) {
                    last = temp;
                }
                size--;
                elementRemoved = true;
            } else {
                temp = temp.getNext();
            }
        }

        return elementRemoved;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            boolean contains = contains(o);
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        if (index == 0) {
            MyNode<T> temp = first;
            MyNode<T> previous = null;
            int counter = 0;
            for (T o : c) {
                MyNode<T> node = new MyNode<>(o);
                if (counter == 0) {
                    first = node;
                    previous = node;
                } else {
                    previous.setNext(node);
                    previous = node;
                }
                counter++;
                size++;
            }
            previous.setNext(temp);
        } else {
            MyNode<T> previous = getNodeByIndex(index - 1);
            MyNode<T> next = previous.getNext();
            for (T o : c) {
                MyNode<T> node = new MyNode<>(o);
                previous.setNext(node);
                previous = node;
                size++;
            }
            previous.setNext(next);
        }
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        boolean anyElementRemoved = false;
        for (Object o : c) {
            boolean removed = remove(o);
            if (!anyElementRemoved) {
                anyElementRemoved = removed;
            }
        }
        return anyElementRemoved;
    }

    public boolean retainAll(Collection<?> c) {
        if (first == null || last == null) {
            return false;
        }
        MyNode<T> temp = first;
        while (temp != null) {
            if (!c.contains(temp.getValue())) {
                remove(temp.getValue());
            }
            temp = temp.getNext();
        }
        return true;
    }

    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    public T get(int index) {
        return getNodeByIndex(index).getValue();
    }

    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        MyNode<T> temp = first;
        int counter = 0;
        while (temp != null) {
            if (counter == index) {
                temp.setValue(element);
            }
            temp = temp.getNext();
            counter++;
        }
        return element;
    }

    public void add(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (first == null || index == size) {
            add(element);
            return;
        }
        MyNode<T> temp = first;
        MyNode<T> newNode = new MyNode<>(element);
        if (index == 0) {
            first = newNode;
            first.setNext(temp);
            size++;
            return;
        }
        int counter = 0;
        while (temp != null) {
            if (counter + 1 == index) {
                MyNode<T> next = temp.getNext();
                temp.setNext(newNode);
                newNode.setNext(next);
                if (next == null) {
                    last = newNode;
                }
                size++;
                return;
            }
            temp = temp.getNext();
            counter++;
        }
    }

    public T remove(int index) {
        if (first == null) {
            return null;
        }
        if (index == 0) {
            T removedElement = first.getValue();
            first = first.getNext();
            if (first == null) {
                last = null;
            }
            size--;
            return removedElement;
        }
        int counter = 0;
        MyNode<T> temp = first;
        T elemRemoved = null;
        while (temp != null && temp.getNext() != null) {
            if (counter + 1 == index) {
                elemRemoved = temp.getNext().getValue();
                temp.setNext(temp.getNext().getNext());
                if (temp.getNext() == last) {
                    last = temp;
                }
                size--;
            } else {
                temp = temp.getNext();
            }
            counter++;
        }

        return elemRemoved;
    }

    public int indexOf(Object o) {
        MyNode<T> temp = first;
        int current = 0;
        while (temp != null) {
            if (temp.getValue().equals(o)) {
                return current;
            }
            temp = temp.getNext();
            current++;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        MyNode<T> temp = first;
        int counter = 0;
        int lastIndexOf = -1;
        while (temp != null) {
            if (temp.getValue().equals(o)) {
                lastIndexOf = counter;
            }
            temp = temp.getNext();
            counter++;
        }
        return lastIndexOf;
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator<T>(0, first);
    }

    public ListIterator<T> listIterator(int index) {
        MyNode<T> node = getNodeByIndex(index);
        return new MyListIterator<T>(index, node);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex >= size || toIndex > size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        List<T> returnList = new MySingleLinkedList<>();
        MyNode<T> temp = first;
        int counter = 0;
        while (temp != null) {
            if (counter >= fromIndex && counter < toIndex) {
                returnList.add(temp.getValue());
            }
            temp = temp.getNext();
            counter++;
        }
        return returnList;
    }

    private MyNode<T> getNodeByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        MyNode<T> temp = first;
        int current = 0;
        while (temp != null) {
            if (current == index) {
                return temp;
            }
            temp = temp.getNext();
            current++;
        }
        return null;
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
            T nextElement = current.getValue();
            current = current.getNext();
            return nextElement;
        }
    }

    private class MyListIterator<T> implements ListIterator<T> {

        private int currentIndex;
        private MyNode<T> currentNode;

        private MyListIterator(int currentIndex, MyNode<T> currentNode) {
            this.currentIndex = currentIndex;
            this.currentNode = currentNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T nextElement = currentNode.getValue();
            currentNode = currentNode.getNext();
            currentIndex++;
            return nextElement;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return --currentIndex;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }

}
