package class110;

// 线段树范围为1 ~ n时，需要几倍空间才够用？
public class HowManySpace {

	public static int maxi(int l, int r, int i) {
		if (l == r) {
			return i;
		} else {
			int mid = (l + r) / 2;
			return Math.max(maxi(l, mid, i << 1), maxi(mid + 1, r, i << 1 | 1));
		}
	}

	public static void main(String[] args) {
		int n = 999;
		double max = 0;
		int r = 0;
		for (int i = 1; i <= n; i++) {
			double cur = (double) maxi(1, i, 1) / (double) i;
			System.out.println("范围[1 ~ " + i + "]时，空间 / 范围 = " + cur);
			if (cur > max) {
				max = cur;
				r = i;
			}
		}
		System.out.println("其中的最大倍数，范围[1 ~ " + r + "]时，空间 / 范围 = " + max);
	}

}
