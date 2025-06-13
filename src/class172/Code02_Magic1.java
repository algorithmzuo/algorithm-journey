package class172;

// 教主的魔法，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2801
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_Magic1 {

	public static int MAXN = 1000001;
	public static int MAXB = 1001;
	public static int n, q;
	public static int[] arr = new int[MAXN];
	public static int[] sortv = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	public static int[] lazy = new int[MAXB];

	public static void build() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		for (int i = 1; i <= n; i++) {
			sortv[i] = arr[i];
		}
		for (int i = 1; i <= bnum; i++) {
			Arrays.sort(sortv, bl[i], br[i] + 1);
		}
	}

	public static int num(int l, int r, int v) {
		v -= lazy[bi[l]];
		int m, ans = 0;
		while (l <= r) {
			m = (l + r) >> 1;
			if (sortv[m] >= v) {
				ans += r - m + 1;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int innerQuery(int l, int r, int v) {
		v -= lazy[bi[l]];
		int ans = 0;
		for (int i = l; i <= r; i++) {
			if (arr[i] >= v) {
				ans++;
			}
		}
		return ans;
	}

	public static int query(int l, int r, int v) {
		int ans = 0;
		if (bi[l] == bi[r]) {
			ans = innerQuery(l, r, v);
		} else {
			ans += innerQuery(l, br[bi[l]], v);
			ans += innerQuery(bl[bi[r]], r, v);
			for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
				ans += num(bl[i], br[i], v);
			}
		}
		return ans;
	}

	public static void innerAdd(int l, int r, int v) {
		for (int i = l; i <= r; i++) {
			arr[i] += v;
		}
		for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
			sortv[i] = arr[i];
		}
		Arrays.sort(sortv, bl[bi[l]], br[bi[l]] + 1);
	}

	public static void add(int l, int r, int v) {
		if (bi[l] == bi[r]) {
			innerAdd(l, r, v);
		} else {
			innerAdd(l, br[bi[l]], v);
			innerAdd(bl[bi[r]], r, v);
			for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
				lazy[i] += v;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		build();
		char op;
		int l, r, v;
		for (int i = 1; i <= q; i++) {
			op = in.nextChar();
			l = in.nextInt();
			r = in.nextInt();
			v = in.nextInt();
			if (op == 'A') {
				out.println(query(l, r, v));
			} else {
				add(l, r, v);
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

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
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

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
