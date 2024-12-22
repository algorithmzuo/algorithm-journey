package class155;

// 棘手的操作，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3273
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class Code02_TrickyOperation1 {

	public static int MAXN = 300001;

	public static int[] num = new int[MAXN];

	public static int[] up = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] add = new int[MAXN];

	public static TreeMap<Integer, Integer> heap = new TreeMap<>();

	public static int addAll = 0;

	public static int[] stack = new int[MAXN];

	public static int n, m;

	public static void minusHead(int h) {
		if (h != 0) {
			int hnum = num[h] + add[h];
			if (heap.get(hnum) == 1) {
				heap.remove(hnum);
			} else {
				heap.put(hnum, heap.get(hnum) - 1);
			}
		}
	}

	public static void addHead(int h) {
		if (h != 0) {
			int hnum = num[h] + add[h];
			heap.put(hnum, heap.getOrDefault(hnum, 0) + 1);
		}
	}

	public static void prepare() {
		dist[0] = -1;
		heap.clear();
		for (int i = 1; i <= n; i++) {
			up[i] = left[i] = right[i] = dist[i] = 0;
			father[i] = i;
			size[i] = 1;
			add[i] = 0;
			addHead(i);
		}
		addAll = 0;
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (num[i] < num[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		up[right[i]] = i;
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int remove(int i) {
		int h = find(i);
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		int s = merge(left[i], right[i]);
		int f = up[i];
		father[i] = s;
		up[s] = f;
		if (h != i) {
			father[s] = h;
			if (left[f] == i) {
				left[f] = s;
			} else {
				right[f] = s;
			}
			for (int d = dist[s], tmp; dist[f] > d + 1; f = up[f], d++) {
				dist[f] = d + 1;
				if (dist[left[f]] < dist[right[f]]) {
					tmp = left[f];
					left[f] = right[f];
					right[f] = tmp;
				}
			}
		}
		up[i] = left[i] = right[i] = dist[i] = 0;
		return father[s];
	}

	public static void down(int i, int v) {
		if (i != 0) {
			add[i] = 0;
			int size = 0;
			stack[++size] = i;
			while (size > 0) {
				i = stack[size--];
				num[i] += v;
				if (right[i] != 0) {
					stack[++size] = right[i];
				}
				if (left[i] != 0) {
					stack[++size] = left[i];
				}
			}
		}
	}

	public static void u(int i, int j) {
		int l = find(i);
		int r = find(j);
		if (l == r) {
			return;
		}
		int lsize = size[l];
		minusHead(l);
		int rsize = size[r];
		minusHead(r);
		int addTag;
		if (lsize <= rsize) {
			down(l, add[l] - add[r]);
			addTag = add[r];
		} else {
			down(r, add[r] - add[l]);
			addTag = add[l];
		}
		int h = merge(l, r);
		size[h] = lsize + rsize;
		add[h] = addTag;
		addHead(h);
	}

	public static void a1(int i, int v) {
		int h = find(i);
		minusHead(h);
		int l = remove(i);
		if (l != 0) {
			size[l] = size[h] - 1;
			add[l] = add[h];
			addHead(l);
		}
		num[i] = num[i] + add[h] + v;
		father[i] = i;
		size[i] = 1;
		add[i] = 0;
		addHead(i);
		u(l, i);
	}

	public static void a2(int i, int v) {
		int h = find(i);
		minusHead(h);
		add[h] += v;
		addHead(h);
	}

	public static void a3(int v) {
		addAll += v;
	}

	public static int f1(int i) {
		return num[i] + add[find(i)] + addAll;
	}

	public static int f2(int i) {
		int h = find(i);
		return num[h] + add[h] + addAll;
	}

	public static int f3() {
		return heap.lastKey() + addAll;
	}

	public static void main(String[] args) {
		ReaderWriter io = new ReaderWriter();
		n = io.nextInt();
		for (int i = 1; i <= n; i++) {
			num[i] = io.nextInt();
		}
		prepare();
		m = io.nextInt();
		String op;
		for (int i = 1, x, y; i <= m; i++) {
			op = io.next();
			if (op.equals("F3")) {
				io.println(f3());
			} else {
				x = io.nextInt();
				if (op.equals("U")) {
					y = io.nextInt();
					u(x, y);
				} else if (op.equals("A1")) {
					y = io.nextInt();
					a1(x, y);
				} else if (op.equals("A2")) {
					y = io.nextInt();
					a2(x, y);
				} else if (op.equals("A3")) {
					a3(x);
				} else if (op.equals("F1")) {
					io.println(f1(x));
				} else {
					io.println(f2(x));
				}
			}
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class ReaderWriter extends PrintWriter {
		byte[] buf = new byte[1 << 10];
		int bId = 0, bSize = 0;
		boolean eof = false;

		public ReaderWriter() {
			super(System.out);
		}

		private byte getByte() {
			if (bId >= bSize) {
				try {
					bSize = System.in.read(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bSize == -1)
					eof = true;
				bId = 0;
			}
			return buf[bId++];
		}

		byte c;

		public boolean hasNext() {
			if (eof)
				return false;
			while ((c = getByte()) <= 32 && !eof)
				;
			if (eof)
				return false;
			bId--;
			return true;
		}

		public String next() {
			if (!hasNext())
				return null;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			StringBuilder sb = new StringBuilder();
			while (c > 32) {
				sb.append((char) c);
				c = getByte();
			}
			return sb.toString();
		}

		public int nextInt() {
			if (!hasNext())
				return Integer.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			int val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public long nextLong() {
			if (!hasNext())
				return Long.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			long val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public double nextDouble() {
			if (!hasNext())
				return Double.NaN;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			double val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			if (c == '.') {
				double mul = 1;
				c = getByte();
				while (c >= '0' && c <= '9') {
					mul *= 0.1;
					val += (c - '0') * mul;
					c = getByte();
				}
			}
			bId--;
			return val * sign;
		}
	}

}
