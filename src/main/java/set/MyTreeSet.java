package set;

import java.lang.reflect.Array;
import java.util.*;

public class MyTreeSet<T> implements Set<T> {

    private MyNode<T> root;
    private int size;
    private Comparator<T> comparator;

    public MyTreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
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
        MyNode<T> iterNode = root;
        while (iterNode != null) {
            int result = comparator.compare(iterNode.getValue(), (T) o);
            if (result < 0) {
                if (iterNode.getRight() == null) {
                    return false;
                } else {
                    iterNode = iterNode.getRight();
                }

            } else if (result > 0) {
                if (iterNode.getLeft() == null) {
                    return false;
                } else {
                    iterNode = iterNode.getLeft();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>(root);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (Object o : this) {
            array[index++] = o;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] array = (T1[]) Array.newInstance(a.getClass(), size);
        int index = 0;
        for (Object o : this) {
            array[index++] = (T1) o;
        }
        return array;
    }

    @Override
    public boolean add(T t) {
        MyNode<T> node = new MyNode<>(t);
        if (root == null) {
            root = node;
            root.setBlack();
            size++;
            return true;
        }
        node.setRed();
        boolean isAdded = addNode(node, root);
        if (isAdded) {
            size++;
        }
        return isAdded;
    }

    public boolean addNode(MyNode<T> nodeForAdding, MyNode<T> rootNode) {
        MyNode<T> iterNode = rootNode;
        boolean isAdded = false;
        while (iterNode != null) {
            int result = comparator.compare(iterNode.getValue(), nodeForAdding.getValue());
            if (result < 0) {
                if (iterNode.getRight() == null) {
                    iterNode.setRight(nodeForAdding);
                    nodeForAdding.setParent(iterNode);
                    addNodeRules(nodeForAdding);

                    iterNode = null;
                    isAdded = true;
                } else {
                    iterNode = iterNode.getRight();
                }
            } else if (result > 0) {
                if (iterNode.getLeft() == null) {
                    iterNode.setLeft(nodeForAdding);
                    nodeForAdding.setParent(iterNode);
                    addNodeRules(nodeForAdding);

                    iterNode = null;
                    isAdded = true;
                } else {
                    iterNode = iterNode.getLeft();
                }
            } else {
                iterNode = null;
            }
        }
        return isAdded;
    }

    private void addNodeRules(MyNode<T> node) {
        if (node.isUncleRed()) {
            uncleIsRedRule(node);
        } else if (node.isUncleBlack()) {
            if (node.isLeftLeftChild()) {
                uncleIsBlackLeftLeftCase(node);
            } else if (node.isLeftRightChild()) {
                uncleIsBlackLeftRightCase(node);
            } else if (node.isRightRightChild()) {
                uncleIsBlackRightRightCase(node);
            } else if (node.isRightLeftChild()) {
                uncleIsBlackRightLeftCase(node);
            }
        }
    }

    private void uncleIsRedRule(MyNode<T> node) {
            node.changeUncleColor(MyNode.BLACK);
            node.getParent().setColor(MyNode.BLACK);
            if (node.getParent().getParent() != root) {
                node.getParent().getParent().setColor(MyNode.RED);

                MyNode<T> iterNode = node.getParent().getParent();
                while (iterNode != null) {
                    if (iterNode.getParent() != null && iterNode.getParent().getParent() != null) {
                        iterNode = iterNode.getParent().getParent();
                        iterNode.setRed();
                    } else {
                        iterNode = null;
                    }
                }
            }
    }

    private void uncleIsBlackLeftLeftCase(MyNode<T> node) {
        MyNode<T> grandParent = node.getParent().getParent();
        MyNode<T> parent = node.getParent();

        parent.setParent(grandParent.getParent());
        if (grandParent.getParent() != null) {
            if (grandParent.amILeftChild()) {
                grandParent.getParent().setLeft(parent);
            } else if (grandParent.amIRightChild()) {
                grandParent.getParent().setRight(parent);
            }
        }
        boolean grandParentColor = grandParent.getColor();
        grandParent.setParent(parent);
        grandParent.setColor(parent.getColor());
        grandParent.setLeft(parent.getRight());
        parent.setRight(grandParent);
        parent.setColor(grandParentColor);
        if (parent.getParent() == null) {
            root = parent;
            root.setBlack();
        }
    }

    private void uncleIsBlackLeftRightCase(MyNode<T> node) {
        MyNode<T> grandParent = node.getParent().getParent();
        MyNode<T> parent = node.getParent();

        parent.setRight(node.getLeft());
        grandParent.setLeft(node);
        node.setParent(grandParent);
        node.setLeft(parent);
        parent.setParent(node);

        uncleIsBlackLeftLeftCase(parent);
    }

    private void uncleIsBlackRightRightCase(MyNode<T> node) {
        MyNode<T> grandParent = node.getParent().getParent();
        MyNode<T> parent = node.getParent();

        parent.setParent(grandParent.getParent());
        if (grandParent.getParent() != null) {
            if (grandParent.amILeftChild()) {
                grandParent.getParent().setLeft(parent);
            } else if (grandParent.amIRightChild()) {
                grandParent.getParent().setRight(parent);
            }
        }
        boolean grandParentColor = grandParent.getColor();
        grandParent.setParent(parent);
        grandParent.setColor(parent.getColor());
        grandParent.setRight(parent.getLeft());
        parent.setLeft(grandParent);
        parent.setColor(grandParentColor);
        if (parent.getParent() == null) {
            root = parent;
            root.setBlack();
        }
    }

    private void uncleIsBlackRightLeftCase(MyNode<T> node) {
        MyNode<T> grandParent = node.getParent().getParent();
        MyNode<T> parent = node.getParent();

        parent.setLeft(node.getRight());
        grandParent.setRight(node);
        node.setParent(grandParent);
        node.setRight(parent);
        parent.setParent(node);

        uncleIsBlackRightRightCase(parent);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean containsAll = true;
        for (Object o : c) {
            containsAll = containsAll && contains(o);
        }
        return containsAll;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean isAdded = true;
        for (Object o : c) {
            isAdded = add((T) o) && isAdded;
        }
        return isAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<T> elementsToAdd = new HashSet<>();
        for (Object o : c) {
            if (contains(o)) {
                elementsToAdd.add((T) o);
            }
        }

        boolean areAllRetained = size == elementsToAdd.size();
        clear();
        addAll(elementsToAdd);
        return areAllRetained;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<T> elementsToAdd = new HashSet<>();
        for (T t : this) {
            if (!c.contains(t)) {
                elementsToAdd.add(t);
            }
        }

        boolean areAllRemoved = size == elementsToAdd.size();
        clear();
        addAll(elementsToAdd);
        return areAllRemoved;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private static class MyNode<T> {

        static final boolean RED = true;
        static final boolean BLACK = false;

        private T value;
        private MyNode<T> parent;
        private MyNode<T> left;
        private MyNode<T> right;
        private boolean color;

        public MyNode(T value) {
            this.value = value;
        }

        public boolean amILeftChild() {
            return parent != null && value.equals(parent.getLeft().getValue());
        }

        public boolean amIRightChild() {
            return parent != null && value.equals(parent.getRight().getValue());
        }

        public boolean isUncleRed() {
            if (this.parent != null && parent.getParent() != null){
                MyNode<T> grandParent = parent.getParent();
                if ((grandParent.getLeft() != null && grandParent.getLeft().getLeft() != null && grandParent.getLeft().getLeft().getValue().equals(value)) ||
                        (grandParent.getLeft()!= null && grandParent.getLeft().getRight() != null && grandParent.getLeft().getRight().getValue().equals(value))) {
                    if (grandParent.getRight() != null) {
                        return grandParent.getRight().isRed();
                    }
                } else {
                    if (grandParent.getLeft() != null) {
                        return grandParent.getLeft().isRed();
                    }
                }
            }
            return false;
        }

        public void changeUncleColor(boolean color) {
            if (this.parent != null && parent.getParent() != null){
                MyNode<T> grandParent = parent.getParent();
                if ((grandParent.getLeft() != null && grandParent.getLeft().getLeft() != null && grandParent.getLeft().getLeft().getValue().equals(value)) ||
                        (grandParent.getLeft()!= null && grandParent.getLeft().getRight() != null && grandParent.getLeft().getRight().getValue().equals(value))) {
                    if (grandParent.getRight() != null) {
                        grandParent.getRight().setColor(color);
                    }
                } else {
                    if (grandParent.getLeft() != null) {
                        grandParent.getLeft().setColor(color);
                    }
                }
            }
        }

        public boolean isUncleBlack() {
            if (this.parent != null && parent.getParent() != null){
                MyNode<T> grandParent = parent.getParent();
                if ((grandParent.getLeft() != null && grandParent.getLeft().getLeft() != null && grandParent.getLeft().getLeft().getValue().equals(value)) ||
                        (grandParent.getLeft()!= null && grandParent.getLeft().getRight() != null && grandParent.getLeft().getRight().getValue().equals(value))) {
                    if (grandParent.getRight() != null) {
                        return grandParent.getRight().isBlack();
                    } else {
                        return true;
                    }
                } else {
                    if (grandParent.getLeft() != null) {
                        return grandParent.getLeft().isBlack();
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean isLeftLeftChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getLeft() != null && parent.getParent().getLeft().getLeft() != null &&
                        parent.getParent().getLeft().getLeft().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isLeftRightChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getLeft() != null && parent.getParent().getLeft().getRight() != null &&
                        parent.getParent().getLeft().getRight().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isRightRightChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getRight() != null && parent.getParent().getRight().getRight() != null &&
                        parent.getParent().getRight().getRight().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isRightLeftChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getRight() != null && parent.getParent().getRight().getLeft() != null &&
                        parent.getParent().getRight().getLeft().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public void setBlack() {
            color = BLACK;
        }

        public void setRed() {
            color = RED;
        }

        public boolean isRed() {
            return color == RED;
        }

        public boolean isBlack() {
            return color == BLACK;
        }


        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public MyNode<T> getParent() {
            return parent;
        }

        public void setParent(MyNode<T> parent) {
            this.parent = parent;
        }

        public MyNode<T> getLeft() {
            return left;
        }

        public void setLeft(MyNode<T> left) {
            this.left = left;
        }

        public MyNode<T> getRight() {
            return right;
        }

        public void setRight(MyNode<T> right) {
            this.right = right;
        }

        public boolean getColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        Stack<MyNode<T>> stack;

        public MyIterator(MyNode<T> node) {
            stack = new Stack<>();
            explore(node);
        }

        private void explore(MyNode<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            MyNode<T> node = stack.pop();

            if (node.getRight() != null) {
                explore(node.getRight());
            }
            return node.getValue();
        }
    }
}


