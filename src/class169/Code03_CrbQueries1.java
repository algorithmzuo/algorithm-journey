package class169;

// 动态查询第k小，java版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5412
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

public class Code03_CrbQueries1 {

	public static int MAXN = 100001;

	public static int n;

	public static int[] tree = new int[MAXN];

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

}
