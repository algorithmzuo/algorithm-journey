package class207;

// 有向图与查询，java版
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc287_h
// 测试链接 : https://atcoder.jp/contests/abc287/tasks/abc287_h
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

public class Code07_DirectedGraphAndQuery1 {

	public static int MAXN = 2001;
	public static int MAXQ = 10001;
	public static int n, m, q;

	public static int[] s = new int[MAXQ];
	public static int[] t = new int[MAXQ];

	public static BitSet[] dp = new BitSet[MAXN];
	public static int[] ans = new int[MAXQ];

	public static void floyd() {
		for (int bridge = 1; bridge <= n; bridge++) {
			for (int i = 1; i <= n; i++) {
				if (dp[i].get(bridge)) {
					dp[i].or(dp[bridge]);
				}
			}
			for (int i = 1; i <= q; i++) {
				if (dp[s[i]].get(t[i])) {
					ans[i] = Math.min(ans[i], Math.max(bridge, Math.max(s[i], t[i])));
				}
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
		for (int i = 1, a, b; i <= m; i++) {
			a = in.nextInt();
			b = in.nextInt();
			dp[a].set(b);
		}
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			s[i] = in.nextInt();
			t[i] = in.nextInt();
		}
		int inf = n + 1;
		for (int i = 1; i <= q; i++) {
			ans[i] = inf;
		}
		floyd();
		for (int i = 1; i <= q; i++) {
			out.println(ans[i] == inf ? -1 : ans[i]);
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

	}

}