package class041;

public class Code01_EqualProbabilityRandom {

	// 可以修改这个值
	// 让f函数以p的概率返回0，1-p的概率返回1
	public static final double p = 0.834;

	// 作为条件
	public static int f() {
		return Math.random() < p ? 0 : 1;
	}

	// 不管f函数以什么概率返回0和1
	// g函数都等概率返回0和1
	public static int g() {
		int first = 0;
		do {
			first = f();
		} while (first == f());
		return first;
	}

	public static void main(String[] args) {
		int[] cnts = new int[2];
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			cnts[g()]++;
		}
		System.out.println(cnts[0] + " , " + cnts[1]);
	}

}
