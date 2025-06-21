package class173;

// 磁力块，java版
// 测试链接 : https://www.luogu.com.cn/problem/P10590
// java实现的逻辑一定是正确的，但是无法通过测试
// 因为这道题只考虑C++能通过的时间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code04_Magnet2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_Magnet1 {

	public static class Node {
		int x, y, m, p;
		long range;
		long dist;

		public Node(int x_, int y_, int m_, int p_, int range_) {
			x = x_;
			y = y_;
			m = m_;
			p = p_;
			range = range_;
		}
	}

	public static class Cmp1 implements Comparator<Node> {
		@Override
		public int compare(Node a, Node b) {
			return a.m - b.m;
		}
	}

	public static class Cmp2 implements Comparator<Node> {
		@Override
		public int compare(Node a, Node b) {
			return a.dist <= b.dist ? -1 : 1;
		}
	}

	public static Cmp1 cmp1 = new Cmp1();
	public static Cmp2 cmp2 = new Cmp2();

	public static int MAXN = 300001;
	public static int MAXB = 601;
	public static int n;
	public static Node[] arr = new Node[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	public static int[] maxm = new int[MAXB];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] que = new int[MAXN];

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		Arrays.sort(arr, 1, n + 1, cmp1);
		for (int i = 1; i <= bnum; i++) {
			maxm[i] = arr[br[i]].m;
			Arrays.sort(arr, bl[i], br[i] + 1, cmp2);
		}
	}

	public static int bfs() {
		int ans = 0;
		vis[0] = true;
		int l = 1, r = 1;
		que[r++] = 0;
		while (l < r) {
			int cur = que[l++];
			for (int i = 1; i <= bnum; i++) {
				if (maxm[i] > arr[cur].p) {
					for (int j = bl[i]; j <= br[i]; j++) {
						if (arr[j].dist <= arr[cur].range && arr[j].m <= arr[cur].p && !vis[j]) {
							vis[j] = true;
							que[r++] = j;
							ans++;
						}
					}
					break;
				} else {
					while (bl[i] <= br[i] && arr[bl[i]].dist <= arr[cur].range) {
						if (!vis[bl[i]]) {
							vis[bl[i]] = true;
							que[r++] = bl[i];
							ans++;
						}
						bl[i]++;
					}
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int x = in.nextInt();
		int y = in.nextInt();
		int m = 0;
		int p = in.nextInt();
		int range = in.nextInt();
		arr[0] = new Node(x, y, 0, p, range);
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			x = in.nextInt();
			y = in.nextInt();
			m = in.nextInt();
			p = in.nextInt();
			range = in.nextInt();
			arr[i] = new Node(x, y, m, p, range);
		}
		long xd, yd;
		for (int i = 0; i <= n; i++) {
			arr[i].range = arr[i].range * arr[i].range;
			xd = arr[0].x - arr[i].x;
			yd = arr[0].y - arr[i].y;
			arr[i].dist = xd * xd + yd * yd;
		}
		prepare();
		out.println(bfs());
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
