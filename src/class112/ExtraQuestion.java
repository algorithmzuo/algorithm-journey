package class112;

// 赠送一道课上没有讲的附加练习题
// 色板游戏
// 一共有L个色板，编号1~L，一开始所有色板都是1号颜色
// 一共有T种颜色，编号1~T，可以往色板上涂色
// 一共有O次操作，操作类型有如下两种
// 操作 C A B C : A~B范围的色板都涂上C颜色 
// 操作 P A B   : 查询A~B范围的色板一共有几种颜色
// L <= 10^5, T <= 30, O <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1558
// 解法 : 
// 数据很特殊，颜色种类不超过30
// 可以用位信息来表示线段树范围上的颜色状况
// 那么，父范围的颜色状况 = 左范围的颜色状况 | 右范围的颜色状况
// 除此之外没啥难点了，提交时把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class ExtraQuestion {

	public static int MAXN = 100001;

	// 线段树范围上的颜色状况
	public static int[] color = new int[MAXN << 2];

	// 懒更新信息，范围上重置成了什么颜色
	public static int[] change = new int[MAXN << 2];

	// 懒更新信息，范围上是否有重置任务
	public static boolean[] update = new boolean[MAXN << 2];

	public static void up(int i) {
		color[i] = color[i << 1] | color[i << 1 | 1];
	}

	public static void down(int i) {
		if (update[i]) {
			lazy(i << 1, change[i]);
			lazy(i << 1 | 1, change[i]);
			update[i] = false;
		}
	}

	// 整个范围的色板重置成v颜色的懒更新处理
	public static void lazy(int i, int v) {
		color[i] = 1 << v;
		change[i] = v;
		update[i] = true;
	}

	public static void build(int l, int r, int i) {
		if (l < r) {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
		// 一开始所有色板都是1号颜色
		// 所以状态为0...0010 = 2
		color[i] = 2;
		change[i] = 0;
		update[i] = false;
	}

	// 范围重置颜色
	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// 返回l..r范围上的颜色种类
	public static int query(int l, int r, int n) {
		int status = query(l, r, 1, n, 1);
		return cntOnes(status);
	}

	// 返回l..r范围上的颜色状况
	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return color[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int status = 0;
		if (jobl <= mid) {
			status |= query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			status |= query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return status;
	}

	// 返回n的二进制中有几个1
	// 该实现讲解031的题目6讲过了
	// 你也可以用更简单的写法
	public static int cntOnes(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		in.nextToken();
		int m = (int) in.nval;
		build(1, n, 1);
		String op;
		for (int i = 1, a, b, c, tmp; i <= m; i++) {
			in.nextToken();
			op = in.sval;
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			// 注意题目可能有a>b的情况需要交换
			if (a > b) {
				tmp = a;
				a = b;
				b = tmp;
			}
			if (op.equals("C")) {
				in.nextToken();
				c = (int) in.nval;
				update(a, b, c, 1, n, 1);
			} else {
				out.println(query(a, b, n));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
