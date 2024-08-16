package class000;

import java.util.ArrayList;
import java.util.Arrays;

public class MinimumAverage {
    public static  double minimumAverage(int[] nums) {
        int averCnt = nums.length >> 1 ;
        ArrayList<Double> averages = new ArrayList<>();
        Arrays.sort(nums);
        double minAverage = Double.MAX_VALUE;

        int l = 0;
        int r = nums.length - 1;
        for (int i = 0; i < averCnt ; i++ , l++, r--) {
            double avr = (double) (nums[i]);
            minAverage = Math.min(minAverage, avr);
        }

        return minAverage;
    }


    public static void main(String[] args) {
        // DOUBLE
        int a = 1;
        int b = 2;
        double c = (double) (a+b /2) ;
        double d = (double) ((a+b) /2) ;
        System.out.println(c);
        System.out.println(d);
    }
}
