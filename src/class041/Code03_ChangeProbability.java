package class041;

public class Code03_ChangeProbability {

	public static double random1() {
		return Math.random();
	}

	public static double random2() {
		return Math.max(Math.random(), Math.random());
	}

	public static void main(String[] args) {
		double x = 0.7;
		int times = 100000;
		int cnt1 = 0;
		int cnt2 = 0;
		for (int i = 0; i < times; i++) {
			if (random1() < x) {
				cnt1++;
			}
			if (random2() < x) {
				cnt2++;
			}
		}
		System.out.println("random1()得到 [0," + x + ") 范围数字的概率 : " + (double) cnt1 / (double) times);
		System.out.println("random2()得到 [0," + x + ") 范围数字的概率 : " + (double) cnt2 / (double) times);
	}

}
