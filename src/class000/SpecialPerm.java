package class000;

import java.util.ArrayList;
import java.util.Arrays;

public class SpecialPerm {

    public static int specialPerm(int[] nums) {
        return  f(nums, 0);
    }

    public static int f(int[] nums, int index){
       if(index == nums.length -1  ){
            return 1;
       }
        int ans = 0;
        for (int i = index; i < nums.length ; i++) {
            swap(nums, index, i);
            if(nums[index] % nums[index+1] == 0 || nums[index+1] % nums[index] == 0){
                ans += f(nums, index +1);
            }
            swap(nums, index, i);
        }

        return ans;
    }

    public static int isSpecialPerm(int[] nums){
        for (int i = 0; i < nums.length -1 ; i++) {
            if(nums[i] % nums[i+1] != 0 && nums[i+1] % nums[i] != 0){
                return  0;
            }
        }
        return 1;
    }
    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,6};
//        int[] nums = new int[]{235745376,19645448,157163584,471490752,117872688,589363440,294681720,147340860,442022580,73670430,12278405,110505645,773539515,257846505};
//        System.out.println(isSpecialPerm(nums));
//        swap(nums,0,1);
        System.out.println(specialPerm(nums));
        //System.out.println((int) Math.pow(10, 9) + 7);
    }
}
