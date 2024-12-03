package class153;

// 提根操作的代码 + 长链高度变化的实验
// 分别去生成一字型长链和之字型长链
// 每一种长链都让最下方节点提根上去
// 然后看看长链的高度变化
public class ShowDetail {

	public static int MAXN = 100001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void rotate(int i) {
		int f = father[i], g = father[f], soni = lr(i), sonf = lr(f);
		if (soni == 1) {
			right[f] = left[i];
			if (right[f] != 0) {
				father[right[f]] = f;
			}
			left[i] = f;
		} else {
			left[f] = right[i];
			if (left[f] != 0) {
				father[left[f]] = f;
			}
			right[i] = f;
		}
		if (g != 0) {
			if (sonf == 1) {
				right[g] = i;
			} else {
				left[g] = i;
			}
		}
		father[f] = i;
		father[i] = g;
		up(f);
		up(i);
	}

	public static void splay(int i, int goal) {
		int f = father[i], g = father[f];
		while (f != goal) {
			if (g != goal) {
				if (lr(i) == lr(f)) {
					rotate(f);
				} else {
					rotate(i);
				}
			}
			rotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	public static int build1(int l, int r) {
		int h = cnt + 1;
		for (int i = l, last = 0; i <= r; i++, last = cnt) {
			key[++cnt] = i;
			father[cnt] = last;
			left[cnt] = right[cnt] = 0;
			size[cnt] = r - i + 1;
			if (last != 0) {
				right[last] = cnt;
			}
		}
		return h;
	}

	public static int build2(int l, int r, int fa) {
		if (l > r) {
			return 0;
		}
		key[++cnt] = l;
		father[cnt] = fa;
		left[cnt] = right[cnt] = 0;
		int h = cnt;
		if (l < r) {
			key[++cnt] = r;
			father[cnt] = h;
			left[cnt] = right[cnt] = 0;
			int c = cnt;
			right[h] = c;
			left[c] = build2(l + 1, r - 1, c);
			up(c);
		}
		up(h);
		return h;
	}

	public static int height(int i) {
		if (i == 0) {
			return 0;
		}
		return Math.max(height(left[i]), height(right[i])) + 1;
	}

	public static void main(String[] args) {
		System.out.println("构建一字型长链");
		System.out.println("最下方节点执行splay，观察高度变化");
		head = build1(1, 1000);
		System.out.println("splay之前的链长度 : " + height(head));
		splay(cnt, 0);
		System.out.println("splay之后的链长度 : " + height(head));

		System.out.println("==================");

		System.out.println("构建之字型长链");
		System.out.println("最下方节点执行splay，观察高度变化");
		head = build2(1, 1000, 0);
		System.out.println("splay之前的链长度 : " + height(head));
		splay(cnt, 0);
		System.out.println("splay之后的链长度 : " + height(head));
	}

}
