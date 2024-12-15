package class154;

// 派遣，java版
// 一共有n个忍者，每个忍者有上级编号、工资、能力，三个属性
// 输入保证，任何忍者的上级编号 < 这名忍者的编号，1号忍者是整棵忍者树的头
// 你一共有m的预算，可以在忍者树上随意选一棵子树，然后在这棵子树上挑选忍者
// 你选择某棵子树之后，不一定要选子树头的忍者，只要不超过m的预算，可以随意选择子树上的忍者
// 最终收益 = 雇佣人数 * 子树头忍者的能力，返回能取得的最大收益是多少
// 1 <= n <= 10^5           1 <= m <= 10^9
// 1 <= 每个忍者工资 <= m     1 <= 每个忍者领导力 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1552
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_Dispatch1 {

	public static int MAXN = 100001;

	public static int n, m;

	// 上级
	public static int[] leader = new int[MAXN];

	// 薪水
	public static long[] cost = new long[MAXN];

	// 能力
	public static long[] ability = new long[MAXN];

	// 左孩子
	public static int[] left = new int[MAXN];

	// 右孩子
	public static int[] right = new int[MAXN];

	// 距离
	public static int[] dist = new int[MAXN];

	// 寻找堆顶需要
	public static int[] father = new int[MAXN];

	// 堆的大小
	public static int[] size = new int[MAXN];

	// 堆的费用和
	public static long[] sum = new long[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= n; i++) {
			left[i] = right[i] = dist[i] = 0;
			size[i] = 1;
			sum[i] = cost[i];
			father[i] = i;
		}
	}

	public static int find(int i) {
		father[i] = father[i] == i ? i : find(father[i]);
		return father[i];
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		// 维护大根堆
		if (cost[i] < cost[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		father[left[i]] = father[right[i]] = i;
		return i;
	}

	public static int pop(int i) {
		father[left[i]] = left[i];
		father[right[i]] = right[i];
		father[i] = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		return father[i];
	}

	public static long compute() {
		long ans = 0;
		int p, psize, h, hsize;
		long hsum, psum;
		for (int i = n; i >= 1; i--) {
			h = find(i);
			hsize = size[h];
			hsum = sum[h];
			while (hsum > m) {
				pop(h);
				hsize--;
				hsum -= cost[h];
				h = find(i);
			}
			ans = Math.max(ans, (long) hsize * ability[i]);
			if (i > 1) {
				p = find(leader[i]);
				psize = size[p];
				psum = sum[p];
				father[p] = father[h] = merge(p, h);
				size[father[p]] = psize + hsize;
				sum[father[p]] = psum + hsum;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			leader[i] = (int) in.nval;
			in.nextToken();
			cost[i] = (int) in.nval;
			in.nextToken();
			ability[i] = (int) in.nval;
		}
		prepare();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
