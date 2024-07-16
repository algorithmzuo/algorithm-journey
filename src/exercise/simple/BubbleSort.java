package exercise.simple;

public class BubbleSort implements Sort {
    @Override
    public void sort(int[] arr) {
        // empty & only 1 element & null

        for (int end = arr.length - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }
}
