package class039;

import java.util.ArrayList;

// 含有嵌套的表达式求值
// 测试链接 : https://leetcode.cn/problems/basic-calculator-iii/
public class Code01_BasicCalculatorIII {

	public static int calculate(String str) {
		where = 0;
		return f(str.toCharArray(), 0);
	}

	public static int where;

	// s[i....]开始计算，遇到字符串终止 或者 遇到)停止
	// 返回 : 自己负责的这一段，计算的结果
	// 返回之间，更新全局变量where，为了上游函数知道从哪继续！
	public static int f(char[] s, int i) {
		int cur = 0;
		ArrayList<Integer> numbers = new ArrayList<>();
		ArrayList<Character> ops = new ArrayList<>();
		while (i < s.length && s[i] != ')') {
			if (s[i] >= '0' && s[i] <= '9') {
				cur = cur * 10 + s[i++] - '0';
			} else if (s[i] != '(') {
				// 遇到了运算符 + - * /
				push(numbers, ops, cur, s[i++]);
				cur = 0;
			} else {
				// i (.....)
				// 遇到了左括号！
				cur = f(s, i + 1);
				i = where + 1;
			}
		}
		push(numbers, ops, cur, '+');
		where = i;
		return compute(numbers, ops);
	}

	public static void push(ArrayList<Integer> numbers, ArrayList<Character> ops, int cur, char op) {
		int n = numbers.size();
		if (n == 0 || ops.get(n - 1) == '+' || ops.get(n - 1) == '-') {
			numbers.add(cur);
			ops.add(op);
		} else {
			int topNumber = numbers.get(n - 1);
			char topOp = ops.get(n - 1);
			if (topOp == '*') {
				numbers.set(n - 1, topNumber * cur);
			} else {
				numbers.set(n - 1, topNumber / cur);
			}
			ops.set(n - 1, op);
		}
	}

	public static int compute(ArrayList<Integer> numbers, ArrayList<Character> ops) {
		int n = numbers.size();
		int ans = numbers.get(0);
		for (int i = 1; i < n; i++) {
			ans += ops.get(i - 1) == '+' ? numbers.get(i) : -numbers.get(i);
		}
		return ans;
	}

}
