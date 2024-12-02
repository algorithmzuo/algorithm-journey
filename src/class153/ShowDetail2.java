package class153;

// 底层节点提根操作后子树大小变化的实验
// 分别去生成一字型长链和之字型长链
// 每一种长链都让最下方节点提根上去
// 然后看看每个节点子树大小的变化并统计数量
public class ShowDetail2 {

	public static int MAXN = 100001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] before = new int[MAXN];

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

	public static void experiment(int top, int bottom) {
		for (int i = top; i <= bottom; i++) {
			before[i] = size[i];
		}
		int all = bottom - top + 1;
		splay(bottom, 0);
		int half = 0;
		int beyond = 0;
		int other = 0;
		for (int i = top; i <= bottom; i++) {
			if (size[i] <= before[i] / 2) {
				half++;
			} else if (size[i] > before[i]) {
				beyond++;
			} else {
				other++;
			}
		}
		System.out.println("子树总数 : " + all);
		System.out.println("子树大小至少减半的数量 : " + half);
		System.out.println("子树大小得到增长的数量 : " + beyond);
		System.out.println("其他子树的数量 : " + other);
	}

	public static void main(String[] args) {
		System.out.println("构建一字型长链");
		head = build1(1, 1000);
		System.out.println("实验开始");
		experiment(head, cnt);
		System.out.println("实验结束");

		System.out.println();

		System.out.println("构建之字型长链");
		System.out.println("实验开始");
		head = build2(1, 1000, 0);
		experiment(head, cnt);
		System.out.println("实验结束");
	}

}
