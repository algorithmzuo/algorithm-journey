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

public class Code01_FutureDiary1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// idxset[i]表示下标i，在归属的块中，所属集合的编号
	// valset[b][v]表示序列块b中的数值v，所属集合的编号
	// setval[b][s]表示序列块b中的集合s，对应的数值
	public static int[] idxset = new int[MAXN];
	public static int[][] valset = new int[MAXB][MAXN];
	public static int[][] setval = new int[MAXB][MAXN];

	// sum1[k][b]表示前k个序列块中，第b块个值域块的数字有几个
	// sum2[k][v]表示前k个序列块中，数字v有几个
	// cnt1[b]表示遍历散块之后，第b块个值域块的数字有几个
	// cnt2[v]表示遍历散块之后，数字v有几个
	public static int[][] sum1 = new int[MAXB][MAXB];
	public static int[][] sum2 = new int[MAXB][MAXN];
	public static int[] cnt1 = new int[MAXB];
	public static int[] cnt2 = new int[MAXN];

	// 序列第b块中，根据arr中的值建立起，idxset、valset[b]、setval[b]
	public static void build(int b) {
		for (int i = 1; i <= blen; i++) {
			valset[b][setval[b][i]] = 0;
		}
		for (int i = bl[b], cnt = 0; i <= br[b]; i++) {
			if (valset[b][arr[i]] == 0) {
				cnt++;
				valset[b][arr[i]] = cnt;
				setval[b][cnt] = arr[i];
			}
			idxset[i] = valset[b][arr[i]];
		}
	}

	// 序列第b块中，有些位置的值已经改了，让改动写入arr
	public static void writeArray(int b) {
		for (int i = bl[b]; i <= br[b]; i++) {
			arr[i] = setval[b][idxset[i]];
		}
	}

	// 序列[l..r]范围上，有x有y，把所有x改成y
	public static void innerUpdate(int l, int r, int x, int y) {
		writeArray(bi[l]);
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

	// 序列第b块中，有x无y，把所有x改成y
	public static void xtoy(int b, int x, int y) {
		valset[b][y] = valset[b][x];
		setval[b][valset[b][x]] = y;
		valset[b][x] = 0;
	}

	public static void update(int l, int r, int x, int y) {
		// 必要的剪枝
		if (x == y || (sum2[bi[r]][x] - sum2[bi[l] - 1][x] == 0)) {
			return;
		}
		// 前缀统计变成当前块统计
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
						// 整块更新时，调用innerUpdate的次数 <= 块长
						innerUpdate(bl[b], br[b], x, y);
					} else {
						sum1[b][bi[y]] += sum2[b][x];
						sum1[b][bi[x]] -= sum2[b][x];
						sum2[b][y] += sum2[b][x];
						sum2[b][x] = 0;
						xtoy(b, x, y);
					}
				}
			}
		}
		// 当前块统计变回前缀统计
		for (int b = bi[l]; b <= bi[n]; b++) {
			sum1[b][bi[x]] += sum1[b - 1][bi[x]];
			sum1[b][bi[y]] += sum1[b - 1][bi[y]];
			sum2[b][x] += sum2[b - 1][x];
			sum2[b][y] += sum2[b - 1][y];
		}
	}

	public static void addCnt(int l, int r) {
		for (int i = l; i <= r; i++) {
			cnt1[bi[arr[i]]]++;
			cnt2[arr[i]]++;
		}
	}

	public static void clearCnt(int l, int r) {
		for (int i = l; i <= r; i++) {
			cnt1[bi[arr[i]]] = cnt2[arr[i]] = 0;
		}
	}

	public static int query(int l, int r, int k) {
		int ans = 0;
		boolean inner = bi[l] == bi[r];
		// 建立散块的词频统计
		if (inner) {
			writeArray(bi[l]);
			addCnt(l, r);
		} else {
			writeArray(bi[l]);
			writeArray(bi[r]);
			addCnt(l, br[bi[l]]);
			addCnt(bl[bi[r]], r);
		}
		int sumCnt = 0;
		int vblock = 0;
		// 定位第k小的数字，来自哪个值域块
		for (int b = 1; b <= bi[MAXN - 1]; b++) {
			// 如果不存在中间的整块，词频 = 散块词频，否则 词频 = 散块词频 + 整块词频
			int cnt = cnt1[b] + (inner ? 0 : sum1[bi[r] - 1][b] - sum1[bi[l]][b]);
			if (sumCnt + cnt >= k) {
				vblock = b;
				break;
			} else {
				sumCnt += cnt;
			}
		}
		// 定位第k小的数字，来自值域块的具体数字
		for (int v = (vblock - 1) * blen + 1; v <= vblock * blen; v++) {
			// 如果不存在中间的整块，词频 = 散块词频，否则 词频 = 散块词频 + 整块词频
			int cnt = cnt2[v] + (inner ? 0 : sum2[bi[r] - 1][v] - sum2[bi[l]][v]);
			if (sumCnt + cnt >= k) {
				ans = v;
				break;
			} else {
				sumCnt += cnt;
			}
		}
		// 清空散块的词频统计
		if (inner) {
			clearCnt(l, r);
		} else {
			clearCnt(l, br[bi[l]]);
			clearCnt(bl[bi[r]], r);
		}
		return ans;
	}

	public static void prepare() {
		blen = 300;
		bnum = (n + blen - 1) / blen;
		// i一定要枚举[1, MAXN)
		// 因为不仅序列要分块，值域也要分块
		// bi[i]可以查询下标i来自哪个序列块
		// bi[v]也可查询数字v来自哪个值域块
		for (int i = 1; i < MAXN; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		// bl[i]表示下标第i块的左边界
		// br[i]表示下标第i块的右边界
		// bl、br 仅用于序列分块
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
			build(i);
		}
		// 初始建立sum1、sum2，都表示前缀信息
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
