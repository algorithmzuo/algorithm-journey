package class178;

// 莫队二次离线入门题，java版
// 给定一个长度为n的数组arr，给定一个非负整数k，下面给出合法二元组的定义
// 位置二元组(i, j)，i和j必须是不同的，并且 arr[i]异或arr[j] 的二进制状态里有k个1
// 当i != j时，(i, j)和(j, i)认为是相同的二元组
// 一共有m条查询，格式为 l r : 打印arr[l..r]范围上，有多少合法二元组
// 1 <= n、m <= 10^5
// 0 <= arr[i]、k < 16384(2的14次方)
// 测试链接 : https://www.luogu.com.cn/problem/P4887
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code01_MoOfflineTwice2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code01_MoOfflineTwice1 {

	public static int MAXN = 100001;
	public static int MAXV = 1 << 14;
	public static int n, m, k;
	public static int[] arr = new int[MAXN];
	public static int[] bi = new int[MAXN];
	public static int[] kOneArr = new int[MAXV];
	public static int cntk;

	// 莫队任务，l、r、id
	public static int[][] query = new int[MAXN][3];
	// 离线任务，x、l、r、op、id
	// 位置x的任务列表用链式前向星表示
	public static int[] headq = new int[MAXN];
	public static int[] nextq = new int[MAXN << 1];
	public static int[] ql = new int[MAXN << 1];
	public static int[] qr = new int[MAXN << 1];
	public static int[] qop = new int[MAXN << 1];
	public static int[] qid = new int[MAXN << 1];
	public static int cntq;

	// cnt[v] : 当前的数字v作为第二个数，之前出现的数字作为第一个数，产生多少合法二元组
	public static int[] cnt = new int[MAXV];
	// pre[i] : 单点信息的前缀和，课上重点图解了
	public static long[] pre = new long[MAXN];

	public static long[] ans = new long[MAXN];

	public static class QueryCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			return a[1] - b[1];
		}
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static int countOne(int num) {
		int ret = 0;
		while (num > 0) {
			ret++;
			num -= lowbit(num);
		}
		return ret;
	}

	public static void addOffline(int x, int l, int r, int op, int id) {
		nextq[++cntq] = headq[x];
		headq[x] = cntq;
		ql[cntq] = l;
		qr[cntq] = r;
		qop[cntq] = op;
		qid[cntq] = id;
	}

	public static void prepare() {
		int blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		Arrays.sort(query, 1, m + 1, new QueryCmp());
		for (int v = 0; v < MAXV; v++) {
			if (countOne(v) == k) {
				kOneArr[++cntk] = v;
			}
		}
	}

	public static void compute() {
		for (int i = 1; i <= n; i++) {
			pre[i] = pre[i - 1] + cnt[arr[i]];
			for (int j = 1; j <= cntk; j++) {
				cnt[arr[i] ^ kOneArr[j]]++;
			}
		}
		// 第一次离线，执行莫队
		int winl = 1, winr = 0;
		for (int i = 1; i <= m; i++) {
			int jobl = query[i][0];
			int jobr = query[i][1];
			int id = query[i][2];
			if (winr < jobr) {
				addOffline(winl - 1, winr + 1, jobr, -1, id);
				ans[id] += pre[jobr] - pre[winr];
			}
			if (winr > jobr) {
				addOffline(winl - 1, jobr + 1, winr, 1, id);
				ans[id] -= pre[winr] - pre[jobr];
			}
			winr = jobr;
			if (winl > jobl) {
				addOffline(winr, jobl, winl - 1, 1, id);
				ans[id] -= pre[winl - 1] - pre[jobl - 1];
			}
			if (winl < jobl) {
				addOffline(winr, winl, jobl - 1, -1, id);
				ans[id] += pre[jobl - 1] - pre[winl - 1];
			}
			winl = jobl;
		}
		// 第二次离线，执行离线任务
		Arrays.fill(cnt, 0);
		for (int i = 0; i <= n; i++) {
			if (i >= 1) {
				for (int j = 1; j <= cntk; j++) {
					cnt[arr[i] ^ kOneArr[j]]++;
				}
			}
			for (int q = headq[i]; q > 0; q = nextq[q]) {
				int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
				for (int j = l; j <= r; j++) {
					// 计算j 对 1..i范围的贡献
					// 此时1..i范围上的数字都更新过cnt
					if (j <= i && k == 0) {
						// j在1..i范围上，此时又有k==0
						// 那么arr[j]一定更新过cnt，并且(arr[j], arr[j])一定算进贡献了
						// 但是题目要求的二元组必须是不同位置，所以贡献要进行减1修正
						ans[id] += (long) op * (cnt[arr[j]] - 1);
					} else {
						// 要么j不在1..i范围上，arr[j]没更新过cnt
						// 要么k!=0，(arr[j], arr[j])无法被算成贡献
						// 无论哪种情况，贡献都是正确的，不用进行减1修正
						ans[id] += (long) op * cnt[arr[j]];
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			query[i][0] = in.nextInt();
			query[i][1] = in.nextInt();
			query[i][2] = i;
		}
		prepare();
		compute();
		for (int i = 2; i <= m; i++) {
			ans[query[i][2]] += ans[query[i - 1][2]];
		}
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
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
