package class055;

import java.util.Arrays;

class code03test {
    public static int max=1000001;
    public static  int[] dp=new int[max];
    public static int h,t;
    public static int[] task;
    public static int[] worker;
    public static int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        int c=tasks.length;
        int n=workers.length;
        Arrays.sort(tasks);
        Arrays.sort(workers);
        task=tasks;
        worker=workers;
        int ans=0;
        for(int l=0,m,r=Math.min(c,n);l<=r;){
            m=(l+r)/2;
            if(f(0,m-1,n-m,n-1,pills,strength)){
                ans=m;
                l=m+1;
            }else{
                r=m-1;
            }
        }
        return ans;
    }
    public static boolean f(int tl,int tr,int wl,int wr,int pills,int strength){
        //todo 每次都重置
        h=t=0;
        for(int i=wl,j=tl;i<=wr;i++){
            for(;j<=tr&&worker[i]>=task[j];j++){
                dp[t++]=j;
            }
            if(h<t&&worker[i]>=task[dp[h]]){
                h++;
            }else{
                for(;j<=tr&&worker[i]+strength>=task[j];j++){
                    dp[t++]=j;
                }
                if(h<t){
                    t--;
                    pills--;
                    //todo 哭了，浪费了两小时发现每次遍历没有重置队列，把这个注释掉，结果都是正确的，？？
                    if(pills<0) return false;
                }else{
                    return false;
                }
            }
        }
        return pills>=0;
    }

    public static void main(String[] args) {
        int[] tasks ={5,9,8,5,9};
        int[] workers = {1,6,4,2,6};
        int pills=1;
        int strength=5;
        System.out.println(maxTaskAssign(tasks, workers, pills, strength));
    }
}