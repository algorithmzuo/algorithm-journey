package class043;

// 现在有一个打怪类型的游戏，这个游戏是这样的，你有n个技能
// 每一个技能会有一个伤害，
// 同时若怪物小于等于一定的血量，则该技能可能造成双倍伤害
// 每一个技能最多只能释放一次，已知怪物有m点血量
// 现在想问你最少用几个技能能消灭掉他(血量小于等于0)
// 技能的数量是n，怪物的血量是m
// i号技能的伤害是x[i]，i号技能触发双倍伤害的血量最小值是y[i]
// 1 <= n <= 10
// 1 <= m、x[i]、y[i] <= 10^6
// 测试链接 : https://www.nowcoder.com/practice/d88ef50f8dab4850be8cd4b95514bbbd
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的所有代码，并把主类名改成"Main"
// 可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_KillMonsterEverySkillUseOnce {

	public static int MAXN = 11;

	public static int[] kill = new int[MAXN];

	public static int[] blood = new int[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int t = (int) in.nval;
			for (int i = 0; i < t; i++) {
				in.nextToken();
				int n = (int) in.nval;
				in.nextToken();
				int m = (int) in.nval;
				for (int j = 0; j < n; j++) {
					in.nextToken();
					kill[j] = (int) in.nval;
					in.nextToken();
					blood[j] = (int) in.nval;
				}
				int ans = f(n, 0, m);
				out.println(ans == Integer.MAX_VALUE ? -1 : ans);
			}
		}
		out.flush();
		br.close();
		out.close();
	}

	// kill[i]、blood[i]
	// n : 一共几个技能
	// i : 当前来到了第几号技能
	// r : 怪兽目前的剩余血量
	public static int f(int n, int i, int r) {
		if (r <= 0) {
			// 之前的决策已经让怪兽挂了！返回使用了多少个节能
			return i;
		}
		// r > 0
		if (i == n) {
			// 无效，之前的决策无效
			return Integer.MAX_VALUE;
		}
		// 返回至少需要几个技能可以将怪兽杀死
		int ans = Integer.MAX_VALUE;
		for (int j = i; j < n; j++) {
			swap(i, j);
			ans = Math.min(ans, f(n, i + 1, r - (r > blood[i] ? kill[i] : kill[i] * 2)));
			swap(i, j);
		}
		return ans;
	}

	// i号技能和j号技能，参数交换
	// j号技能要来到i位置，试一下
	public static void swap(int i, int j) {
		int tmp = kill[i];
		kill[i] = kill[j];
		kill[j] = tmp;
		tmp = blood[i];
		blood[i] = blood[j];
		blood[j] = tmp;
	}

}