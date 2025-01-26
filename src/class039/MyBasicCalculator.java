package class039;
import java.util.List;
import java.util.ArrayList;
//测试链接：https://leetcode.cn/problems/basic-calculator-ii/
//todo这里有个巨坑，题目中有空字符串需要去掉。
public class MyBasicCalculator {
    public int where=0;
    public int calculate(String s) {
        if(s==null) return 0;
        where=0;
        return f(s.toCharArray(),0);
    }
    public int f(char[] arr,int i){
        int cur=0;
        List<Integer> nums=new ArrayList();
        List<Character> ops=new ArrayList();
        while(i<arr.length&&arr[i] !=')'){
            if(arr[i]==' ') {
                i++;
                continue;
            }
            if(arr[i]<='9'&&arr[i]>='0'){
                cur=cur*10+arr[i++]-'0';
            }else if(arr[i]!='('){
                push(nums,ops,cur,arr[i++]);
                cur=0;
            }else{
                cur=f(arr,i+1);
                i=where+1;
            }
        }
        push(nums,ops,cur,'+');
        where=i;
        return compute(nums,ops);
    }
    public void push(List<Integer> nums,List<Character> opss,int cur,char ops){
        int n=opss.size();
        if(n==0||opss.get(n-1)=='+'||opss.get(n-1)=='-'){
            nums.add(cur);
            opss.add(ops);
        }else{
            int topnum=nums.get(n-1);
            if(opss.get(n-1)=='*'){
                nums.set(n-1,topnum*cur);
            }else{
                nums.set(n-1,topnum/cur);
            }
            opss.set(n-1,ops);
        }
    }
    public int compute(List<Integer> nums,List<Character> opss){
        int ans=nums.get(0);
        for(int i=1;i<nums.size();i++){
            ans+=opss.get(i-1)=='+'?nums.get(i):-nums.get(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        MyBasicCalculator myBasicCalculator = new MyBasicCalculator();
        System.out.println(myBasicCalculator.calculate("3/2"));
    }
}
