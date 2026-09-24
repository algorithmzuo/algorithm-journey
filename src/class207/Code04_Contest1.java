package class207;

// 确定能力，java版
// 一共n头奶牛，编号为1~n，每头奶牛的能力互不相同
// 给定m条已知关系，格式 a b，表示a的能力强于b
// 能力关系具有传递性，如果a强于b，b强于c，那么a强于c
// 题目保证没有矛盾，根据已知关系，希望确定奶牛的排名
// 计算有多少头奶牛的具体名次已经能够确定，打印这个数量
// 1 <= n <= 100
// 1 <= m <= 4500
// 测试链接 : https://www.luogu.com.cn/problem/P2419
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

public class Code04_Contest1 {

	public static int MAXN = 101;
	public static int n, m;

	public static BitSet[] dp = new BitSet[MAXN];
	public static int[] cnt = new int[MAXN];

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
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			dp[i] = new BitSet(n + 1);
		}
		for (int i = 1, a, b; i <= m; i++) {
			a = in.nextInt();
			b = in.nextInt();
			dp[a].set(b);
		}
		floyd();
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				if (dp[i].get(j) || dp[j].get(i)) {
					cnt[i]++;
					cnt[j]++;
				}
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (cnt[i] == n - 1) {
				ans++;
			}
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

	}

}
