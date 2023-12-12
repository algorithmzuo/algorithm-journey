package class085;

// windy数
// 不含前导零且相邻两个数字之差至少为2的正整数被称为windy数
// windy想知道[a,b]范围上总共有多少个windy数
// 测试链接 : https://www.luogu.com.cn/problem/P2657
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_WindyNumber {

	public static int MAXLEN = 11;

	public static int[][][] dp = new int[MAXLEN][11][2];

	public static void build(int len) {
		for (int i = 0; i <= len; i++) {
			for (int j = 0; j <= 10; j++) {
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int a = (int) in.nval;
			in.nextToken();
			int b = (int) in.nval;
			out.println(compute(a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute(int low, int high) {
		return cnt(high) - cnt(low - 1);
	}

	public static int cnt(int num) {
		if (num == 0) {
			return 0;
		}
		int len = 1;
		int offset = 1;
		int tmp = num / 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		build(len);
		return f(num, offset, len, 10, 0);
	}

	public static int f(int num, int offset, int len, int pre, int less) {
		if (len == 0) {
			return pre == 10 ? 0 : 1;
		}
		if (dp[len][pre][less] != -1) {
			return dp[len][pre][less];
		}
		int cur = num / offset % 10;
		int ans = 0;
		if (less == 0) {
			if (pre == 10) {
				ans += f(num, offset / 10, len - 1, 10, 1);
				for (int i = 1; i < cur; i++) {
					ans += f(num, offset / 10, len - 1, i, 1);
				}
				ans += f(num, offset / 10, len - 1, cur, 0);
			} else {
				for (int i = 0; i <= 9; i++) {
					if (i <= pre - 2 || i >= pre + 2) {
						if (i < cur) {
							ans += f(num, offset / 10, len - 1, i, 1);
						} else if (i == cur) {
							ans += f(num, offset / 10, len - 1, i, 0);
						}
					}
				}
			}
		} else {
			if (pre == 10) {
				ans += f(num, offset / 10, len - 1, 10, 1);
				for (int i = 1; i <= 9; i++) {
					ans += f(num, offset / 10, len - 1, i, 1);
				}
			} else {
				for (int i = 0; i <= 9; i++) {
					if (i <= pre - 2 || i >= pre + 2) {
						ans += f(num, offset / 10, len - 1, i, 1);
					}
				}
			}
		}
		dp[len][pre][less] = ans;
		return ans;
	}

}
