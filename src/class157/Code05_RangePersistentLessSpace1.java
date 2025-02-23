package class157;

// 范围修改的可持久化线段树，标记永久化减少空间占用，java版
// 给定一个长度为n的数组arr，下标1~n，时间戳t=0，arr认为是0版本的数组
// 一共有m条查询，每条查询为如下四种类型中的一种
// C x y z : 当前时间戳t版本的数组，[x..y]范围每个数字增加z，得到t+1版本数组，并且t++
// Q x y   : 当前时间戳t版本的数组，打印[x..y]范围累加和
// H x y z : z版本的数组，打印[x..y]范围的累加和
// B x     : 当前时间戳t设置成x
// 1 <= n、m <= 10^5
// -10^9 <= arr[i] <= +10^9
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=4348
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是通过不了
// 因为这道题根据C++的运行空间，制定通过标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code05_RangePersistentLessSpace2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class Code05_RangePersistentLessSpace1 {

	public static int MAXN = 100001;

	public static int MAXT = MAXN * 25;

	public static int n, m, t = 0;

	public static int[] arr = new int[MAXN];

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	// 不是真实累加和，而是之前的任务中
	// 不考虑被上方范围截住的任务，只考虑来到当前范围 或者 往下走的任务
	// 累加和变成了什么
	public static long[] sum = new long[MAXT];

	// 不再是懒更新信息，变成标记信息
	public static long[] addTag = new long[MAXT];

	public static int cnt = 0;

	public static int build(int l, int r) {
		int rt = ++cnt;
		addTag[rt] = 0;
		if (l == r) {
			sum[rt] = arr[l];
		} else {
			int mid = (l + r) / 2;
			left[rt] = build(l, mid);
			right[rt] = build(mid + 1, r);
			sum[rt] = sum[left[rt]] + sum[right[rt]];
		}
		return rt;
	}

	public static int add(int jobl, int jobr, long jobv, int l, int r, int i) {
		int rt = ++cnt, a = Math.max(jobl, l), b = Math.min(jobr, r);
		left[rt] = left[i];
		right[rt] = right[i];
		sum[rt] = sum[i] + jobv * (b - a + 1);
		addTag[rt] = addTag[i];
		if (jobl <= l && r <= jobr) {
			addTag[rt] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				left[rt] = add(jobl, jobr, jobv, l, mid, left[rt]);
			}
			if (jobr > mid) {
				right[rt] = add(jobl, jobr, jobv, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	public static long query(int jobl, int jobr, long addHistory, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i] + addHistory * (r - l + 1);
		}
		int mid = (l + r) / 2;
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, addHistory + addTag[i], l, mid, left[i]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, addHistory + addTag[i], mid + 1, r, right[i]);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		root[0] = build(1, n);
		String op;
		for (int i = 1, x, y, z; i <= m; i++) {
			op = in.next();
			if (op.equals("C")) {
				x = in.nextInt();
				y = in.nextInt();
				z = in.nextInt();
				root[t + 1] = add(x, y, z, 1, n, root[t]);
				t++;
			} else if (op.equals("Q")) {
				x = in.nextInt();
				y = in.nextInt();
				out.write(query(x, y, 0, 1, n, root[t]) + "\n");
			} else if (op.equals("H")) {
				x = in.nextInt();
				y = in.nextInt();
				z = in.nextInt();
				out.write(query(x, y, 0, 1, n, root[z]) + "\n");
			} else {
				x = in.nextInt();
				t = x;
			}
		}
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

		public String next() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return null;
			} while (c <= ' ');
			StringBuilder sb = new StringBuilder();
			while (c > ' ') {
				sb.append((char) c);
				c = readByte();
			}
			return sb.toString();
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
