package class129;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

// 寻找最近和次近
// 给定一个长度为n的数组arr，下标1 ~ n范围，数组无重复值
// 对i位置的数字x来说，只关注x右侧的数字，和x的差值绝对值越小就越近
// 距离为差值绝对值，如果距离一样，数值越小的越近
// 数值 : 3 5 7 1
// 下标 : 1 2 3 4
// 对1位置的数字3来说，第一近是4位置的1，距离为2；第二近是2位置的5，距离为2
// 每个位置的数字都求第一近的位置及其距离、第二近的位置及其距离
// 分别用to1、dist1、to2、dist2数组表示，0表示不存在
// 比如上面的例子，生成的to1、dist1、to2、dist2分别为
// to1   = { 4, 3, 4, 0 }
// dist1 = { 2, 2, 6, 0 }
// to2   = { 2, 4, 0, 0 }
// dist2 = { 2, 4, 0, 0 }
// 提供有序表的版本，做对数器验证
// 本题完全是为了题目5做准备，很有意思的题，但是和倍增优化无关
// 单纯展示一下，数组实现的双向链表，如何完成该功能
public class Code04_FindNear {

	public static int MAXN = 10001;

	// 数组
	public static int[] arr = new int[MAXN];

	// 数组长度
	public static int n;

	// 如下四个数组用来做结果数组，near1、near2方法都需要
	public static int[] to1 = new int[MAXN];

	public static int[] dist1 = new int[MAXN];

	public static int[] to2 = new int[MAXN];

	public static int[] dist2 = new int[MAXN];

	// 如下四个数组只有near2方法都需要
	public static int[][] rank = new int[MAXN][2];

	public static int[] last = new int[MAXN];

	public static int[] next = new int[MAXN];

	// 如下四个数组用来做备份，测试时需要
	public static int[] a = new int[MAXN];

	public static int[] b = new int[MAXN];

	public static int[] c = new int[MAXN];

	public static int[] d = new int[MAXN];

	// 有序表实现
	public static void near1() {
		TreeSet<int[]> set = new TreeSet<>((a, b) -> a[1] - b[1]);
		for (int i = n; i >= 1; i--) {
			to1[i] = 0;
			dist1[i] = 0;
			to2[i] = 0;
			dist2[i] = 0;
			int[] cur = new int[] { i, arr[i] };
			int[] p1 = set.floor(cur);
			int[] p2 = p1 != null ? set.floor(new int[] { p1[0], p1[1] - 1 }) : null;
			int[] p3 = set.ceiling(cur);
			int[] p4 = p3 != null ? set.ceiling(new int[] { p3[0], p3[1] + 1 }) : null;
			update(i, p1 != null ? p1[0] : 0);
			update(i, p2 != null ? p2[0] : 0);
			update(i, p3 != null ? p3[0] : 0);
			update(i, p4 != null ? p4[0] : 0);
			set.add(cur);
		}
	}

	// i位置的右边是r位置
	// r的出现看看能不能更新最近或者次近
	// 如果r==0则不更新
	public static void update(int i, int r) {
		if (r == 0) {
			return;
		}
		int d = Math.abs(arr[i] - arr[r]);
		if (better(to1[i], dist1[i], r, d)) {
			to2[i] = to1[i];
			dist2[i] = dist1[i];
			to1[i] = r;
			dist1[i] = d;
		} else if (better(to2[i], dist2[i], r, d)) {
			to2[i] = r;
			dist2[i] = d;
		}
	}

	// 返回后者(p2,d2)是否比前者(p1,d1)更近
	public static boolean better(int r1, int d1, int r2, int d2) {
		if (r1 == 0) {
			return true;
		}
		return d2 < d1 || (d2 == d1 && arr[r2] < arr[r1]);
	}

	// 双向链表(自己用数组手搓)实现
	public static void near2() {
		for (int i = 1; i <= n; i++) {
			rank[i][0] = i;
			rank[i][1] = arr[i];
		}
		Arrays.sort(rank, 1, n + 1, (a, b) -> a[1] - b[1]);
		for (int i = 1; i <= n; i++) {
			last[rank[i][0]] = i == 1 ? 0 : rank[i - 1][0];
			next[rank[i][0]] = i == n ? 0 : rank[i + 1][0];
		}
		for (int i = 1; i <= n; i++) {
			to1[i] = 0;
			dist1[i] = 0;
			to2[i] = 0;
			dist2[i] = 0;
			update(i, last[i]);
			update(i, last[last[i]]);
			update(i, next[i]);
			update(i, next[next[i]]);
			delete(i);
		}
	}

	public static void delete(int i) {
		int l = last[i];
		int r = next[i];
		if (l != 0) {
			next[l] = r;
		}
		if (r != 0) {
			last[r] = l;
		}
	}

	// 随机生成arr[1...n]确保没有重复数值
	// 为了测试
	public static void random(int v) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 1, cur; i <= n; i++) {
			do {
				cur = (int) (Math.random() * v * 2) - v;
			} while (set.contains(cur));
			set.add(cur);
			arr[i] = cur;
		}
	}

	// 验证的过程
	// 为了测试
	public static boolean check() {
		near1();
		for (int i = 1; i <= n; i++) {
			a[i] = to1[i];
			b[i] = dist1[i];
			c[i] = to2[i];
			d[i] = dist2[i];
		}
		near2();
		for (int i = 1; i <= n; i++) {
			if (a[i] != to1[i] || b[i] != dist1[i]) {
				return false;
			}
		}
		for (int i = 1; i <= n; i++) {
			if (c[i] != to2[i] || d[i] != dist2[i]) {
				return false;
			}
		}
		return true;
	}

	// 对数器
	// 为了测试
	public static void main(String[] args) {
		// 一定要确保arr中的数字无重复，所以让v大于n
		n = 1000;
		int v = 5000;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTime; i++) {
			random(v);
			if (!check()) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
