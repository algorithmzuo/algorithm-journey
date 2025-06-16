package class172;

// 空间少求众数的次数，java版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上，众数到底出现了几次
// 1 <= 所有数值 <= 5 * 10^5
// 内存空间只有64MB，题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P5048
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是内存占用过大，无法通过测试用例
// 因为这道题只考虑C++能通过的空间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code04_ModeCnt2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_ModeCnt1 {

	public static int MAXN = 500001;
	public static int MAXB = 801;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// 值、下标
	public static int[][] sortList = new int[MAXN][2];
	// listIdx[i] = j，代表数组的i位置元素在sortList中的j位置
	public static int[] listIdx = new int[MAXN];
	// modeCnt[i][j]表示从i块到j块中众数的出现次数
	public static int[][] modeCnt = new int[MAXB][MAXB];
	// 数字词频统计
	public static int[] numCnt = new int[MAXN];

	public static void prepare() {
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
			sortList[i][0] = arr[i];
			sortList[i][1] = i;
		}
		Arrays.sort(sortList, 1, n + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		for (int i = 1; i <= n; i++) {
			listIdx[sortList[i][1]] = i;
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = i; j <= bnum; j++) {
				int cnt = modeCnt[i][j - 1];
				for (int k = bl[j]; k <= br[j]; k++) {
					cnt = Math.max(cnt, ++numCnt[arr[k]]);
				}
				modeCnt[i][j] = cnt;
			}
			for (int j = 1; j <= n; j++) {
				numCnt[j] = 0;
			}
		}
	}

	public static int query(int l, int r) {
		int ans = 0;
		if (bi[l] == bi[r]) {
			for (int i = l; i <= r; i++) {
				ans = Math.max(ans, ++numCnt[arr[i]]);
			}
			for (int i = l; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		} else {
			ans = modeCnt[bi[l] + 1][bi[r] - 1];
			for (int i = l, idx; i <= br[bi[l]]; i++) {
				idx = listIdx[i];
				while (idx + ans <= n && sortList[idx + ans][0] == arr[i] && sortList[idx + ans][1] <= r) {
					ans++;
				}
			}
			for (int i = bl[bi[r]], idx; i <= r; i++) {
				idx = listIdx[i];
				while (idx - ans >= 1 && sortList[idx - ans][0] == arr[i] && sortList[idx - ans][1] >= l) {
					ans++;
				}
			}
		}
		return ans;
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
		for (int i = 1, l, r, lastAns = 0; i <= m; i++) {
			l = in.nextInt() ^ lastAns;
			r = in.nextInt() ^ lastAns;
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
