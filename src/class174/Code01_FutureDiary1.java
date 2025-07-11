package class174;

// 未来日记，java版
// 给定一个长度为n的数组arr，一共有m条操作，每条操作类型如下
// 操作 1 l r x y : arr[l..r]范围上，所有值x变成值y
// 操作 2 l r k   : arr[l..r]范围上，查询第k小的值
// 1 <= n、m、arr[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4119
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code01_FutureDiary2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_FutureDiary1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	public static int[] tmp = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	public static int[] idxrt = new int[MAXN];
	public static int[][] valrt = new int[MAXB][MAXN];
	public static int[][] rtval = new int[MAXB][MAXN];

	public static int[][] sum1 = new int[MAXB][MAXB];
	public static int[][] sum2 = new int[MAXB][MAXN];
	public static int[] cnt1 = new int[MAXB];
	public static int[] cnt2 = new int[MAXN];

	public static void build(int b) {
		for (int i = 1; i <= blen; i++) {
			valrt[b][rtval[b][i]] = 0;
		}
		for (int i = bl[b], cnt = 0; i <= br[b]; i++) {
			if (valrt[b][arr[i]] == 0) {
				cnt++;
				valrt[b][arr[i]] = cnt;
				rtval[b][cnt] = arr[i];
			}
			idxrt[i] = valrt[b][arr[i]];
		}
	}

	public static void down(int b) {
		for (int i = bl[b]; i <= br[b]; i++) {
			arr[i] = rtval[b][idxrt[i]];
		}
	}

	public static void xToy(int b, int x, int y) {
		valrt[b][y] = valrt[b][x];
		rtval[b][valrt[b][x]] = y;
		valrt[b][x] = 0;
	}

	public static void innerUpdate(int l, int r, int x, int y) {
		down(bi[l]);
		for (int i = l; i <= r; i++) {
			if (arr[i] == x) {
				sum1[bi[i]][bi[x]]--;
				sum1[bi[i]][bi[y]]++;
				sum2[bi[i]][x]--;
				sum2[bi[i]][y]++;
				arr[i] = y;
			}
		}
		build(bi[l]);
	}

	public static void update(int l, int r, int x, int y) {
		if (x == y || (sum2[bi[r]][x] - sum2[bi[l] - 1][x] == 0)) {
			return;
		}
		for (int b = bi[n]; b >= bi[l]; b--) {
			sum1[b][bi[x]] -= sum1[b - 1][bi[x]];
			sum1[b][bi[y]] -= sum1[b - 1][bi[y]];
			sum2[b][x] -= sum2[b - 1][x];
			sum2[b][y] -= sum2[b - 1][y];
		}
		if (bi[l] == bi[r]) {
			innerUpdate(l, r, x, y);
		} else {
			innerUpdate(l, br[bi[l]], x, y);
			innerUpdate(bl[bi[r]], r, x, y);
			for (int b = bi[l] + 1; b <= bi[r] - 1; b++) {
				if (sum2[b][x] != 0) {
					if (sum2[b][y] != 0) {
						innerUpdate(bl[b], br[b], x, y);
					} else {
						sum1[b][bi[y]] += sum2[b][x];
						sum1[b][bi[x]] -= sum2[b][x];
						sum2[b][y] += sum2[b][x];
						sum2[b][x] = 0;
						xToy(b, x, y);
					}
				}
			}
		}
		for (int b = bi[l]; b <= bi[n]; b++) {
			sum1[b][bi[x]] += sum1[b - 1][bi[x]];
			sum1[b][bi[y]] += sum1[b - 1][bi[y]];
			sum2[b][x] += sum2[b - 1][x];
			sum2[b][y] += sum2[b - 1][y];
		}
	}

	public static int query(int l, int r, int k) {
		int ans = 0;
		if (bi[l] == bi[r]) {
			down(bi[l]);
			for (int i = l; i <= r; i++) {
				tmp[i] = arr[i];
			}
			Arrays.sort(tmp, l, r + 1);
			ans = tmp[l + k - 1];
		} else {
			down(bi[l]);
			down(bi[r]);
			for (int i = l; i <= br[bi[l]]; i++) {
				cnt1[bi[arr[i]]]++;
				cnt2[arr[i]]++;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				cnt1[bi[arr[i]]]++;
				cnt2[arr[i]]++;
			}
			int sumCnt = 0;
			int vblock = 0;
			for (int b = 1; b <= bi[MAXN - 1]; b++) {
				int blockCnt = cnt1[b] + sum1[bi[r] - 1][b] - sum1[bi[l]][b];
				if (sumCnt + blockCnt < k) {
					sumCnt += blockCnt;
				} else {
					vblock = b;
					break;
				}
			}
			for (int v = (vblock - 1) * blen + 1; v <= vblock * blen; v++) {
				int valCnt = cnt2[v] + sum2[bi[r] - 1][v] - sum2[bi[l]][v];
				if (sumCnt + valCnt >= k) {
					ans = v;
					break;
				} else {
					sumCnt += valCnt;
				}
			}
			for (int i = l; i <= br[bi[l]]; i++) {
				cnt1[bi[arr[i]]] = cnt2[arr[i]] = 0;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				cnt1[bi[arr[i]]] = cnt2[arr[i]] = 0;
			}
		}
		return ans;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i < MAXN; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
			build(i);
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = 1; j < MAXB; j++) {
				sum1[i][j] = sum1[i - 1][j];
			}
			for (int j = 1; j < MAXN; j++) {
				sum2[i][j] = sum2[i - 1][j];
			}
			for (int j = bl[i]; j <= br[i]; j++) {
				sum1[i][bi[arr[j]]]++;
				sum2[i][arr[j]]++;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, op, l, r, x, y, k; i <= m; i++) {
			op = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			if (op == 1) {
				x = in.nextInt();
				y = in.nextInt();
				update(l, r, x, y);
			} else {
				k = in.nextInt();
				out.println(query(l, r, k));
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
