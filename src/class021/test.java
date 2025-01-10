package class021;

import java.io.*;

public class test{
    private static  int Max=100000;
    private static  int[] help=new int[Max];
    private static  int[] arr=new int[Max];
    private static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in=new StreamTokenizer(br);
        PrintWriter out=new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        n=(int)in.nval;
        for(int i=0;i<n;i++){
            in.nextToken();
            arr[i]=(int)in.nval;
        }
        MergeSort1(arr,0,n-1);
        for(int i=0;i<n-1;i++){
            out.print(arr[i]+" ");
        }
        out.print(arr[n-1]);
        out.flush();
        out.close();
        br.close();
    }
    public static void MergeSort(int[] arr,int l,int r){
        if(l==r){
            return;
        }
        int m=(l+r)/2;
        MergeSort(arr,l,m);
        MergeSort(arr,m+1,r);
        merge(arr,l,m,r);
    }
    public static void merge(int[] arr,int l, int m, int r) {
        //辅助数组指针
        int i = l;
        //左指针
        int a = l;
        //右指针
        int b = m + 1;
        //判断是否有一侧完成
        while (a <= m && b <= r) {
            help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
        }
        // 左侧指针、右侧指针，必有一个越界、另一个不越界
        while (a <= m) {
            help[i++] = arr[a++];
        }
        while (b <= r) {
            help[i++] = arr[b++];
        }
        for (i = l; i <= r; i++) {
            arr[i] = help[i];
        }
    }
    public static void MergeSort1(int[] arr,int l,int r){
        for (int le,ri,m,step = 1; step < r+1; step<<=1) {
            le=l;
            while(le<arr.length) {
                m = le + step - 1;
                if(m+1>r){
                    break;
                }
                ri=Math.min(le + (step << 1) - 1,r);
                merge(arr, le, m, ri);
                le = ri + 1;
            }
        }
    }
}