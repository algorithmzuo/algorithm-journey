package class160;

// 线段树套平衡树，java版
// 给定一个长度为n的数组arr，下标1~n，每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 2 * 10^5
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 提交以下的code，提交时请把类名改成"Main"
// 本题后来增加了测试用例，数据范围放大到 2 * 10^5
// 线段树套平衡树，常数时间大，加上是java实现，导致卡常无法通过
// 想通过用C++实现，本节课Code03_SegmentWithBalanced2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_SegmentWithBalanced1 {

	public static int MAXN = 200001;
	public static int MAXT = MAXN * 40;
	public static int INF = Integer.MAX_VALUE;
	public static int n, m;

	// 原始数组
	public static int[] arr = new int[MAXN];

	// 线段树维护的替罪羊树根节点编号
	public static int[] root = new int[MAXN << 2];

	// 替罪羊树的节点计数
	public static int cntn;

	// 替罪羊树节点的key值
	public static int[] key = new int[MAXT];

	// 替罪羊树节点的左儿子
	public static int[] ls = new int[MAXT];

	// 替罪羊树节点的右儿子
	public static int[] rs = new int[MAXT];

	// 替罪羊树节点是否存活，删掉就是不存活，否则就是存活
	public static boolean[] alive = new boolean[MAXT];

	// 子树上存活的节点数量
	public static int[] aliveSiz = new int[MAXT];

	// 替罪羊树的平衡因子
	public static double ALPHA = 0.7;

	// 最上方不平衡点
	public static int top;

	// 最上方不平衡点的父亲
	public static int father;

	// 最上方不平衡点是其父亲的哪侧儿子
	public static int side;

	// 收集重构子树的所有存活节点
	public static int[] collect = new int[MAXN];
	public static int collectSiz;

	public static int init(int num) {
		key[++cntn] = num;
		ls[cntn] = rs[cntn] = 0;
		alive[cntn] = true;
		aliveSiz[cntn] = 1;
		return cntn;
	}

	// 存活的节点的信息汇总
	public static void up(int i) {
		aliveSiz[i] = (alive[i] ? 1 : 0) + aliveSiz[ls[i]] + aliveSiz[rs[i]];
	}

	// 存活节点的多少来判断是否平衡
	public static boolean balance(int i) {
		return ALPHA * aliveSiz[i] >= Math.max(aliveSiz[ls[i]], aliveSiz[rs[i]]);
	}

	public static void inorder(int i) {
		// 整棵树上没有存活节点也跳过
		if (i != 0 && aliveSiz[i] != 0) {
			inorder(ls[i]);
			if (alive[i]) {
				collect[++collectSiz] = i;
			}
			inorder(rs[i]);
		}
	}

	public static int innerBuild(int l, int r) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		int h = collect[mid];
		ls[h] = innerBuild(l, mid - 1);
		rs[h] = innerBuild(mid + 1, r);
		up(h);
		return h;
	}

	public static int innerRebuild(int h) {
		if (top != 0) {
			collectSiz = 0;
			inorder(top);
			int newRoot = innerBuild(1, collectSiz);
			if (father == 0) {
				h = newRoot;
			} else if (side == 1) {
				ls[father] = newRoot;
			} else {
				rs[father] = newRoot;
			}
		}
		return h;
	}

	public static int innerInsert(int num, int i, int f, int s) {
		// 整棵树上没有存活节点，就算空树
		if (i == 0 || aliveSiz[i] == 0) {
			return init(num);
		}
		if (num <= key[i]) {
			ls[i] = innerInsert(num, ls[i], i, 1);
		} else {
			rs[i] = innerInsert(num, rs[i], i, 2);
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
		return i;
	}

	// 平衡树当前来到i号节点，把num这个数字插入
	// 返回头节点编号
	public static int innerInsert(int num, int i) {
		top = father = side = 0;
		i = innerInsert(num, i, 0, 0);
		i = innerRebuild(i);
		return i;
	}

	// 平衡树当前来到i号节点，返回<num的数字个数
	public static int innerSmall(int num, int i) {
		// 整棵树上没有存活节点，就算空树
		if (i == 0 || aliveSiz[i] == 0) {
			return 0;
		}
		if (num <= key[i]) {
			return innerSmall(num, ls[i]);
		} else {
			return aliveSiz[ls[i]] + (alive[i] ? 1 : 0) + innerSmall(num, rs[i]);
		}
	}

	// 平衡树当前来到i号节点，返回第index小的数字
	public static int innerIndex(int index, int i) {
		int lsiz = aliveSiz[ls[i]];
		if (index <= lsiz) {
			return innerIndex(index, ls[i]);
		}
		int cur = alive[i] ? 1 : 0;
		if (lsiz + cur < index) {
			return innerIndex(index - lsiz - cur, rs[i]);
		}
		return key[i];
	}

	// 平衡树当前来到i号节点，返回num的前驱
	public static int innerPre(int num, int i) {
		int kth = innerSmall(num, i) + 1;
		if (kth == 1) {
			return -INF;
		} else {
			return innerIndex(kth - 1, i);
		}
	}

	// 平衡树当前来到i号节点，返回num的后继
	public static int innerPost(int num, int i) {
		int k = innerSmall(num + 1, i);
		if (k == aliveSiz[i]) {
			return INF;
		} else {
			return innerIndex(k + 1, i);
		}
	}

	// 注意innerRemove方法
	// 因为替罪羊树会重构，所以值相同的一批节点，重构时假设选出的头为h
	// 那么这批节点，有可能在h的左侧，也有可能在h的右侧
	// 所以删除时，如果h已经被删，还要继续寻找其他key值相同的节点
	// 此时只根据key值的大小关系，方向无法确定是左还是右
	// 所以先求出目标的排名，再按排名删除，这样移动方向是确定的
	public static void innerRemove(int i, int f, int s, int rank) {
		int leftSize = aliveSiz[ls[i]];
		if (rank <= leftSize) {
			innerRemove(ls[i], i, 1, rank);
		} else {
			int cur = alive[i] ? 1 : 0;
			if (alive[i] && rank == leftSize + cur) {
				alive[i] = false;
			} else {
				innerRemove(rs[i], i, 2, rank - leftSize - cur);
			}
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
	}

	public static int innerRemove(int num, int i) {
		int rank1 = innerSmall(num, i) + 1;
		int rank2 = innerSmall(num + 1, i) + 1;
		if (rank1 != rank2) {
			top = father = side = 0;
			innerRemove(i, 0, 0, rank1);
			i = innerRebuild(i);
		}
		return i;
	}

	public static void add(int jobi, int jobv, int l, int r, int i) {
		root[i] = innerInsert(jobv, root[i]);
		if (l < r) {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				add(jobi, jobv, l, mid, i << 1);
			} else {
				add(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
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
			if (small(jobl, jobr, mid + 1, 1, n, 1) + 1 > jobk) {
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
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			add(i, arr[i], 1, n, 1);
		}
		for (int i = 1, op, x, y, z; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 3) {
				update(x, y, 1, n, 1);
				arr[x] = y;
			} else {
				z = in.nextInt();
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
