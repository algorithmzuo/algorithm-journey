package class153;

// 书架(java版)
// 测试链接 : https://www.luogu.com.cn/problem/P2596
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code04_Bookcase1 {

	public static int MAXN = 80005;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] num = new int[MAXN];

	public static int[] pos = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int n, m;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void upRotate(int i) {
		int f = father[i], g = father[f], soni = lr(i), sonf = lr(f);
		if (soni == 1) {
			right[f] = left[i];
			if (right[f] != 0) {
				father[right[f]] = f;
			}
			left[i] = f;
		} else {
			left[f] = right[i];
			if (left[f] != 0) {
				father[left[f]] = f;
			}
			right[i] = f;
		}
		if (g != 0) {
			if (sonf == 1) {
				right[g] = i;
			} else {
				left[g] = i;
			}
		}
		father[f] = i;
		father[i] = g;
		up(f);
		up(i);
	}

	public static void splay(int i, int goal) {
		int f = father[i], g = father[f];
		while (f != goal) {
			if (g != goal) {
				if (lr(i) == lr(f)) {
					upRotate(f);
				} else {
					upRotate(i);
				}
			}
			upRotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	// 返回中序排名为rank的节点编号
	public static int find(int rank) {
		int i = head, ans = 0;
		while (i != 0) {
			if (size[left[i]] + 1 == rank) {
				ans = i;
				break;
			} else if (size[left[i]] >= rank) {
				i = left[i];
			} else {
				rank -= size[left[i]] + 1;
				i = right[i];
			}
		}
		splay(ans, 0);
		return ans;
	}

	public static void add(int s) {
		int i = head;
		while (right[i] != 0) {
			i = right[i];
		}
		num[++cnt] = s;
		pos[s] = cnt;
		size[cnt] = 1;
		father[cnt] = i;
		right[i] = cnt;
		splay(cnt, 0);
	}

	public static int ask(int s) {
		int i = pos[s];
		splay(i, 0);
		return size[left[i]];
	}

	public static int query(int s) {
		return num[find(s)];
	}

	// 中序排名为a的节点，移动到中序排名为b的位置
	// 注意a不会是1和n位置，b也如此
	// 因为1位置和n位置提前加入了预备值，永远不会修改
	public static void move(int a, int b) {
		int i = find(a);
		int l = find(a - 1);
		splay(l, 0);
		splay(i, l);
		right[l] = right[i];
		father[right[l]] = l;
		up(l);
		left[i] = right[i] = 0;
		int r = find(b - 1);
		int next = find(b);
		splay(r, 0);
		splay(next, r);
		right[r] = i;
		father[i] = r;
		right[i] = next;
		father[next] = i;
		up(i);
		up(r);
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		add(0);
		for (int i = 1; i <= n; i++) {
			add(io.nextInt());
		}
		add(n + 1);
		n = n + 2;
		String op;
		for (int i = 1, s, t, rank; i <= m; i++) {
			op = io.next();
			s = io.nextInt();
			rank = ask(s) + 1;
			if (op.equals("Top")) {
				move(rank, 2);
			} else if (op.equals("Bottom")) {
				move(rank, n - 1);
			} else if (op.equals("Insert")) {
				t = io.nextInt();
				move(rank, rank + t);
			} else if (op.equals("Ask")) {
				io.println(rank - 2);
			} else {
				io.println(query(s + 1));
			}
		}
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
