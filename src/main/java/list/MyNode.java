package list;

class MyNode<T> {

    private T value;
    private MyNode<T> next;

    MyNode(T value) {
        this.value = value;
    }

    T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    MyNode<T> getNext() {
        return next;
    }

    void setNext(MyNode<T> next) {
        this.next = next;
    }
}
