package class113;

// 地道相连的房子
// 有n个房子排成一排，编号1~n，一开始每相邻的两个房子之间都有地道
// 实现如下三个操作
// 操作 D x : 把x号房子摧毁，该房子附近的地道也一并摧毁
// 操作 R   : 恢复上次摧毁的房子，该房子附近的地道一并恢复
// 操作 Q x : 查询x号房子能到达的房子数量，包括x号房子自身
// 测试链接 : https://www.luogu.com.cn/problem/P1503
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_TunnelWarfare {

	public static int MAXN = 50001;

	// 连续1的最长前缀长度
	public static int[] pre = new int[MAXN << 2];

	// 连续1的最长后缀长度
	public static int[] suf = new int[MAXN << 2];

	// 摧毁的房屋编号入栈，以便执行恢复操作
	public static int[] stack = new int[MAXN];

	public static void up(int l, int r, int i) {
		pre[i] = pre[i << 1];
		suf[i] = suf[i << 1 | 1];
		int mid = (l + r) >> 1;
		if (pre[i << 1] == mid - l + 1) {
			pre[i] += pre[i << 1 | 1];
		}
		if (suf[i << 1 | 1] == r - mid) {
			suf[i] += suf[i << 1];
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			pre[i] = suf[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(l, r, i);
		}
	}

	public static void update(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			pre[i] = suf[i] = jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(l, r, i);
		}
	}

	// 已知jobi在l...r范围上
	// 返回jobi往两侧扩展出的最大长度
	// 递归需要遵循的潜台词 : 从jobi往两侧扩展，一定无法扩展到l...r范围之外！
	public static int query(int jobi, int l, int r, int i) {
		if (l == r) {
			return pre[i];
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				if (jobi > mid - suf[i << 1]) {
					return suf[i << 1] + pre[i << 1 | 1];
				} else {
					return query(jobi, l, mid, i << 1);
				}
			} else {
				if (mid + pre[i << 1 | 1] >= jobi) {
					return suf[i << 1] + pre[i << 1 | 1];
				} else {
					return query(jobi, mid + 1, r, i << 1 | 1);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			build(1, n, 1);
			String op;
			int stackSize = 0;
			for (int i = 1, x; i <= m; i++) {
				in.nextToken();
				op = in.sval;
				if (op.equals("D")) {
					in.nextToken();
					x = (int) in.nval;
					update(x, 0, 1, n, 1);
					stack[stackSize++] = x;
				} else if (op.equals("R")) {
					update(stack[--stackSize], 1, 1, n, 1);
				} else {
					in.nextToken();
					x = (int) in.nval;
					out.println(query(x, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
