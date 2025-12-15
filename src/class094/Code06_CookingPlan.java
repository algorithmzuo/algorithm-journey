package class094;

// 烹调方案
// 课上没有讲这个题，本节课的题目5，砍树问题，现在验证已经很困难了
// 所以找到这个题作为平替，因为贪心的思路是非常类似的
//
// 一共有n个食材，一共有m秒可以加工食材，你只能串行加工食材
// 每个食材的初始美味度为a[i]，美味衰减速度b[i]，加工花费c[i]秒
// 第i个食材美味度 = a[i] - t * b[i]，t是加工食材的时刻
// 计算m秒内，你能获得的最大美味度总和
// 1 <= n <= 50
// 其他数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1417
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过
//
// 本题的分析过程
// 假设当前来到s时刻，食材x和食材y，必须选择一个先加工
// 选择1，先加工x，后加工y，获得食材x的美味时刻是s，y的时刻是s + c[x]
// 选择1的美味度 = a[x] - s * b[x] + a[y] - (s + c[x]) * b[y]
//             = a[x] - s * b[x] + a[y] - s * b[y] - c[x] * b[y]
//
// 选择2，先加工y，后加工x，获得食材y的美味时刻是s，x的时刻是s + c[y]
// 选择2的美味度 = a[y] - s * b[y] + a[x] - (s + c[y]) * b[x]
//             = a[y] - s * b[y] + a[x] - s * b[x] - c[y] * b[x]
//
// 选择1和选择2的公共部分 = a[x] + a[y] - s * b[x] - s * b[y]，记为common
// 选择1 = common - c[x] * b[y]
// 选择2 = common - c[y] * b[x]
// 也就是说，如果c[x] * b[y] <= c[y] * b[x]，先选食材x，否则先选食材y
// 于是排序策略搞定了，剩下就和题目5一样了，01背包的解法，并且可以空间压缩
// 代码如下

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code06_CookingPlan {

	static class Food {
		int a, b, c;

		public Food(int x, int y, int z) {
			a = x;
			b = y;
			c = z;
		}
	}

	static class FoodCmp implements Comparator<Food> {
		@Override
		public int compare(Food x, Food y) {
			if ((long) x.c * (long) y.b <= (long) y.c * (long) x.b) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public static int MAXN = 51;
	public static int MAXM = 100001;
	public static int n, m;
	public static Food[] food = new Food[MAXN];
	public static long[] dp = new long[MAXM];

	public static long compute() {
		Arrays.sort(food, 1, n + 1, new FoodCmp());
		dp[0] = 0;
		for (int i = 1; i <= m; i++) {
			dp[i] = -1;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = m, p = j - food[i].c; p >= 0; j--, p--) {
				if (dp[p] != -1) {
					dp[j] = Math.max(dp[j], dp[p] + food[i].a - (long) j * food[i].b);
				}
			}
		}
		long ans = 0;
		for (int i = 1; i <= m; i++) {
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		m = in.nextInt();
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			food[i] = new Food(0, 0, 0);
		}
		for (int i = 1; i <= n; i++) {
			food[i].a = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			food[i].b = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			food[i].c = in.nextInt();
		}
		long ans = compute();
		out.println(ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}
	}

}