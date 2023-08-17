package class038;

// 打印n层汉诺塔问题的移动轨迹
// 测试链接 : https://www.nowcoder.com/practice/84ce91c6099a45dc869355fa1c4f589d
public class Code07_TowerOfHanoi {

	public static void hanoi(int n) {
		if (n > 0) {
			f(n, "左", "右", "中");
		}
	}

	public static void f(int i, String from, String to, String other) {
		if (i == 1) {
			System.out.println("移动 圆盘1 从 " + from + " 到 " + to);
		} else {
			f(i - 1, from, other, to);
			System.out.println("移动 圆盘" + i + " 从 " + from + " 到 " + to);
			f(i - 1, other, to, from);
		}
	}

	public static void main(String[] args) {
		int n = 3;
		hanoi(n);
	}

}
