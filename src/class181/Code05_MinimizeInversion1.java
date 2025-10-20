package class181;

// 最小化逆序对，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3521
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code05_MinimizeInversion2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_MinimizeInversion1 {

	public static int MAXT = 5000001;
	public static int n;

	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int cntt;

	public static long ans, u, v;

	public static void up(int i) {
		siz[i] = siz[ls[i]] + siz[rs[i]];
	}

	public static int build(int jobi, int l, int r) {
		int rt = ++cntt;
		if (l == r) {
			siz[rt]++;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = build(jobi, l, mid);
			} else {
				rs[rt] = build(jobi, mid + 1, r);
			}
			up(rt);
		}
		return rt;
	}

	public static int merge(int l, int r, int t1, int t2) {
		if (t1 == 0 || t2 == 0) {
			return t1 + t2;
		}
		if (l == r) {
			siz[t1] += siz[t2];
		} else {
			u += (long) siz[rs[t1]] * siz[ls[t2]];
			v += (long) siz[ls[t1]] * siz[rs[t2]];
			int mid = (l + r) >> 1;
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	public static int dfs() throws IOException {
		int rt;
		int val = in.nextInt();
		if (val == 0) {
			int left = dfs();
			int right = dfs();
			u = v = 0;
			rt = merge(1, n, left, right);
			ans += Math.min(u, v);
		} else {
			rt = build(val, 1, n);
		}
		return rt;
	}

	public static FastReader in = new FastReader(System.in);

	public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		n = in.nextInt();
		dfs();
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
