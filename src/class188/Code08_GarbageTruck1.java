package class188;

// 垃圾车，java版
// 一共有n个点，m条无向边，所有点不保证连通
// 每条边的属性为 u v s t : u和v是端点，s是初始状态，t是最终状态
// 状态的数值只有0和1两种，当一辆车通过一条无向边，那么状态会翻转
// 所有的边都要达成最终状态，所以需要若干辆车来完成这个目标
// 一辆车的路线中，可以指定一个起点，最终回到起点，别的点不能重复出现
// 如果存在方案，提供任何一种方案即可，首先打印需要几辆车
// 然后对每辆车，先打印通过的边数，再打印依次到达了哪些点
// 如果不存在方案，打印"NIE"
// 1 <= n <= 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3520
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_GarbageTruck1 {

	public static int MAXN = 100001;
	public static int MAXM = 2000001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int[] eid = new int[MAXM];
	public static int cntg;

	public static int[] deg = new int[MAXN];
	public static int[] cur = new int[MAXN];
	public static boolean[] enter = new boolean[MAXN];
	public static boolean[] visNode = new boolean[MAXN];
	public static boolean[] visEdge = new boolean[MAXM];

	public static int[] path = new int[MAXM];
	public static int cntp;

	public static int[] arr = new int[MAXM];
	public static int idx;

	public static int[] ansl = new int[MAXM];
	public static int[] ansr = new int[MAXM];
	public static int cnta;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[] stack = new int[MAXM];
	public static int u;
	public static int stacksize;

	public static void push(int u) {
		stack[stacksize++] = u;
	}

	public static void pop() {
		u = stack[--stacksize];
	}

	public static void addEdge(int u, int v, int id) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		eid[cntg] = id;
		head[u] = cntg;
	}

	public static void euler1(int u) {
		visNode[u] = true;
		for (int e = cur[u]; e > 0; e = cur[u]) {
			cur[u] = nxt[e];
			if (!visEdge[eid[e]]) {
				visEdge[eid[e]] = true;
				euler1(to[e]);
			}
		}
		if (!enter[u]) {
			enter[u] = true;
			path[++cntp] = u;
		} else {
			cnta++;
			arr[++idx] = u;
			ansl[cnta] = ansr[cnta] = idx;
			while (cntp > 0 && path[cntp] != u) {
				enter[path[cntp]] = false;
				arr[++idx] = path[cntp--];
				ansr[cnta] = idx;
			}
			arr[++idx] = u;
			ansr[cnta] = idx;
		}
	}

	public static void euler2(int node) {
		stacksize = 0;
		push(node);
		while (stacksize > 0) {
			pop();
			visNode[u] = true;
			int e = cur[u];
			if (e != 0) {
				cur[u] = nxt[e];
				if (!visEdge[eid[e]]) {
					visEdge[eid[e]] = true;
					push(u);
					push(to[e]);
				} else {
					push(u);
				}
			} else {
				if (!enter[u]) {
					enter[u] = true;
					path[++cntp] = u;
				} else {
					cnta++;
					arr[++idx] = u;
					ansl[cnta] = ansr[cnta] = idx;
					while (cntp > 0 && path[cntp] != u) {
						enter[path[cntp]] = false;
						arr[++idx] = path[cntp--];
						ansr[cnta] = idx;
					}
					arr[++idx] = u;
					ansr[cnta] = idx;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, s, t; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			s = in.nextInt();
			t = in.nextInt();
			if (s != t) {
				deg[u]++;
				deg[v]++;
				addEdge(u, v, i);
				addEdge(v, u, i);
			}
		}
		for (int i = 1; i <= n; i++) {
			cur[i] = head[i];
		}
		boolean check = true;
		for (int i = 1; i <= n; i++) {
			if ((deg[i] & 1) == 1) {
				check = false;
				break;
			}
		}
		if (!check) {
			out.println("NIE");
		} else {
			for (int i = 1; i <= n; i++) {
				if (!visNode[i]) {
					// euler1(i);
					euler2(i);
				}
			}
			out.println(cnta);
			for (int i = 1; i <= cnta; i++) {
				out.print((ansr[i] - ansl[i]) + " ");
				for (int j = ansl[i]; j <= ansr[i]; j++) {
					out.print(arr[j] + " ");
				}
				out.println();
			}
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
