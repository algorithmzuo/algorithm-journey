package class042;

// 草一共有n的重量，两只牛轮流吃草，A牛先吃，B牛后吃
// 每只牛在自己的回合，吃草的重量必须是4的幂，1、4、16、64....
// 谁在自己的回合正好把草吃完谁赢，根据输入的n，返回谁赢
public class Code02_EatGrass {

	// "A"  "B"
	public static String win1(int n) {
		return f(n, "A");
	}

	// rest : 还剩多少草
	// cur  : 当前选手的名字
	// 返回  : 还剩rest份草，当前选手是cur，按照题目说的，返回最终谁赢 
	public static String f(int rest, String cur) {
		String enemy = cur.equals("A") ? "B" : "A";
		if (rest < 5) {
			return (rest == 0 || rest == 2) ? enemy : cur;
		}
		// rest >= 5
		// rest == 100
		// cur : 
		// 1) 1 ->99,enemy ....
		// 2) 4 ->96,enemy ....
		// 3) 16 -> 84,enemy ....
		// 4) 64 -> 36,enemy ...
		// 没有cur赢的分支，enemy赢
		int pick = 1;
		while (pick <= rest) {
			if (f(rest - pick, enemy).equals(cur)) {
				return cur;
			}
			pick *= 4;
		}
		return enemy;
	}

	public static String win2(int n) {
		if (n % 5 == 0 || n % 5 == 2) {
			return "B";
		} else {
			return "A";
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 50; i++) {
			System.out.println(i + " : " + win1(i));
		}
	}

}
