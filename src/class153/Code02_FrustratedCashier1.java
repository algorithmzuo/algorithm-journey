package class153;

// 郁闷的出纳员(java版)
// 最低薪水为limit，一旦员工薪水低于limit，员工会离职，实现如下四种操作
// I x : 新来员工初始薪水是x，如果x低于limit，该员工不会入职当然也不算离职
// A x : 所有员工的薪水都加上x
// S x : 所有员工的薪水都减去x，一旦有员工低于limit那么就会离职
// F x : 查询第x多的工资，如果x大于当前员工数量，打印-1
// 所有操作完成后，打印有多少员工在操作期间离开了公司
// 测试链接 : https://www.luogu.com.cn/problem/P1486
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_FrustratedCashier1 {

	public static int MAXN = 300001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int limit;

	public static int change = 0;

	public static int enter = 0;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void rotate(int i) {
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
					rotate(f);
				} else {
					rotate(i);
				}
			}
			rotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	public static void add(int num) {
		key[++cnt] = num;
		size[cnt] = 1;
		if (head == 0) {
			head = cnt;
		} else {
			int f = 0, i = head, son = 0;
			while (i != 0) {
				f = i;
				if (key[i] <= num) {
					son = 1;
					i = right[i];
				} else {
					son = 0;
					i = left[i];
				}
			}
			if (son == 1) {
				right[f] = cnt;
			} else {
				left[f] = cnt;
			}
			father[cnt] = f;
			splay(cnt, 0);
		}
	}

	public static int index(int x) {
		int i = head, last = head;
		while (i != 0) {
			last = i;
			if (size[left[i]] >= x) {
				i = left[i];
			} else if (size[left[i]] + 1 < x) {
				x -= size[left[i]] + 1;
				i = right[i];
			} else {
				i = 0;
			}
		}
		splay(last, 0);
		return key[last];
	}

	public static void departure() {
		int num = limit - change - 1;
		int i = head, ans = 0;
		while (i != 0) {
			if (key[i] > num) {
				ans = i;
				i = left[i];
			} else {
				i = right[i];
			}
		}
		if (ans == 0) {
			head = 0;
		} else {
			splay(ans, 0);
			left[head] = 0;
			up(head);
		}
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		int n = io.nextInt();
		limit = io.nextInt();
		String op;
		int x;
		for (int i = 1; i <= n; i++) {
			op = io.next();
			x = io.nextInt();
			if (op.equals("I")) {
				if (x >= limit) {
					enter++;
					add(x - change);
				}
			} else if (op.equals("A")) {
				change += x;
			} else if (op.equals("S")) {
				change -= x;
				departure();
			} else {
				if (x > size[head]) {
					io.println(-1);
				} else {
					io.println(index(size[head] - x + 1) + change);
				}
			}
		}
		io.println(enter - size[head]);
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
