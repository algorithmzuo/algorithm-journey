package class085;

// windy数，加强版
// 课上没有讲这个文件，这是windy数的加强版测试
// 需要改成long类型，除此之外和课上讲的完全一样
// 测试链接 : https://www.luogu.com.cn/problem/P13085
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class FollowUpWindy {

	public static int MAXLEN = 21;

	public static long[][][] dp = new long[MAXLEN][11][2];

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
			long a = (long) in.nval;
			in.nextToken();
			long b = (long) in.nval;
			out.println(compute(a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long compute(long a, long b) {
		return cnt(b) - cnt(a - 1);
	}

	public static long cnt(long num) {
		if (num == 0) {
			return 1;
		}
		int len = 1;
		long offset = 1;
		long tmp = num / 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		build(len);
		return f(num, offset, len, 10, 0);
	}

	public static long f(long num, long offset, int len, int pre, int free) {
		if (len == 0) {
			return 1;
		}
		if (dp[len][pre][free] != -1) {
			return dp[len][pre][free];
		}
		int cur = (int) (num / offset % 10);
		long ans = 0;
		if (free == 0) {
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
							ans += f(num, offset / 10, len - 1, cur, 0);
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
		dp[len][pre][free] = ans;
		return ans;
	}

}
