package class207;

// 连通数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4306
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

public class Code03_Connectivity1 {

	public static int MAXN = 2001;
	public static int n;

	public static BitSet[] dp = new BitSet[MAXN];

	public static void floyd() {
		for (int bridge = 1; bridge <= n; bridge++) {
			for (int i = 1; i <= n; i++) {
				if (dp[i].get(bridge)) {
					dp[i].or(dp[bridge]);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			dp[i] = new BitSet(n + 1);
		}
		char s;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				s = in.nextChar();
				dp[i].set(j, s == '1');
			}
		}
		// 主动设置对角线
		for (int i = 1; i <= n; i++) {
			dp[i].set(i);
		}
		floyd();
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			ans += dp[i].cardinality();
		}
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

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			return (char) c;
		}

	}

}
