import java.util.Iterator;


@SuppressWarnings("unchecked")
public class ArrayList<E> implements Iterable<E>, Queue<E>, Stack<E> {
    E[] array;
    int firstIndex;
    int size;

    public ArrayList() {
        array = (E[]) new Object[16];
        firstIndex = -1;
        size = 0;
    }

    private int capacity() {
        return array.length;
    }

    private void resize() {
        E[] newArr = (E[]) new Object[size * 2];
        for (int i = 0; i < size; i++) {
            newArr[i] = array[(firstIndex + i) % capacity()];
        }
        array = newArr;
        firstIndex = 0;
    }

    public int size() {
        return size;
    }

    public E poll() {
        return removeLast();
    }

    public void offer(E item) {
        addFirst(item);
    }

    public E peek() {
        return get(size - 1);
    }

    // return and remove the item on the top of the stack
    public E pop() {
        return removeLast();
    }

    // adds this item onto the top of the stack
    public void push(E item) {
        addLast(item);
    }

    public E get(int index) {
        return array[(firstIndex + index) % capacity()];
    }

    public void addFirst(E item) {
        if (size == capacity()) {
            resize();
        }

        firstIndex--;
        if (firstIndex < 0) {
            firstIndex = capacity() - 1;
        }

        array[firstIndex] = item;
        size++;
    }

    public E removeFirst() {
        E toReturn = array[firstIndex];

        array[firstIndex] = null;

        firstIndex++;
        if (firstIndex >= capacity()) {
            firstIndex = 0;
        }

        size--;
        return toReturn;
    }

    public void addLast(E item) {
        if (size == capacity()) {
            resize();
        }

        if (size == 0) {
            firstIndex = 0;
        }

        array[(firstIndex + size) % capacity()] = item;
        size++;
    }

    public E removeLast() {
        E toReturn = array[(firstIndex + size - 1) % capacity()];

        if (size == 0) {
            firstIndex = -1;
        }

        array[(firstIndex + size - 1) % capacity()] = null;

        size--;
        return toReturn;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int curIndex = 0;

            @Override
            public boolean hasNext() {
                return curIndex < size();
            }

            @Override
            public E next() {
                return get(curIndex++);
            }

        };
    }

    public boolean contains(Object o) {
        for (E item : this) {
            if (item.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public String toString(){
        if (size() == 0) return "[]";

        String out = "[" + get(0);
        for(int i = 1; i < size; i++){
            out += ", " + get(i);
        }

        return out + "]";
    }
}