package class185;

// 开店，java版
// 一共n个人，每个人有年龄，给定n-1条路，每条路有距离，路把人连成一棵树
// 一共m条查询，格式 u l r : 查询年龄范围[l, r]的所有人，到第u号人的距离总和
// 1 <= n <= 1.5 * 10^5    1 <= m <= 2 * 10^5
// 0 <= 年龄值 <= 10^9      1 <= 距离值 <= 1000
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3241
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_OpenStore1 {

	public static int MAXN = 200001;
	public static int MAXK = 4000001;
	public static int n, m, A;
	public static int[] age = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static int[] dist = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] centfa = new int[MAXN];

	// cur列表(年龄，到当前重心距离)
	public static int[] curl = new int[MAXN];
	public static int[] curr = new int[MAXN];
	public static int[] curAge = new int[MAXK];
	public static long[] curSum = new long[MAXK];
	public static int cntc;

	// fa列表(年龄，到上级重心距离)
	public static int[] fal = new int[MAXN];
	public static int[] far = new int[MAXN];
	public static int[] faAge = new int[MAXK];
	public static long[] faSum = new long[MAXK];
	public static int cntf;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, a, b, e;
	public static int stacksize;

	public static void push(int u, int f, int a, int b, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = a;
		stack[stacksize][3] = b;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		a = stack[stacksize][2];
		b = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void dfs1(int u, int f, int dis) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		dist[u] = dis;
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != f) {
				dfs1(v, u, dis + w);
			}
		}
		for (int ei = head[u], v; ei > 0; ei = nxt[ei]) {
			v = to[ei];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = nxt[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static void dfs3(int cur, int father, int distance) {
		stacksize = 0;
		push(cur, father, distance, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				fa[u] = f;
				dep[u] = dep[f] + 1;
				dist[u] = a;
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, a, 0, e);
				int v = to[e];
				int w = weight[e];
				if (v != f) {
					push(v, u, a + w, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f) {
						siz[u] += siz[v];
						if (son[u] == 0 || siz[son[u]] < siz[v]) {
							son[u] = v;
						}
					}
				}
			}
		}
	}

	public static void dfs4(int cur, int tag) {
		stacksize = 0;
		push(cur, 0, 0, tag, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				top[u] = b;
				if (son[u] == 0) {
					continue;
				}
				push(u, 0, 0, b, -2);
				push(son[u], 0, 0, b, -1);
				continue;
			} else if (e == -2) {
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, 0, 0, b, e);
				int v = to[e];
				if (v != fa[u] && v != son[u]) {
					push(v, 0, 0, v, -1);
				}
			}
		}
	}

	public static int getLca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	public static int getDist(int x, int y) {
		return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
	}

	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	// 递归版，java会爆栈，C++可以通过
	// 来到了一片连通区，当前重心是rt，上级重心是centfa[rt]
	// 生成两个列表，距离到当前重心的列表，距离到上级重心的列表
	public static void getList1(int u, int fa, int sum, int rt) {
		curAge[++cntc] = age[u];
		curSum[cntc] = sum;
		if (centfa[rt] > 0) {
			faAge[++cntf] = age[u];
			faSum[cntf] = getDist(u, centfa[rt]);
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !vis[v]) {
				getList1(v, u, sum + w, rt);
			}
		}
	}

	// getList1的迭代版
	public static void getList2(int cur, int father, int psum, int cen) {
		stacksize = 0;
		push(cur, father, psum, cen, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				curAge[++cntc] = age[u];
				curSum[cntc] = a;
				if (centfa[b] > 0) {
					faAge[++cntf] = age[u];
					faSum[cntf] = getDist(u, centfa[b]);
				}
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, a, b, e);
				int v = to[e];
				int w = weight[e];
				if (v != f && !vis[v]) {
					push(v, u, a + w, b, -1);
				}
			}
		}
	}

	public static void centroidTree(int u, int fa) {
		centfa[u] = fa;
		vis[u] = true;
		curl[u] = cntc + 1;
		fal[u] = cntf + 1;
		// getList1(u, 0, 0, u);
		getList2(u, 0, 0, u);
		curr[u] = cntc;
		far[u] = cntf;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				centroidTree(getCentroid(v, u), u);
			}
		}
	}

	// 查询x在有序数组arr中的排名
	public static int kth(int[] arr, int l, int r, int x) {
		int ans = r + 1;
		while (l <= r) {
			int mid = (l + r) >> 1;
			if (arr[mid] >= x) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	// 每个对象两个属性(age, sum)，所有对象根据年龄排序
	// java自带的排序慢，手撸双指针快排，C++实现时可以用自带的排序
	public static void sort(int[] age, long[] sum, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = age[(l + r) >> 1], tmp1;
		long tmp2;
		while (i <= j) {
			while (age[i] < pivot) i++;
			while (age[j] > pivot) j--;
			if (i <= j) {
				tmp1 = age[i]; age[i] = age[j]; age[j] = tmp1;
				tmp2 = sum[i]; sum[i] = sum[j]; sum[j] = tmp2;
				i++; j--;
			}
		}
		sort(age, sum, l, j);
		sort(age, sum, i, r);
	}

	public static long nodeCnt, pathSum;

	// 下标范围[l...r]代表当前节点的列表，找到年龄范围[agel, ager]的人
	// 查到的人数设置给nodeCnt，查到的距离总和设置给pathSum
	public static void query(int[] age, long[] sum, int l, int r, int agel, int ager) {
		nodeCnt = pathSum = 0;
		if (l <= r) {
			int a = kth(age, l, r, agel);
			int b = kth(age, l, r, ager + 1) - 1;
			if (a <= b) {
				nodeCnt = b - a + 1;
				pathSum = sum[b] - (a == l ? 0 : sum[a - 1]);
			}
		}
	}

	public static long compute(int x, int agel, int ager) {
		query(curAge, curSum, curl[x], curr[x], agel, ager);
		long ans = pathSum;
		long cnt1, sum1, cnt2, sum2;
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			query(curAge, curSum, curl[fa], curr[fa], agel, ager);
			cnt1 = nodeCnt;
			sum1 = pathSum;
			query(faAge, faSum, fal[cur], far[cur], agel, ager);
			cnt2 = nodeCnt;
			sum2 = pathSum;
			ans += sum1;
			ans -= sum2;
			ans += (cnt1 - cnt2) * getDist(x, fa);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		A = in.nextInt();
		for (int i = 1; i <= n; i++) {
			age[i] = in.nextInt();
		}
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		// dfs1(1, 0, 0);
		// dfs2(1, 1);
		dfs3(1, 0, 0);
		dfs4(1, 1);
		centroidTree(getCentroid(1, 0), 0);
		for (int i = 1; i <= n; i++) {
			sort(curAge, curSum, curl[i], curr[i]);
			for (int j = curl[i] + 1; j <= curr[i]; j++) {
				curSum[j] += curSum[j - 1];
			}
			sort(faAge, faSum, fal[i], far[i]);
			for (int j = fal[i] + 1; j <= far[i]; j++) {
				faSum[j] += faSum[j - 1];
			}
		}
		long lastAns = 0;
		for (int i = 1, u, l, r; i <= m; i++) {
			u = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			l = (int) ((lastAns + l) % A);
			r = (int) ((lastAns + r) % A);
			if (l > r) {
				int tmp = l; l = r; r = tmp;
			}
			lastAns = compute(u, l, r);
			out.println(lastAns);
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
