package gb.danila.heapsort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class HeapSorter<E extends Comparable<E>> {
    private final List<E> items;
    private final Comparator<E> comparator;
    private int lastIndex;

    public HeapSorter(List<E> items, boolean isReversed) {
        this.items = items;
        if(isReversed)
            this.comparator = Comparator.reverseOrder();
        else
            this.comparator = Comparable::compareTo;
        initial();
    }

    public HeapSorter(List<E> items, Comparator<E> comparator) {
        this.items = items;
        this.comparator = comparator;
        initial();
    }

    public HeapSorter(List<E> items) {
        this(items, false);
    }

    private void initial() {
        lastIndex = items.size() - 1;

        int lastIndexOfItemWithParent = (lastIndex - 1)/ 2;
        for (int i = lastIndexOfItemWithParent; i >= 0; i--) {
            heapify(i);
        }
        for (int i = 1; i <= lastIndexOfItemWithParent; i++) {
            heapify(i);
        }

        swap(0, lastIndex);
        lastIndex--;
    }

    public void sort(){
        while(lastIndex > 0){
            sortIter();
        }
    }

    public void sortIter(){
        int nextIndex = 0;
        int lastIndexOfItemWithParent = lastIndex / 2;
        while(nextIndex < lastIndexOfItemWithParent){
            nextIndex = heapify(nextIndex);
        }
        swap(0, lastIndex);
        lastIndex--;
    }


    private int heapify(int parentIndex){
        int child1 = (parentIndex + 1) * 2 - 1;
        int child2 = child1 + 1;

        int maxChildIndex = -1;
        if(child1 > lastIndex)
            return -1;

        else if (child2 > lastIndex) {
            maxChildIndex = child1;
        }else
            maxChildIndex = greater(child1, child2) ? child1: child2;

        if(maxChildIndex != -1 && greater(maxChildIndex, parentIndex))
            swap(parentIndex, maxChildIndex);

        return maxChildIndex;
    }

    private void swap(int i, int j) {
        E temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }

    private boolean greater(int child1, int child2) {
        return this.comparator.compare(items.get(child1), items.get(child2)) > 0;
    }

    private static  <E extends Comparable<E>> void sortList(List<E> list, boolean isReverse){
        HeapSorter<E> heap = new HeapSorter<>(list, isReverse);
        heap.sort();
    }

    public static void main(String[] args) {
        List<Integer> numbers = generateList(30);
        System.out.println(numbers);
        sortList(numbers, false);
        for(int number: numbers)
            System.out.println(number);
    }

    public static List<Integer> generateList(int length){
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            numbers.add(random.nextInt(100));
        }

        return numbers;
    }
}