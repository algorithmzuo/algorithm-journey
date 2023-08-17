package class043;

// 有装下8个苹果的袋子、装下6个苹果的袋子，一定要保证买苹果时所有使用的袋子都装满
// 对于无法装满所有袋子的方案不予考虑，给定n个苹果，返回至少要多少个袋子
// 如果不存在装满的方案返回-1
public class Code01_AppleMinBags {

	public static int minBags1(int apple) {
		if (apple < 0) {
			return -1;
		}
		int bag8 = (apple >> 3);
		int rest = apple - (bag8 << 3);
		while (bag8 >= 0) {
			if (rest % 6 == 0) {
				return bag8 + (rest / 6);
			} else {
				bag8--;
				rest += 8;
			}
		}
		return -1;
	}

	public static int minBags2(int apple) {
		if ((apple & 1) != 0) {
			return -1;
		}
		if (apple < 18) {
			return apple == 0 ? 0
					: (apple == 6 || apple == 8) ? 1 : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
		}
		return (apple - 18) / 8 + 3;
	}

	public static void main(String[] args) {
		for (int apple = 1; apple < 200; apple++) {
			System.out.println(apple + " : " + minBags1(apple));
		}
	}

}
