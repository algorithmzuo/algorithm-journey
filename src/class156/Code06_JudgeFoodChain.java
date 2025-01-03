package class156;

// 甄别食物链
// 一共有n只动物，编号1 ~ n，每只动物的类别未知，但一定是A、B、C中的一种
// 这三种类别是轮流捕食的关系，A吃B、B吃C、C吃A
// 一共有k句话，希望你判断哪些话是假话，每句话是如下两类句子中的一类
// 1 X Y : 第X只动物和第Y只动物是同类
// 2 X Y : 第X只动物吃第Y只动物
// 假话的标准如下
// 当前的话与前面的某些真话冲突，视为假话
// 当前的话提到的X和Y，有任何一个大于n，视为假话
// 当前的话如果关于吃，又有X==Y，视为假话
// 返回k句话中，假话的数量
// 1 <= n <= 5 * 10^4    1 <= k <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2024
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_JudgeFoodChain {

	public static int MAXN = 50001;

	public static int n, k, ans;

	public static int[] father = new int[MAXN];

	public static int[] relation = new int[MAXN];

	public static void prepare() {
		ans = 0;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			relation[i] = 0;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			int tmp = father[i];
			father[i] = find(tmp);
			relation[i] = (relation[i] + relation[tmp]) % 3;
		}
		return father[i];
	}

	public static void union(int op, int l, int r) {
		int lf = find(l), rf = find(r), v = op == 1 ? 0 : 1;
		if (lf != rf) {
			father[lf] = rf;
			relation[lf] = (relation[r] - relation[l] + v + 3) % 3;
		}
	}

	public static boolean check(int op, int l, int r) {
		if (l > n || r > n || (op == 2 && l == r)) {
			return false;
		}
		if (find(l) == find(r)) {
			if (op == 1) {
				if (relation[l] != relation[r]) {
					return false;
				}
			} else {
				if ((relation[l] - relation[r] + 3) % 3 != 1) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		prepare();
		for (int i = 1, op, l, r; i <= k; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			l = (int) in.nval;
			in.nextToken();
			r = (int) in.nval;
			if (!check(op, l, r)) {
				ans++;
			} else {
				union(op, l, r);
			}
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
