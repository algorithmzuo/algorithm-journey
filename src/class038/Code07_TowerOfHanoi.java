package class038;

// 打印n层汉诺塔问题的最优移动轨迹
public class Code07_TowerOfHanoi {

	public static void hanoi(int n) {
		if (n > 0) {
			f(n, "左", "右", "中");
		}
	}

	public static void f(int i, String from, String to, String other) {
		if (i == 1) {
			System.out.println("移动圆盘 1 从 " + from + " 到 " + to);
		} else {
			f(i - 1, from, other, to);
			System.out.println("移动圆盘 " + i + " 从 " + from + " 到 " + to);
			f(i - 1, other, to, from);
		}
	}

	public static void main(String[] args) {
		int n = 3;
		hanoi(n);
	}

}
