package class113;

// 旅馆
// 一共有n个房间，编号1~n，一开始都是空房
// 实现如下两种操作，会一共调用m次
// 操作 1 x   : 找到至少有连续x个空房间的区域，返回最左编号
//              如果有多个满足条件的区域，返回其中最左区域的最左编号
//              如果找不到打印0，并且不办理入住
//              如果找到了打印最左编号，并且从最左编号开始办理x个人的入住
// 操作 2 x y : 从x号房间开始往下数y个房间，一律清空
// 操作1有打印操作，操作2没有
// 1 <= n 、m <= 50000
// 测试链接 : https://www.luogu.com.cn/problem/P2894
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_Hotel {

	public static int MAXN = 50001;

	// 连续空房最长子串长度
	public static int[] len = new int[MAXN << 2];

	// 连续空房最长前缀长度
	public static int[] pre = new int[MAXN << 2];

	// 连续空房最长后缀长度
	public static int[] suf = new int[MAXN << 2];

	// 懒更新信息，范围上所有数字被重置成了什么
	public static int[] change = new int[MAXN << 2];

	// 懒更新信息，范围上有没有重置任务
	public static boolean[] update = new boolean[MAXN << 2];

	public static void up(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		len[i] = Math.max(Math.max(len[l], len[r]), suf[l] + pre[r]);
		pre[i] = len[l] < ln ? pre[l] : (pre[l] + pre[r]);
		suf[i] = len[r] < rn ? suf[r] : (suf[l] + suf[r]);
	}

	public static void down(int i, int ln, int rn) {
		if (update[i]) {
			lazy(i << 1, change[i], ln);
			lazy(i << 1 | 1, change[i], rn);
			update[i] = false;
		}
	}

	public static void lazy(int i, int v, int n) {
		len[i] = pre[i] = suf[i] = v == 0 ? n : 0;
		change[i] = v;
		update[i] = true;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			len[i] = pre[i] = suf[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i, mid - l + 1, r - mid);
		}
		update[i] = false;
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i, mid - l + 1, r - mid);
		}
	}

	// 在l..r范围上，在满足空房长度>=x的情况下，返回尽量靠左的开头位置
	// 递归需要遵循的潜台词 : l..r范围上一定存在连续空房长度>=x的区域
	public static int queryLeft(int x, int l, int r, int i) {
		if (l == r) {
			return l;
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			// 最先查左边
			if (len[i << 1] >= x) {
				return queryLeft(x, l, mid, i << 1);
			}
			// 然后查中间向两边扩展的可能区域
			if (suf[i << 1] + pre[i << 1 | 1] >= x) {
				return mid - suf[i << 1] + 1;
			}
			// 前面都没有再最后查右边
			return queryLeft(x, mid + 1, r, i << 1 | 1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		build(1, n, 1);
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, op, x, y, left; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				x = (int) in.nval;
				if (len[1] < x) {
					left = 0;
				} else {
					left = queryLeft(x, 1, n, 1);
					update(left, left + x - 1, 1, 1, n, 1);
				}
				out.println(left);
			} else {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				update(x, Math.min(x + y - 1, n), 0, 1, n, 1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
