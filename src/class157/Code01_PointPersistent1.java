package class157;

// 单点修改的可持久化线段树模版题1，java版
// 给定一个长度为n的数组arr，下标1~n，原始数组认为是0号版本
// 一共有m条操作，每条操作是如下两种类型中的一种
// v 1 x y : 基于v号版本的数组，把x位置的值设置成y，生成新版本的数组
// v 2 x   : 基于v号版本的数组，打印x位置的值，生成新版本的数组和v版本一致
// 每条操作后得到的新版本数组，版本编号为操作的计数
// 1 <= n, m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3919
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Code01_PointPersistent1 {

	public static int MAXN = 1000001;

	public static int MAXT = MAXN * 23;

	public static int n, m;

	// 原始数组
	public static int[] arr = new int[MAXN];

	// 可持久化线段树需要
	// root[i] : i号版本线段树的头节点编号
	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	// value[i] : 节点i的值信息，只有叶节点有这个信息
	public static int[] value = new int[MAXT];

	// 可持久化线段树的节点空间计数
	public static int cnt = 0;

	// 建树，返回头节点编号
	public static int build(int l, int r) {
		int rt = ++cnt;
		if (l == r) {
			value[rt] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
		}
		return rt;
	}

	// 线段树范围l~r，信息在i号节点里
	// 在l~r范围上，jobi位置的值，设置成jobv
	// 生成的新节点编号返回
	public static int update(int jobi, int jobv, int l, int r, int i) {
		int rt = ++cnt;
		left[rt] = left[i];
		right[rt] = right[i];
		value[rt] = value[i];
		if (l == r) {
			value[rt] = jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				left[rt] = update(jobi, jobv, l, mid, left[rt]);
			} else {
				right[rt] = update(jobi, jobv, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	// 线段树范围l~r，信息在i号节点里
	// 返回l~r范围上jobi位置的值
	public static int query(int jobi, int l, int r, int i) {
		if (l == r) {
			return value[i];
		}
		int mid = (l + r) >> 1;
		if (jobi <= mid) {
			return query(jobi, l, mid, left[i]);
		} else {
			return query(jobi, mid + 1, r, right[i]);
		}
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		m = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = io.nextInt();
		}
		root[0] = build(1, n);
		for (int i = 1, version, op, x, y; i <= m; i++) {
			version = io.nextInt();
			op = io.nextInt();
			x = io.nextInt();
			if (op == 1) {
				y = io.nextInt();
				root[i] = update(x, y, 1, n, root[version]);
			} else {
				root[i] = root[version];
				io.writelnInt(query(x, 1, n, root[i]));
			}
		}
		io.flush();
	}

	// 读写工具类
	static class FastIO {
		private final InputStream is;
		private final OutputStream os;
		private final byte[] inbuf = new byte[1 << 16];
		private int lenbuf = 0;
		private int ptrbuf = 0;
		private final StringBuilder outBuf = new StringBuilder();

		public FastIO(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}

		private int readByte() {
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				if (lenbuf == -1) {
					return -1;
				}
			}
			return inbuf[ptrbuf++] & 0xff;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1) {
				if (b > ' ') {
					return b;
				}
			}
			return -1;
		}

		public int nextInt() {
			int b = skip();
			if (b == -1) {
				throw new RuntimeException("No more integers (EOF)");
			}
			boolean negative = false;
			if (b == '-') {
				negative = true;
				b = readByte();
			}
			int val = 0;
			while (b >= '0' && b <= '9') {
				val = val * 10 + (b - '0');
				b = readByte();
			}
			return negative ? -val : val;
		}

		public void write(String s) {
			outBuf.append(s);
		}

		public void writeInt(int x) {
			outBuf.append(x);
		}

		public void writelnInt(int x) {
			outBuf.append(x).append('\n');
		}

		public void flush() {
			try {
				os.write(outBuf.toString().getBytes());
				os.flush();
				outBuf.setLength(0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}