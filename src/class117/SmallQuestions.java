package class117;

public class SmallQuestions {

	// 给定一个正数x
	// 已知x一定可以用m个二进制位表示
	// 从高位到低位打印x每一位的状态
	public static void show1(int x, int m) {
		for (int p = m - 1, t = x; p >= 0; p--) {
			if (1 << p <= t) {
				t -= 1 << p;
				System.out.println(x + "的第" + p + "位是1");
			} else {
				System.out.println(x + "的第" + p + "位是0");
			}
		}
	}

	// 给定一个正数x
	// 打印<=x最大的2的幂
	// 到底是2的几次方
	public static void show2(int x) {
		int power = 0;
		// 以下注释掉的写法不对，没有考虑溢出
//		while ((1 << power) <= x) {
//			power++;
//		}
//		power--;
		// 防止溢出的写法
		while ((1 << power) <= (x >> 1)) {
			power++;
		}
		System.out.println("<=" + x + "最大的2的幂，是2的" + power + "次方");
	}

	public static void main(String[] args) {
		// 需要保证x一定可以用m个二进制位表示
		int x = 100;
		int m = 7;
		show1(x, m);

		x = 2000000000;
		show2(x);
	}

}
