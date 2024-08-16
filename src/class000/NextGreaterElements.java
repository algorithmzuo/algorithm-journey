package class000;

import java.util.Arrays;

public class NextGreaterElements {

    public static int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        int index = 0;
        for (int i = 0; i < nums.length ; i++) {
            int ans = findGreaterElement(nums, nums[i], i);
            result[index++] = ans;
        }
        return result;
    }

    public static int findGreaterElement(int[] nums, int target, int index){
        for (int i = index + 1; i % nums.length!= index ; i++) {
            i = i < nums.length ? i : i % nums.length;
            if(nums[i] > target){
                return nums[i];
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{5, 4})));
    }
}
