package class160;

// 线段树套平衡树，java版
// 给定一个长度为n的数组arr，下标1~n，每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 5 * 10^4
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_SegmentWithBalanced1 {

	public static int MAXN = 50001;

	public static int MAXT = MAXN * 40;

	public static int INF = Integer.MAX_VALUE;

	public static double ALPHA = 0.7;

	public static int n, m;

	// 原始数组
	public static int[] arr = new int[MAXN];

	// 线段树维护的替罪羊树根节点编号
	public static int[] root = new int[MAXN << 2];

	// 替罪羊树需要
	public static int[] key = new int[MAXT];

	public static int[] cnts = new int[MAXT];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int[] size = new int[MAXT];

	public static int[] diff = new int[MAXT];

	public static int cnt = 0;

	// rebuild用到的中序收集数组
	public static int[] collect = new int[MAXT];

	public static int ci;

	// 最上方的失衡点、失衡点的父节点、失衡点的方向
	public static int top, father, side;

	public static int init(int num) {
		key[++cnt] = num;
		left[cnt] = right[cnt] = 0;
		cnts[cnt] = size[cnt] = diff[cnt] = 1;
		return cnt;
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + cnts[i];
		diff[i] = diff[left[i]] + diff[right[i]] + (cnts[i] > 0 ? 1 : 0);
	}

	public static boolean balance(int i) {
		return i == 0 || ALPHA * diff[i] >= Math.max(diff[left[i]], diff[right[i]]);
	}

	public static void inorder(int i) {
		if (i != 0) {
			inorder(left[i]);
			if (cnts[i] > 0) {
				collect[++ci] = i;
			}
			inorder(right[i]);
		}
	}

	public static int innerBuild(int l, int r) {
		if (l > r) {
			return 0;
		}
		int m = (l + r) >> 1;
		int h = collect[m];
		left[h] = innerBuild(l, m - 1);
		right[h] = innerBuild(m + 1, r);
		up(h);
		return h;
	}

	public static int innerRebuild(int i) {
		if (top != 0) {
			ci = 0;
			inorder(top);
			if (ci > 0) {
				if (father == 0) {
					i = innerBuild(1, ci);
				} else if (side == 1) {
					left[father] = innerBuild(1, ci);
				} else {
					right[father] = innerBuild(1, ci);
				}
			}
		}
		return i;
	}

	public static int innerInsert(int num, int i, int f, int s) {
		if (i == 0) {
			i = init(num);
		} else {
			if (key[i] == num) {
				cnts[i]++;
			} else if (key[i] > num) {
				left[i] = innerInsert(num, left[i], i, 1);
			} else {
				right[i] = innerInsert(num, right[i], i, 2);
			}
			up(i);
			if (!balance(i)) {
				top = i;
				father = f;
				side = s;
			}
		}
		return i;
	}

	public static int innerInsert(int num, int i) {
		top = father = side = 0;
		i = innerInsert(num, i, 0, 0);
		i = innerRebuild(i);
		return i;
	}

	public static int innerSmall(int num, int i) {
		if (i == 0) {
			return 0;
		}
		if (key[i] >= num) {
			return innerSmall(num, left[i]);
		} else {
			return size[left[i]] + cnts[i] + innerSmall(num, right[i]);
		}
	}

	public static int innerIndex(int index, int i) {
		int leftsize = size[left[i]];
		if (leftsize >= index) {
			return innerIndex(index, left[i]);
		} else if (leftsize + cnts[i] < index) {
			return innerIndex(index - leftsize - cnts[i], right[i]);
		} else {
			return key[i];
		}
	}

	public static int innerPre(int num, int i) {
		int kth = innerSmall(num, i) + 1;
		if (kth == 1) {
			return -INF;
		} else {
			return innerIndex(kth - 1, i);
		}
	}

	public static int innerPost(int num, int i) {
		int kth = innerSmall(num + 1, i);
		if (kth == size[i]) {
			return INF;
		} else {
			return innerIndex(kth + 1, i);
		}
	}

	public static void innerRemove(int num, int i, int f, int s) {
		if (key[i] == num) {
			cnts[i]--;
		} else if (key[i] > num) {
			innerRemove(num, left[i], i, 1);
		} else {
			innerRemove(num, right[i], i, 2);
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
	}

	public static int innerRemove(int num, int i) {
		if (innerSmall(num, i) != innerSmall(num + 1, i)) {
			top = father = side = 0;
			innerRemove(num, i, 0, 0);
			i = innerRebuild(i);
		}
		return i;
	}

	public static void build(int l, int r, int i) {
		for (int j = l; j <= r; j++) {
			root[i] = innerInsert(arr[j], root[i]);
		}
		if (l < r) {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
	}

	public static void update(int jobi, int jobv, int l, int r, int i) {
		root[i] = innerRemove(arr[jobi], root[i]);
		root[i] = innerInsert(jobv, root[i]);
		if (l < r) {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int small(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return innerSmall(jobv, root[i]);
		}
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans += small(jobl, jobr, jobv, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += small(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static int number(int jobl, int jobr, int jobk) {
		int l = 0, r = 100000000, mid, ans = 0;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (small(jobl, jobr, mid + 1, 1, n, 1) >= jobk) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static int pre(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return innerPre(jobv, root[i]);
		}
		int mid = (l + r) >> 1;
		int ans = -INF;
		if (jobl <= mid) {
			ans = Math.max(ans, pre(jobl, jobr, jobv, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, pre(jobl, jobr, jobv, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static int post(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return innerPost(jobv, root[i]);
		}
		int mid = (l + r) >> 1;
		int ans = INF;
		if (jobl <= mid) {
			ans = Math.min(ans, post(jobl, jobr, jobv, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, post(jobl, jobr, jobv, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		build(1, n, 1);
		for (int i = 1, op, x, y, z; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			if (op == 3) {
				update(x, y, 1, n, 1);
				arr[x] = y;
			} else {
				in.nextToken();
				z = (int) in.nval;
				if (op == 1) {
					out.println(small(x, y, z, 1, n, 1) + 1);
				} else if (op == 2) {
					out.println(number(x, y, z));
				} else if (op == 4) {
					out.println(pre(x, y, z, 1, n, 1));
				} else {
					out.println(post(x, y, z, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}
}
