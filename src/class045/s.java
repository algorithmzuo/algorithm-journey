package class045;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class s {
    public int lower_bound(int[] nums, int t) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (nums[m] <= t) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return r;
    }

    public static void main(String[] args) {
        s s = new s();
        int nums[] = {2, 3, 4, 5, 5, 6}, t = 5;
        int ans = s.lower_bound(nums, t);
        System.out.println("ans : " + ans);
    }
}
