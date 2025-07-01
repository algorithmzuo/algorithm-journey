package class173;

// 区间逆序对，java版
// 给定一个长度为n的排列，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上的逆序对数量
// 1 <= n、m <= 10^5
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P5046
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是无法通过测试
// 因为这道题只考虑C++能通过的时间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code04_Inversion2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_Inversion1 {

	public static int MAXN = 100001;
	public static int MAXB = 701;
	public static int n, m;
	public static int[] arr = new int[MAXN];
	// (数值、位置)
	public static int[][] sortv = new int[MAXN][2];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// 树状数组，为了快速生成pre数组和suf数组
	public static int[] tree = new int[MAXN];

	// pre[i] : 从所在块最左位置到i位置，有多少逆序对
	public static int[] pre = new int[MAXN];
	// suf[i] : 从i位置到所在块最右位置，有多少逆序对
	public static int[] suf = new int[MAXN];
	// cnt[i][j] : 前i块里<=j的数字个数
	public static int[][] cnt = new int[MAXB][MAXN];
	// dp[i][j] : 从第i块到第j块有多少逆序对
	public static long[][] dp = new long[MAXB][MAXB];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	// 更靠左的第x块，从xl到xr范围上，选第一个数
	// 更靠右的第y块，从yl到yr范围上，选第二个数
	// x和y可以相等，但是xl..xr需要在yl..yr的左侧
	// 返回逆序对数量
	public static int f(int x, int xl, int xr, int y, int yl, int yr) {
		int ans = 0;
		for (int p1 = bl[x], p2 = bl[y] - 1, cnt = 0; p1 <= br[x]; p1++) {
			if (xl <= sortv[p1][1] && sortv[p1][1] <= xr) {
				while (p2 + 1 <= br[y] && sortv[p1][0] > sortv[p2 + 1][0]) {
					p2++;
					if (yl <= sortv[p2][1] && sortv[p2][1] <= yr) {
						cnt++;
					}
				}
				ans += cnt;
			}
		}
		return ans;
	}

	public static long query(int l, int r) {
		long ans = 0;
		int lb = bi[l];
		int rb = bi[r];
		if (lb == rb) {
			if (l == bl[lb]) {
				ans = pre[r];
			} else {
				ans = pre[r] - pre[l - 1] - f(lb, bl[lb], l - 1, lb, l, r);
			}
		} else {
			// 左散块[l..]内部逆序对 + 右散块[..r]内部逆序对 + 左散块 结合 右散块 的逆序对
			ans = suf[l] + pre[r] + f(lb, l, br[lb], rb, bl[rb], r);
			// 左散块中的arr[i]，作为第一个数
			// 中间整块中的某个数字，作为第二个数
			// 计算这样的逆序对数量
			// 注意因为题目给定的是排列！所以如下这么写没问题
			for (int i = l; i <= br[lb]; i++) {
				ans += cnt[rb - 1][arr[i]] - cnt[lb][arr[i]];
			}
			// 中间整块中的某个数字，作为第一个数
			// 右散块中的arr[i]，作为第二个数
			// 计算这样的逆序对数量
			for (int i = bl[rb]; i <= r; i++) {
				ans += br[rb - 1] - bl[lb + 1] + 1 - (cnt[rb - 1][arr[i]] - cnt[lb][arr[i]]);
			}
			// 中间整块的逆序对
			ans += dp[lb + 1][rb - 1];
		}
		return ans;
	}

	// 注意调整块长
	public static void prepare() {
		blen = (int) Math.sqrt(n / 4);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		for (int i = 1; i <= n; i++) {
			sortv[i][0] = arr[i];
			sortv[i][1] = i;
		}
		for (int i = 1; i <= bnum; i++) {
			Arrays.sort(sortv, bl[i], br[i] + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = bl[i]; j <= br[i]; j++) {
				cnt[i][arr[j]]++;
				if (j != bl[i]) {
					pre[j] = pre[j - 1] + sum(n) - sum(arr[j]);
				}
				add(arr[j], 1);
			}
			for (int j = bl[i]; j <= br[i]; j++) {
				add(arr[j], -1);
			}
			for (int j = br[i]; j >= bl[i]; j--) {
				if (j != br[i]) {
					suf[j] = suf[j + 1] + sum(arr[j]);
				}
				add(arr[j], 1);
			}
			for (int j = bl[i]; j <= br[i]; j++) {
				add(arr[j], -1);
			}
			int tmp = 0;
			for (int j = 1; j <= n; j++) {
				tmp += cnt[i][j];
				cnt[i][j] = tmp + cnt[i - 1][j];
			}
		}
		for (int l = bnum; l >= 1; l--) {
			dp[l][l] = pre[br[l]];
			for (int r = l + 1; r <= bnum; r++) {
				dp[l][r] = dp[l + 1][r] + dp[l][r - 1] - dp[l + 1][r - 1] + f(l, bl[l], br[l], r, bl[r], br[r]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		long lastAns = 0;
		for (int i = 1, l, r; i <= m; i++) {
			l = in.nextInt();
			r = in.nextInt();
			l = (int) (lastAns ^ l);
			r = (int) (lastAns ^ r);
			lastAns = query(l, r);
			out.println(lastAns);
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
