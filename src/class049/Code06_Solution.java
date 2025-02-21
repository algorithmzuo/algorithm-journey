package class049;

import java.util.Arrays;

//todo 搁置了,只过了一半的测试，测试过大，调试过不去。能力有限回头补
class Code06_Solution {
    public static int subarraysWithKDistinct(int[] nums, int k) {
        int collect=0;
        int ans=0;
        int[] cnt=new int[20001];
        for(int l=0,r=0;r<nums.length;r++){
            if(cnt[nums[r]]++==0){
                collect++;
            }
            if(collect==k){
                System.out.print("正常获取：");
                for (int i = l; i <r; i++) {
                    System.out.print(nums[i]+",");
                }
                System.out.println(nums[r]);
                ans+=1;
                int q=l;
                int[] tmp = Arrays.copyOf(cnt, nums.length);
                while(--tmp[nums[q]]>=1){
                    if(q<r&&r-q+1>=k) {
                        System.out.print("特殊获取：");
                        for (int i = q; i < r && r - i + 1 >= k; i++) {
                            System.out.print(nums[i] + ",");
                        }
                            System.out.println(nums[r]);
                            ans++;
                    }
                    q++;
                }
            }
            while(collect>k){
                if(cnt[nums[l++]]--==1){
                    collect--;
                }
                if(collect==k){
                    System.out.print("缩减窗口获取：");
                    for (int i = l; i <r; i++) {
                        System.out.print(nums[i]+",");
                    }
                    System.out.println(nums[r]);
                    ans+=1;
                }
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(subarraysWithKDistinct(new int[]{2,1,1,1,2},1));
    }
}