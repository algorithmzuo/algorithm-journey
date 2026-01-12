package class188;

// 太鼓达人，java版
// 给定一个正数n，所有长度为n的二进制状态一共有(2^n)个
// 构造一个字符串，字符串可以循环使用，其中的连续子串包含所有二进制状态
// 求出字符串的最小长度值，并且给出字典序最小的方案
// 比如n=3，字符串最小长度值为8，字典序最小的方案为00010111
// 注意到 000、001、010、101、011、111、110、100 都已包含
// 注意到 最后两个二进制状态 是字符串循环使用构造出来的
// 1 <= n <= 11
// 本题可以推广到k进制，代码就是按照推广来实现的
// 测试链接 : https://www.luogu.com.cn/problem/P10950
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Taiko1 {

	public static int MAXN = 3001;
	public static int n, k, m;

	public static int[] cur = new int[MAXN];
	public static int[] path = new int[MAXN];
	public static int cntp;

	public static void prepare() {
		m = 1;
		for (int i = 1; i <= n - 1; i++) {
			m *= k;
		}
		for (int i = 0; i < m; i++) {
			cur[i] = 0;
		}
		cntp = 0;
	}

	public static void euler(int u, int e) {
		while (cur[u] < k) {
			int ne = cur[u]++;
			euler((u * k + ne) % m, ne);
		}
		path[++cntp] = e;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = 2;
		prepare();
		euler(0, 0);
		out.print((m * k) + " ");
		for (int i = 1; i <= n - 1; i++) {
			out.print("0");
		}
		for (int i = cntp - 1; i >= n; i--) {
			out.print(path[i]);
		}
		out.println();
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