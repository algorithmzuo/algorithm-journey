package class157;

// 区间内的不同数字，java版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5919
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.util.Arrays;

public class Code03_DifferentNumbers1 {

	public static int MAXN = 200002;

	public static int MAXM = MAXN * 37;

	public static int cases, n, q;

	public static int[] arr = new int[MAXN];

	public static int[] pos = new int[MAXN];

	public static int[] root = new int[MAXN];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] diff = new int[MAXM];

	public static int cnt;

	public static int build(int l, int r) {
		int rt = ++cnt;
		if (l == r) {
			return rt;
		}
		int mid = (l + r) / 2;
		left[rt] = build(l, mid);
		right[rt] = build(mid + 1, r);
		return rt;
	}

	public static int update(int jobi, int jobv, int l, int r, int i) {
		int rt = ++cnt;
		left[rt] = left[i];
		right[rt] = right[i];
		diff[rt] = diff[i] + jobv;
		if (l == r) {
			return rt;
		}
		int mid = (l + r) / 2;
		if (jobi <= mid) {
			left[rt] = update(jobi, jobv, l, mid, left[rt]);
		} else {
			right[rt] = update(jobi, jobv, mid + 1, r, right[rt]);
		}
		return rt;
	}

	public static int queryDiff(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return diff[i];
		}
		int mid = (l + r) / 2;
		int ans = 0;
		if (jobl <= mid) {
			ans += queryDiff(jobl, jobr, l, mid, left[i]);
		}
		if (jobr > mid) {
			ans += queryDiff(jobl, jobr, mid + 1, r, right[i]);
		}
		return ans;
	}

	public static int queryKth(int jobk, int l, int r, int i) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		int leftDiff = diff[left[i]];
		if (leftDiff >= jobk) {
			return queryKth(jobk, l, mid, left[i]);
		} else {
			return queryKth(jobk - leftDiff, mid + 1, r, right[i]);
		}
	}

	public static void prepare() {
		cnt = 0;
		Arrays.fill(pos, 0);
		root[n + 1] = build(1, n);
		for (int i = n; i >= 1; i--) {
			if (pos[arr[i]] == 0) {
				root[i] = update(i, 1, 1, n, root[i + 1]);
			} else {
				root[i] = update(pos[arr[i]], -1, 1, n, root[i + 1]);
				root[i] = update(i, 1, 1, n, root[i]);
			}
			pos[arr[i]] = i;
		}
	}

	public static void main(String[] args) throws IOException {
		ReaderWriter io = new ReaderWriter();
		cases = io.nextInt();
		for (int t = 1; t <= cases; t++) {
			n = io.nextInt();
			q = io.nextInt();
			for (int i = 1; i <= n; i++) {
				arr[i] = io.nextInt();
			}
			prepare();
			io.write("Case #");
			io.writeInt(t);
			io.write(":");
			for (int i = 1, l, r, k, lastAns = 0; i <= q; i++) {
				l = (io.nextInt() + lastAns) % n + 1;
				r = (io.nextInt() + lastAns) % n + 1;
				if (l > r) {
					int tmp = l;
					l = r;
					r = tmp;
				}
				k = (queryDiff(l, r, 1, n, root[l]) + 1) >> 1;
				lastAns = queryKth(k, 1, n, root[l]);
				io.write(" ");
				io.writeInt(lastAns);
			}
			io.write("\n");
		}
		io.flush();
	}

	// 读写工具类
	static class ReaderWriter {
		private static final int BUFFER_SIZE = 1 << 9;
		private byte[] inBuf = new byte[BUFFER_SIZE];
		private int bId, bSize;
		private final java.io.InputStream in;

		private byte[] outBuf = new byte[BUFFER_SIZE];
		private int oId;
		private final java.io.OutputStream out;

		ReaderWriter() {
			in = System.in;
			out = System.out;
		}

		private byte read() throws IOException {
			if (bId == bSize) {
				bSize = in.read(inBuf);
				bId = 0;
				if (bSize == -1)
					return -1;
			}
			return inBuf[bId++];
		}

		public int nextInt() throws IOException {
			int s = 0;
			byte c = read();
			while (c <= ' ') {
				if (c == -1)
					return -1;
				c = read();
			}
			boolean neg = (c == '-');
			if (neg)
				c = read();
			while (c >= '0' && c <= '9') {
				s = s * 10 + (c - '0');
				c = read();
			}
			return neg ? -s : s;
		}

		public void write(String s) throws IOException {
			for (int i = 0; i < s.length(); i++) {
				writeByte((byte) s.charAt(i));
			}
		}

		public void writeInt(int x) throws IOException {
			if (x == 0) {
				writeByte((byte) '0');
				return;
			}
			if (x < 0) {
				writeByte((byte) '-');
				x = -x;
			}
			int len = 0;
			byte[] tmp = new byte[12];
			while (x > 0) {
				tmp[len++] = (byte) ((x % 10) + '0');
				x /= 10;
			}
			while (len-- > 0) {
				writeByte(tmp[len]);
			}
		}

		private void writeByte(byte b) throws IOException {
			if (oId == BUFFER_SIZE) {
				flush();
			}
			outBuf[oId++] = b;
		}

		public void flush() throws IOException {
			if (oId > 0) {
				out.write(outBuf, 0, oId);
				oId = 0;
			}
		}
	}

}