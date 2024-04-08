package class110;

// 线段树范围为1 ~ n时，需要几倍空间才够用？
public class HowManySpace {

	// 范围l~r，信息存在独立数组的i位置
	// 返回递归展开的过程中出现的最大编号
	public static int maxi(int l, int r, int i) {
		if (l == r) {
			return i;
		} else {
			int mid = (l + r) >> 1;
			return Math.max(maxi(l, mid, i << 1), maxi(mid + 1, r, i << 1 | 1));
		}
	}

	public static void main(String[] args) {
		int n = 10000;
		int a = 0;
		int b = 0;
		double t = 0;
		for (int i = 1; i <= n; i++) {
			int space = maxi(1, i, 1);
			double times = (double) space / (double) i;
			System.out.println("范围[1~" + i + "]，" + "需要空间" + space + "，倍数=" + times);
			if (times > t) {
				a = i;
				b = space;
				t = times;
			}
		}
		System.out.println("其中的最大倍数，范围[1~" + a + "]，" + "需要空间" + b + "，倍数=" + t);
	}

}
