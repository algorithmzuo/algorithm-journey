package class039;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

// 含有嵌套的表达式求值
// 测试链接 : https://leetcode.cn/problems/basic-calculator-iii/
public class Code01_myBasicCalculatorIII {
	public static int where;
	public static int calculate(String str) {
		if(!str.isEmpty()){
			return f(str.toCharArray(), 0);
		}
		return 0;
	}
	// 用于计算括号内的表达式
	// s[i....]开始计算，遇到字符串终止 或者 遇到)停止
	// 返回 : 自己负责的这一段，计算的结果
	// 返回之间，更新全局变量where，为了上游函数知道从哪继续！

	private static int  f(char[] c, int i) {
		List<Integer> nums = new ArrayList<>();
		List<Character> ops = new ArrayList<>();
		int cur = 0;
		while(i != c.length && c[i] != ')'){
			if(c[i]>='0' && c[i]<='9'){
				cur =  cur * 10 + c[i++] - '0';
			}else if(c[i] == '('){
				cur = f(c,i+1);
				i = where + 1;
			}else{
				push(nums, ops, c[i++],cur);
				cur= 0;
			}

		}
		where = i;
		// 把最后一个数字放进去
		push(nums, ops, '+', cur);
		return compute(nums,ops);
	}

	private static int compute(List<Integer> nums, List<Character> ops) {
		int ans = nums.get(0);
		for (int i = 1; i < nums.size(); i++) {
			ans+=  ops.get(i-1) == '+' ? nums.get(i) : -nums.get(i);
		}
		return ans;
	}

	private static void push(List<Integer> nums, List<Character> ops, char op, int cur) {
		int n = nums.size();
		if(n == 0 || ops.get(n-1) == '+' || ops.get(n-1) == '-'){
			nums.add(cur);
			ops.add(op);
		} else {
			int topVal = nums.get(n-1);
			char topOp = ops.get(n-1);
			if(topOp == '*'){
				topVal *= cur;
			}else{
				topVal /= cur;
			}
			nums.set(n-1, topVal);
			ops.set(n-1,op);
		}
	}


	public static void main(String[] args) {
		String str = "(1+3)/2*(2+1)";
		System.out.println(calculate(str));

	}

}
