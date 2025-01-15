package class024;

import java.util.Arrays;

class MyK {
    public int findKthLargest(int[] nums, int k) {
        return quickSort(nums,0,nums.length-1,nums.length-k);
    }
    public int quickSort(int[] nums,int l,int r,int i){
        int ans=0;
        if(i<0) return -1;
        int x=nums[l+(int)Math.random()*(r-l+1)];
        int[] arr=partition(nums,l,r,x);
        if(i<arr[0]){
            ans=quickSort(nums,l,arr[0]-1,i);
        }else if(i>arr[1]){
            ans=quickSort(nums,arr[1]+1,r,i);
        }else{
            ans=nums[i];
        }
        return ans;
    }
    public int[] partition(int[] nums,int l,int r,int x){
        int i=l;
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
        System.out.println("x="+x);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();
        return arr;
    }
    public void swap(int[] nums,int l,int r){
        int tmp=nums[l];
        nums[l]=nums[r];
        nums[r]=tmp;
    }

    public static void main(String[] args) {
        int[] nums={3,2,1,5,6,4};
        int k=2;
        MyK myK = new MyK();
        System.out.println(myK.findKthLargest(nums,k));
    }
}
