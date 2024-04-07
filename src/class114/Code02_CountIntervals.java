package class114;

import java.util.Arrays;

// 统计区间中的整数数目
// 实现CountIntervals类的如下三个方法
// CountIntervals() : 初始化
// void add(int l, int r) : 把[l, r]范围上的数字都设置成1
// int count() : 返回整个区间有多少个1
// CountIntervals类需要支持1 ~ 10^9范围
// 调用add和count方法总共10^5次
// 测试链接 : https://leetcode.cn/problems/count-integers-in-intervals/
public class Code02_CountIntervals {

	// 开点线段树的实现
	// 为了所有语言的同学都容易改出来
	// 本实现选择用静态空间的方式实现
	// 该方法的打败比例不高但是非常好想
	// 有兴趣的同学可以研究其他做法
	class CountIntervals {
		public static int n = 1000000000;// 需要支持的最大范围
		public static int LIMIT = 700001; // 空间大小是实验的结果
		public static int[] left = new int[LIMIT];
		public static int[] right = new int[LIMIT];
		public static int[] sum = new int[LIMIT];
		public static int cnt = 1;

		public CountIntervals() {
			Arrays.fill(left, 1, cnt + 1, 0);
			Arrays.fill(right, 1, cnt + 1, 0);
			Arrays.fill(sum, 1, cnt + 1, 0);
			cnt = 1;
		}

		public static void up(int h, int l, int r) {
			sum[h] = sum[l] + sum[r];
		}

		// 这个操作特殊性在于，只有改1的操作，没有改0的操作
		// 理解这个就可以分析出不需要懒更新任务的下发，原因有两个
		// 1) 查询操作永远查的是整个范围1的数量，不会有范围查询，每次都返回sum[1]
		// 2) 一个范围已经全是1，那以后都会是1，没有必要把全是1的懒更新信息再向下传递
		public static void setOne(int jobl, int jobr, int l, int r, int i) {
			if (sum[i] == r - l + 1) {
				// 整个范围全是1了
				// 以后不需要再执行
				// 以后也没有懒更新
				return;
			}
			if (jobl <= l && r <= jobr) {
				// 任务全包直接修改sum[i]然后结束
				sum[i] = r - l + 1;
			} else {
				int mid = (l + r) / 2;
				if (jobl <= mid) {
					// 用到左才去申请空间
					if (left[i] == 0) {
						left[i] = ++cnt;
					}
					setOne(jobl, jobr, l, mid, left[i]);
				}
				if (jobr > mid) {
					// 用到右才去申请空间
					if (right[i] == 0) {
						right[i] = ++cnt;
					}
					setOne(jobl, jobr, mid + 1, r, right[i]);
				}
				up(i, left[i], right[i]);
			}
		}

		public void add(int left, int right) {
			setOne(left, right, 1, n, 1);
		}

		public int count() {
			return sum[1];
		}
	}

}
