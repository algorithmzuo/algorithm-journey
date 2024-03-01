package class107;

// 蓄水池采样
// 假设有一个不停吐出球的机器，每次吐出1号球、2号球、3号球...
// 有一个袋子只能装下10个球，每次机器吐出的球，要么放入袋子，要么永远扔掉
// 如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
public class Code01_ReservoirSampling {

	public static class Pool {

		private int size;

		public int[] bag;

		public Pool(int s) {
			size = s;
			bag = new int[s];
		}

		private boolean pick(int i) {
			return (int) (Math.random() * i) < size;
		}

		private int where() {
			return (int) (Math.random() * size);
		}

		public void enter(int i) {
			if (i <= size) {
				bag[i - 1] = i;
			} else {
				if (pick(i)) {
					bag[where()] = i;
				}
			}
		}

		public int[] getBag() {
			return bag;
		}

	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int n = 37; // 一共吐出多少球
		int m = 10; // 袋子大小多少
		int testTimes = 10000; // 进行多少次实验
		int[] cnt = new int[n + 1];
		for (int k = 0; k < testTimes; k++) {
			Pool pool = new Pool(m);
			for (int i = 1; i <= n; i++) {
				pool.enter(i);
			}
			int[] bag = pool.getBag();
			for (int num : bag) {
				cnt[num]++;
			}
		}
		System.out.println("机器吐出到" + n + "号球, " + "袋子大小为" + m);
		System.out.println("一共测试" + testTimes + "次");
		for (int i = 1; i <= n; i++) {
			System.out.println(i + "被选中次数 : " + cnt[i] + ", 被选中概率 : " + (double) cnt[i] / testTimes);
		}
		System.out.println("测试结束");
	}

}
