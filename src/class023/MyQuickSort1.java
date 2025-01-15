package class023;
class MyQuickSort1 {
    public int[] sortArray(int[] nums) {
        return quickSort(nums,0,nums.length-1);
    }
    public int[] quickSort(int[] nums,int l,int r){
        if(l>=r) return nums;
        int x=nums[l+(int)Math.random()*(r-l+1)];
        int m=partition(nums,l,r,x);
        quickSort(nums,l,m-1);
        quickSort(nums,m+1,r);
        return nums;
    }
    public int partition(int[] nums,int l,int r,int x){
        int i=l,xi=0;
        while(i<=r){
            if(nums[i]<=x){
                swap(nums,i,l);
                if(nums[l]==x){
                    xi=l;
                }
                l++;
            }
            i++;
        }
        swap(nums,xi,l-1);
        return l-1;
    }
    public void swap(int[] nums,int l,int r){
        int tmp=nums[l];
        nums[l]=nums[r];
        nums[r]=tmp;
    }
}