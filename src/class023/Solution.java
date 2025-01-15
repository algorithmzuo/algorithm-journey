package class023;
class Solution {
    public int[] sortArray(int[] nums) {
        return quickSort(nums,0,nums.length-1);
    }
    public int[] quickSort(int[] nums,int l,int r){
        if(l>=r) return nums;
        int x=nums[l+(int)Math.random()*(r-l+1)];
        int[] arr=partition(nums,l,r,x);
        quickSort(nums,l,arr[0]-1);
        quickSort(nums,arr[1]+1,r);
        return nums;
    }
    public int[] partition(int[] nums,int l,int r,int x){
        int i=l,xi=0;
        while(i<=r){
            if(nums[i]<x){
                swap(nums,i++,l++);
            }else if(nums[i]==x){
                i++;
            }else if(nums[i]>x){
                swap(nums,i,r--);
            }
        }
        int[] arr={l,r};
        return arr;
    }
    public void swap(int[] nums,int l,int r){
        int tmp=nums[l];
        nums[l]=nums[r];
        nums[r]=tmp;
    }
}
