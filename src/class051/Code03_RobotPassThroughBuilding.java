package class051;

// 机器人跳跃问题
// 机器人正在玩一个古老的基于DOS的游戏
// 游戏中有N+1座建筑，从0到N编号，从左到右排列
// 编号为0的建筑高度为0个单位，编号为i的建筑的高度为H(i)个单位
// 起初机器人在编号为0的建筑处
// 每一步，它跳到下一个（右边）建筑。假设机器人在第k个建筑，且它现在的能量值是E
// 下一步它将跳到第个k+1建筑
// 它将会得到或者失去正比于与H(k+1)与E之差的能量
// 如果 H(k+1) > E 那么机器人就失去H(k+1)-E的能量值，否则它将得到E-H(k+1)的能量值
// 游戏目标是到达第个N建筑，在这个过程中，能量值不能为负数个单位
// 现在的问题是机器人以多少能量值开始游戏，才可以保证成功完成游戏
// 测试链接 : https://www.nowcoder.com/practice/7037a3d57bbd4336856b8e16a9cafd71
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_RobotPassThroughBuilding {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			int l = 0;
			int r = 0;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
				r = Math.max(r, arr[i]);
			}
			out.println(compute(l, r, r));
		}
		out.flush();
		out.close();
		br.close();
	}

	// [l,r]通关所需最小能量的范围，不停二分
	// max是所有建筑的最大高度
	// 时间复杂度O(n * log(max))，额外空间复杂度O(1)
	public static int compute(int l, int r, int max) {
		int m, ans = -1;
		while (l <= r) {
			// m中点，此时通关所需规定的初始能量
			m = l + ((r - l) >> 1);
			if (f(m, max)) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// 初始能量为energy，max是建筑的最大高度，返回能不能通关
	// 为什么要给定建筑的最大高度？
	public static boolean f(int energy, int max) {
		// 注意！
		// 如果给的能量值很大，那么后续能量增长将非常恐怖
		// 完全有可能超出long的范围
		// 所以要在遍历时，一定要加入energy >= max的判断
		// 一旦能量超过高度最大值，后面肯定通关了，可以提前返回了
		// 这里很阴
		for (int i = 1; i <= n; i++) {
			if (energy <= arr[i]) {
				energy -= arr[i] - energy;
			} else {
				energy += energy - arr[i];
			}
			if (energy >= max) {
				return true;
			}
			if (energy < 0) {
				return false;
			}
		}
		return true;
	}

}
