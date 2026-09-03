package class150;

// 替罪羊树更好的实现，java版
// 这个文件课上没有讲
// 替罪羊树不进行词频压缩的版本
// 数据经过加强
// 注释比较清楚，结合课上的讲述，一看就会
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ScapeGoatBetter1 {

	public static int MAXN = 2000001;

	// 替罪羊树的节点计数
	public static int cntn;

	// 替罪羊树的根节点
	public static int root;

	// 节点的key值
	public static int[] key = new int[MAXN];

	// 节点的左儿子
	public static int[] ls = new int[MAXN];

	// 节点的右儿子
	public static int[] rs = new int[MAXN];

	// 节点是否存活，删掉就是不存活，否则就是存活
	public static boolean[] alive = new boolean[MAXN];

	// 子树上存活的节点数量
	public static int[] aliveSiz = new int[MAXN];

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

	public static int build(int l, int r) {
		if (l > r) {
			return 0;
		}
		int m = (l + r) / 2;
		int h = collect[m];
		ls[h] = build(l, m - 1);
		rs[h] = build(m + 1, r);
		up(h);
		return h;
	}

	public static void rebuild() {
		if (top != 0) {
			collectSiz = 0;
			inorder(top);
			int newRoot = build(1, collectSiz);
			if (father == 0) {
				root = newRoot;
			} else if (side == 1) {
				ls[father] = newRoot;
			} else {
				rs[father] = newRoot;
			}
		}
	}

	// 带删除的替罪羊树常规设计
	// 1，allSiz代表总节点数，aliveSiz代表存活节点数
	// 2，某节点的左子树或右子树大小高于平衡因子，触发重构
	// 3，某节点的子树中，存活节点的比例低于设定阈值，触发重构
	// 4，重构时，因为allSiz需要正确维护，所以顺着重构子树向上的路径，沿途节点都要更新信息
	//
	// 我的设计
	// 1，只有aliveSiz，也就是存活节点数，新增节点 aliveSiz + 1，删除节点 aliveSiz - 1
	// 2，某节点的左子树或右子树，只关注存活节点数，一旦高于平衡因子，触发重构
	// 3，新增和删除都可能触发重构，但是重构时，树上每个节点的aliveSiz信息已经更新正确
	// 4，所以不需要顺着重构子树向上的路径，遍历沿途节点，不平衡的子树完成重构即可
	// 5，均摊的复杂度依然是O(log n)
	//
	// 为什么复杂度可以得到保证？
	// 即使节点删除得非常均匀，某些子树一直没有触发重构，也不会影响复杂度
	// 因为每次操作结束后，所有包含存活节点的子树都满足上述重量平衡条件
	// 沿一条有效路径向下，存活节点数每层至多变为上一层的ALPHA倍
	// 所以有效访问高度始终是O(log n)，其中n是当前存活节点数
	// 还有aliveSiz[i]为0的子树直接视为空树，插入和查询都不会进入
	public static boolean balance(int i) {
		return ALPHA * aliveSiz[i] >= Math.max(aliveSiz[ls[i]], aliveSiz[rs[i]]);
	}

	// 返回头节点编号
	public static int add(int i, int f, int s, int num) {
		// 整棵树上没有存活节点，就算空树
		if (i == 0 || aliveSiz[i] == 0) {
			return init(num);
		}
		if (num <= key[i]) {
			ls[i] = add(ls[i], i, 1, num);
		} else {
			rs[i] = add(rs[i], i, 2, num);
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
		return i;
	}

	public static void add(int num) {
		top = father = side = 0;
		root = add(root, 0, 0, num);
		rebuild();
	}

	public static int small(int i, int num) {
		// 整棵树上没有存活节点，就算空树
		if (i == 0 || aliveSiz[i] == 0) {
			return 0;
		}
		if (num <= key[i]) {
			return small(ls[i], num);
		} else {
			return aliveSiz[ls[i]] + (alive[i] ? 1 : 0) + small(rs[i], num);
		}
	}

	public static int rank(int num) {
		return small(root, num) + 1;
	}

	public static int index(int i, int x) {
		if (x <= aliveSiz[ls[i]]) {
			return index(ls[i], x);
		} else {
			int less = aliveSiz[ls[i]] + (alive[i] ? 1 : 0);
			if (less < x) {
				return index(rs[i], x - less);
			}
		}
		return key[i];
	}

	public static int index(int x) {
		return index(root, x);
	}

	public static int pre(int num) {
		int kth = rank(num);
		if (kth == 1) {
			return Integer.MIN_VALUE;
		} else {
			return index(kth - 1);
		}
	}

	public static int post(int num) {
		int kth = rank(num + 1);
		if (kth == aliveSiz[root] + 1) {
			return Integer.MAX_VALUE;
		} else {
			return index(kth);
		}
	}

	// 注意remove方法
	// 因为替罪羊树会重构，所以值相同的一批节点，重构时假设选出的头为h
	// 那么这批节点，有可能在h的左侧，也有可能在h的右侧
	// 所以删除时，如果h已经被删，还要继续寻找其他key值相同的节点
	// 此时只根据key值的大小关系，方向无法确定是左还是右
	// 所以先求出目标的排名，再按排名删除，这样移动方向是确定的
	public static void remove(int i, int f, int s, int rank) {
		int lsiz = aliveSiz[ls[i]];
		if (rank <= lsiz) {
			remove(ls[i], i, 1, rank);
		} else {
			int cur = alive[i] ? 1 : 0;
			if (alive[i] && rank == lsiz + cur) {
				alive[i] = false;
			} else {
				remove(rs[i], i, 2, rank - lsiz - cur);
			}
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
	}

	public static void remove(int num) {
		int rank1 = rank(num);
		int rank2 = rank(num + 1);
		if (rank1 != rank2) {
			top = father = side = 0;
			remove(root, 0, 0, rank1);
			rebuild();
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		int m = in.nextInt();
		for (int i = 1, num; i <= n; i++) {
			num = in.nextInt();
			add(num);
		}
		int lastAns = 0;
		int ans = 0;
		for (int i = 1, op, x; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			x ^= lastAns;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				lastAns = rank(x);
				ans ^= lastAns;
			} else if (op == 4) {
				lastAns = index(x);
				ans ^= lastAns;
			} else if (op == 5) {
				lastAns = pre(x);
				ans ^= lastAns;
			} else {
				lastAns = post(x);
				ans ^= lastAns;
			}
		}
		out.println(ans);
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
