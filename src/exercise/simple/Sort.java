package exercise.simple;

public interface Sort {
    void sort(int[] arr);
    default void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
