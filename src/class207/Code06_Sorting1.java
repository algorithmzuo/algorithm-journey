package class207;

// 排序，java版
// 一共n个元素，用大写字母 A、B、C... 表示，元素的值互不相同
// 给定m条大小关系，格式为 A<B，表示A的值小于B，大小关系具有传递性
// 按输入顺序逐条考虑这些关系，判断以下情况
// 如果首次在前k条关系后，已经能唯一确定完整排名，输出k和升序序列，然后结束
// 输出 Sorted sequence determined after {k} relations: {升序序列}.
// 如果首次在前k条关系后，发现了矛盾，那么停止判断，不再考虑后续的关系
// 输出 Inconsistency found after {k} relations.
// 如果考察完所有m条关系，仍然没有矛盾，但无法唯一确定完整排名
// 输出 Sorted sequence cannot be determined.
// 2 <= n <= 26    1 <= m <= 600
// 测试链接 : https://www.luogu.com.cn/problem/P1347
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

public class Code06_Sorting1 {

	public static int MAXN = 27;
	public static int MAXM = 601;
	public static int n, m;

	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static BitSet[] dp = new BitSet[MAXN];

	public static int ans;
	public static int kth;
	public static char[] order = new char[MAXN];

	public static void floyd() {
		for (int bridge = 1; bridge <= n; bridge++) {
			for (int i = 1; i <= n; i++) {
				if (dp[i].get(bridge)) {
					dp[i].or(dp[bridge]);
				}
			}
		}
	}

	// 顺序未确定返回0，顺序确定返回1，出现矛盾返回2
	public static int check() {
		for (int i = 1; i <= n; i++) {
			if (dp[i].get(i)) {
				return 2;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				if (!dp[i].get(j) && !dp[j].get(i)) {
					return 0;
				}
			}
		}
		return 1;
	}

	public static void compute() {
		ans = 0;
		for (int i = 1; i <= m; i++) {
			dp[a[i]].set(b[i]);
			floyd();
			ans = check();
			if (ans == 1 || ans == 2) {
				kth = i;
				break;
			}
		}
		if (ans == 1) {
			for (int i = 1; i <= n; i++) {
				order[n - dp[i].cardinality()] = (char) ('A' + i - 1);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			dp[i] = new BitSet(n + 1);
		}
		char s1, s2;
		for (int i = 1; i <= m; i++) {
			s1 = in.nextChar();
			in.nextChar();
			s2 = in.nextChar();
			a[i] = s1 - 'A' + 1;
			b[i] = s2 - 'A' + 1;
		}
		compute();
		if (ans == 0) {
			out.println("Sorted sequence cannot be determined.");
		} else if (ans == 1) {
			out.print("Sorted sequence determined after " + kth + " relations: ");
			for (int i = 1; i <= n; i++) {
				out.print(order[i]);
			}
			out.println(".");
		} else {
			out.println("Inconsistency found after " + kth + " relations.");
		}
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

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			return (char) c;
		}

	}

}