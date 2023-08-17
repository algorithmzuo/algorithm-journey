package class041;

public class Code03_RandomToRandom {

	// 作为条件
	public static class Tool {
		public final int a;
		public final int b;

		public Tool(int small, int big) {
			a = small;
			b = big;
		}

		public int random() {
			return a + (int) (Math.random() * (b - a + 1));
		}
	}

	// 利用Tool类的随机行为实现RandomBox的随机行为
	// 不能再借助任何别的随机行为
	public static class RandomBox {
		private final Tool tool;
		private final int c;
		private final int d;
		private final int range;
		private final int high;

		public RandomBox(Tool t, int small, int big) {
			tool = t;
			c = small;
			d = big;
			range = d - c;
			int need = 1;
			while ((1 << need) - 1 < range) {
				need++;
			}
			high = need - 1;
		}

		private int r01() {
			boolean sizeOdd = ((tool.b - tool.a + 1) & 1) == 1;
			int mid = (tool.a + tool.b) / 2;
			int decide;
			do {
				decide = tool.random();
			} while (sizeOdd && decide == mid);
			return decide <= mid ? 0 : 1;
		}

		public int random() {
			int decide;
			do {
				decide = 0;
				for (int i = high; i >= 0; i--) {
					decide |= r01() << i;
				}
			} while (decide > range);
			return c + decide;
		}

	}

	public static void main(String[] args) {
		int a = 3;
		int b = 6;
		// tool一旦建立就是唯一的随机源
		Tool tool = new Tool(a, b);
		int c = 8;
		int d = 16;
		// box初始化的时候指定好唯一的随机源
		RandomBox box = new RandomBox(tool, c, d);
		int testTimes = 10000;
		int[] cnts = new int[d + 1];
		for (int i = 0; i < testTimes; i++) {
			cnts[box.random()]++;
		}
		for (int i = 0; i <= d; i++) {
			System.out.println(i + " 出现次数 : " + cnts[i]);
		}
	}

}
