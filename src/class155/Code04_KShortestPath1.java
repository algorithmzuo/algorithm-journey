package class155;

// k短路问题的最优解，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2483
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class Code04_KShortestPath1 {

	public static int MAXN = 50001;
	public static int MAXM = 200001;
	public static int MAXT = MAXN * 20;
	public static int MAXH = 4200001;

	public static int n, m;
	public static double money;

	public static int[] headg = new int[MAXN];
	public static int[] tog = new int[MAXM];
	public static int[] nextg = new int[MAXM];
	public static double[] weightg = new double[MAXM];
	public static int cntg = 0;

	public static int[] headr = new int[MAXN];
	public static int[] tor = new int[MAXM];
	public static int[] nextr = new int[MAXM];
	public static double[] weightr = new double[MAXM];
	public static int cntr = 0;

	public static int[] rt = new int[MAXN];
	public static double[] num = new double[MAXT];
	public static int[] left = new int[MAXT];
	public static int[] right = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int[] father = new int[MAXT];
	public static int cntt = 0;

	public static int[] idx = new int[MAXH];
	public static double[] cost = new double[MAXH];
	public static int cntd;
	public static int[] heap = new int[MAXH];
	public static int cnth;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] path = new int[MAXN];
	public static double[] dis = new double[MAXN];

	public static void addEdgeG(int u, int v, double w) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		weightg[cntg] = w;
		headg[u] = cntg;
	}

	public static void addEdgeR(int u, int v, double w) {
		nextr[++cntr] = headr[u];
		tor[cntr] = v;
		weightr[cntr] = w;
		headr[u] = cntr;
	}

	public static int init(int f, double v) {
		num[++cntt] = v;
		left[cntt] = right[cntt] = dist[cntt] = 0;
		father[cntt] = f;
		return cntt;
	}

	public static int clone(int i) {
		num[++cntt] = num[i];
		left[cntt] = left[i];
		right[cntt] = right[i];
		dist[cntt] = dist[i];
		father[cntt] = father[i];
		return cntt;
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (num[i] > num[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		int k = clone(i);
		right[k] = merge(right[k], j);
		if (dist[left[k]] < dist[right[k]]) {
			tmp = left[k];
			left[k] = right[k];
			right[k] = tmp;
		}
		dist[k] = dist[right[k]] + 1;
		return k;
	}

	public static void heapAdd(int i, double v) {
		idx[++cntd] = i;
		cost[cntd] = v;
		heap[++cnth] = cntd;
		int cur = cnth, up = cur / 2, tmp;
		while (cur > 1 && cost[heap[up]] > cost[heap[cur]]) {
			tmp = heap[up];
			heap[up] = heap[cur];
			heap[cur] = tmp;
			cur = up;
			up = cur / 2;
		}
	}

	public static int heapPop() {
		int ans = heap[1];
		heap[1] = heap[cnth--];
		int cur = 1, l = cur * 2, r = l + 1, best, tmp;
		while (l <= cnth) {
			best = (r <= cnth && cost[heap[r]] < cost[heap[l]]) ? r : l;
			best = (cost[heap[best]] < cost[heap[cur]]) ? best : cur;
			if (best == cur) {
				break;
			}
			tmp = heap[best];
			heap[best] = heap[cur];
			heap[cur] = tmp;
			cur = best;
			l = cur * 2;
			r = l + 1;
		}
		return ans;
	}

	public static boolean isEmpty() {
		return cnth == 0;
	}

	public static void dijkstra() {
		dis[n] = 0;
		for (int i = 1; i < n; i++) {
			dis[i] = Double.POSITIVE_INFINITY;
		}
		cntd = cnth = 0;
		heapAdd(n, 0);
		while (!isEmpty()) {
			int h = heapPop();
			int u = idx[h];
			double w = cost[h];
			if (!vis[u]) {
				vis[u] = true;
				for (int e = headr[u], v; e > 0; e = nextr[e]) {
					v = tor[e];
					if (dis[v] > w + weightr[e]) {
						dis[v] = w + weightr[e];
						path[v] = e;
						heapAdd(v, dis[v]);
					}
				}
			}
		}
	}

	public static void mergeRoad() {
		cntd = cnth = 0;
		for (int i = 1; i <= n; i++) {
			heapAdd(i, dis[i]);
		}
		dist[0] = -1;
		while (!isEmpty()) {
			int h = heapPop();
			int u = idx[h];
			for (int e = headg[u]; e > 0; e = nextg[e]) {
				if (e != path[u]) {
					rt[u] = merge(rt[u], init(tog[e], weightg[e] + dis[tog[e]] - dis[u]));
				}
			}
			if (path[u] != 0) {
				rt[u] = merge(rt[u], rt[tog[path[u]]]);
			}
		}
	}

	public static int expand() {
		int ans = 0;
		money -= dis[1];
		if (money >= 0) {
			ans++;
			cntd = cnth = 0;
			if (rt[1] != 0) {
				heapAdd(rt[1], num[rt[1]]);
			}
			while (!isEmpty()) {
				int h = heapPop();
				int u = idx[h];
				double w = cost[h];
				money -= w + dis[1];
				if (money < 0) {
					break;
				}
				ans++;
				if (left[u] != 0) {
					heapAdd(left[u], w - num[u] + num[left[u]]);
				}
				if (right[u] != 0) {
					heapAdd(right[u], w - num[u] + num[right[u]]);
				}
				if (father[u] != 0 && rt[father[u]] != 0) {
					heapAdd(rt[father[u]], w + num[rt[father[u]]]);
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		money = in.nextDouble();
		int u, v;
		double w;
		for (int i = 1; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextDouble();
			if (u != n) {
				addEdgeG(u, v, w);
				addEdgeR(v, u, w);
			}
		}
		dijkstra();
		mergeRoad();
		out.write(expand() + "\n");
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public boolean hasNext() throws IOException {
			while (hasNextByte()) {
				byte b = buffer[ptr];
				if (!isWhitespace(b))
					return true;
				ptr++;
			}
			return false;
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		public double nextDouble() throws IOException {
			double num = 0, div = 1;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != '.' && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			if (b == '.') {
				b = readByte();
				while (!isWhitespace(b) && b != -1) {
					num += (b - '0') / (div *= 10);
					b = readByte();
				}
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}