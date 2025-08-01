package class175;

// 雅加达的摩天楼，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3645
// 测试链接 : https://uoj.ac/problem/111
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.BitSet;

public class Code05_Skyscraper1 {

	static class Node {
		int idx, jump, step;

		Node(int i, int j, int s) {
			idx = i;
			jump = j;
			step = s;
		}

	}

	public static int MAXN = 30001;
	public static int n, m;

	// 每个位置拥有的doge列表
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cnt;

	// bfs过程
	public static ArrayDeque<Node> que = new ArrayDeque<>();
	public static BitSet[] vis = new BitSet[MAXN];

	public static void add(int idx, int jump) {
		next[++cnt] = head[idx];
		to[cnt] = jump;
		head[idx] = cnt;
	}

	public static void trigger(int idx, int step) {
		for (int e = head[idx], nextJump; e > 0; e = next[e]) {
			nextJump = to[e];
			if (!vis[idx].get(nextJump)) {
				vis[idx].set(nextJump);
				que.addLast(new Node(idx, nextJump, step));
			}
		}
		head[idx] = 0;
	}

	public static void extend(int idx, int jump, int step) {
		trigger(idx, step);
		if (!vis[idx].get(jump)) {
			vis[idx].set(jump);
			que.addLast(new Node(idx, jump, step));
		}
	}

	public static int bfs(int s, int t) {
		if (s == t) {
			return 0;
		}
		for (int i = 0; i < MAXN; i++) {
			vis[i] = new BitSet();
		}
		trigger(s, 0);
		while (!que.isEmpty()) {
			Node cur = que.pollFirst();
			int idx = cur.idx;
			int jump = cur.jump;
			int step = cur.step;
			if (idx - jump == t || idx + jump == t) {
				return step + 1;
			}
			if (idx - jump >= 0) {
				extend(idx - jump, jump, step + 1);
			}
			if (idx + jump < n) {
				extend(idx + jump, jump, step + 1);
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		n = in.nextInt();
		m = in.nextInt();
		int s = in.nextInt();
		int sjump = in.nextInt();
		int t = in.nextInt();
		int tjump = in.nextInt();
		add(s, sjump);
		add(t, tjump);
		for (int i = 2, idx, jump; i < m; i++) {
			idx = in.nextInt();
			jump = in.nextInt();
			add(idx, jump);
		}
		out.println(bfs(s, t));
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