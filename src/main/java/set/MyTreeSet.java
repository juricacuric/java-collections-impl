package set;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

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
                if (iterNode.getLeft() == null) {
                    return false;
                } else {
                    iterNode = iterNode.getLeft();
                }
            } else if (result > 0) {
                if (iterNode.getRight() == null) {
                    return false;
                } else {
                    iterNode = iterNode.getRight();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (root == null) {
            root = new MyNode<>(t);
            root.setBlack();
            size++;
            return true;
        }
        MyNode<T> node = new MyNode<>(t);
        node.setRed();
        boolean isAdded = addNode(node);
        return isAdded;
    }

    public boolean addNode(MyNode<T> nodeForAdding) {
        MyNode<T> iterNode = root;
        boolean isAdded = false;
        while (iterNode != null) {
            int result = comparator.compare(iterNode.getValue(), nodeForAdding.getValue());
            if (result < 0) {
                if (iterNode.getLeft() == null) {
                    iterNode.setLeft(nodeForAdding);
                    nodeForAdding.setParent(iterNode);
                    addNodeRules(nodeForAdding);

                    iterNode = null;
                    isAdded = true;
                    size++;
                } else {
                    iterNode = iterNode.getLeft();
                }
            } else if (result > 0) {
                if (iterNode.getRight() == null) {
                    iterNode.setRight(nodeForAdding);
                    nodeForAdding.setParent(iterNode);
                    addNodeRules(nodeForAdding);

                    iterNode = null;
                    isAdded = true;
                    size++;
                } else {
                    iterNode = iterNode.getRight();
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
            isAdded = isAdded && add((T) o);
        }
        return isAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
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

        public boolean isUncleRed() {
            if (parent != null){
                if (parent.getParent() != null) {
                    if (parent.getLeft().getLeft().getValue().equals(value) ||
                            parent.getLeft().getRight().getValue().equals(value)) {
                        return parent.getRight().isRed();
                    } else {
                        return parent.getLeft().isRed();
                    }
                }
            }
            return false;
        }

        public void changeUncleColor(boolean color) {
            if (parent != null){
                if (parent.getParent() != null) {
                    if (parent.getLeft().getLeft().getValue().equals(value) ||
                            parent.getLeft().getRight().getValue().equals(value)) {
                        parent.getRight().setColor(color);
                    } else {
                        parent.getLeft().setColor(color);
                    }
                }
            }
        }

        public boolean isUncleBlack() {
            if (parent != null){
                if (parent.getParent() != null) {
                    if (parent.getLeft().getLeft().getValue().equals(value) ||
                            parent.getLeft().getRight().getValue().equals(value)) {
                        return parent.getRight().isBlack();
                    } else {
                        return parent.getLeft().isBlack();
                    }
                }
            }
            return false;
        }

        public boolean isLeftLeftChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getLeft().getLeft().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isLeftRightChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getLeft().getRight().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isRightRightChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getRight().getRight().getValue().equals(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isRightLeftChild() {
            if (parent != null && parent.getParent() != null) {
                if (parent.getParent().getRight().getLeft().getValue().equals(value)) {
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
}


