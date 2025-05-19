package class169;

// 带修改的区间第k小，java版
// 给定一个长度为n的数组arr，接下来是m条操作，每种操作是如下两种类型的一种
// 操作 1 x y   : 把x位置的值修改成y
// 操作 2 x y v : 查询arr[x..y]范围上第v小的值
// 1 <= n、m <= 10^5
// 1 <= 数组中的值 <= 10^9
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5412
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_CrbQueries1 {

	public static int MAXN = 100001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] arr = new int[MAXN];

	// 事件编号组成的数组
	public static int[] eid = new int[MAXN << 2];
	// op == 1，代表修改事件，x处，值y，效果v
	// op == 2，代表查询事件，[x..y]范围上查询第v小，答案填入q位置
	public static int[] op = new int[MAXN << 2];
	public static int[] x = new int[MAXN << 2];
	public static int[] y = new int[MAXN << 2];
	public static int[] v = new int[MAXN << 2];
	public static int[] q = new int[MAXN << 2];

	// 树状数组
	public static int[] tree = new int[MAXN];

	// 整体二分
	public static int[] lset = new int[MAXN << 2];
	public static int[] rset = new int[MAXN << 2];

	// 查询的答案
	public static int[] ans = new int[MAXN];

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

	public static int query(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	public static void compute(int el, int er, int vl, int vr) {
		if (el > er) {
			return;
		}
		if (vl == vr) {
			for (int i = el; i <= er; i++) {
				int id = eid[i];
				if (op[id] == 2) {
					ans[q[id]] = vl;
				}
			}
		} else {
			int mid = (vl + vr) >> 1;
			int lsiz = 0, rsiz = 0;
			for (int i = el; i <= er; i++) {
				int id = eid[i];
				if (op[id] == 1) {
					if (y[id] <= mid) {
						add(x[id], v[id]);
						lset[++lsiz] = id;
					} else {
						rset[++rsiz] = id;
					}
				} else {
					int satisfy = query(x[id], y[id]);
					if (v[id] <= satisfy) {
						lset[++lsiz] = id;
					} else {
						v[id] -= satisfy;
						rset[++rsiz] = id;
					}
				}
			}
			for (int i = 1; i <= lsiz; i++) {
				eid[el + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				eid[el + lsiz + i - 1] = rset[i];
			}
			for (int i = 1; i <= lsiz; i++) {
				int id = lset[i];
				if (op[id] == 1 && y[id] <= mid) {
					add(x[id], -v[id]);
				}
			}
			compute(el, el + lsiz - 1, vl, mid);
			compute(el + lsiz, er, mid + 1, vr);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			Arrays.fill(tree, 1, n + 1, 0);
			int eventCnt = 0;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
				op[++eventCnt] = 1;
				x[eventCnt] = i;
				y[eventCnt] = arr[i];
				v[eventCnt] = 1;
			}
			in.nextToken();
			m = (int) in.nval;
			int queryCnt = 0;
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				op[++eventCnt] = (int) in.nval;
				if (op[eventCnt] == 1) {
					in.nextToken();
					int a = (int) in.nval;
					in.nextToken();
					int b = (int) in.nval;
					// 前面的值取消
					x[eventCnt] = a;
					y[eventCnt] = arr[a];
					v[eventCnt] = -1;
					// 当前的值生效
					op[++eventCnt] = 1;
					x[eventCnt] = a;
					y[eventCnt] = b;
					v[eventCnt] = 1;
					arr[a] = b;
				} else {
					in.nextToken();
					x[eventCnt] = (int) in.nval;
					in.nextToken();
					y[eventCnt] = (int) in.nval;
					in.nextToken();
					v[eventCnt] = (int) in.nval;
					q[eventCnt] = ++queryCnt;
				}
			}
			for (int i = 1; i <= eventCnt; i++) {
				eid[i] = i;
			}
			compute(1, eventCnt, 1, INF);
			for (int i = 1; i <= queryCnt; i++) {
				out.println(ans[i]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
