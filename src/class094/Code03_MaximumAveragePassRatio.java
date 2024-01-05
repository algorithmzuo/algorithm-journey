package class094;

import java.util.PriorityQueue;

// 最大平均通过率
// 一所学校里有一些班级，每个班级里有一些学生，现在每个班都会进行一场期末考试
// 给你一个二维数组classes，其中classes[i]=[passi, totali]
// 表示你提前知道了第i个班级总共有totali个学生
// 其中只有 passi 个学生可以通过考试
// 给你一个整数extraStudents，表示额外有extraStudents个聪明的学生，一定能通过期末考
// 你需要给这extraStudents个学生每人都安排一个班级，使得所有班级的平均通过率最大
// 一个班级的 通过率 等于这个班级通过考试的学生人数除以这个班级的总人数
// 平均通过率 是所有班级的通过率之和除以班级数目
// 请你返回在安排这extraStudents个学生去对应班级后的最大平均通过率
// 测试链接 : https://leetcode.cn/problems/maximum-average-pass-ratio/
public class Code03_MaximumAveragePassRatio {

	public static double maxAverageRatio(int[][] classes, int m) {
		int n = classes.length;
		// double[] c1 : {a, b, c}
		// a : c1班级有多少人通过
		// b : c1班级总人数
		// c : 如果再来一人，c1班级通过率提升多少，(a+1)/(b+1) - a/b
		// 通过率提升的大根堆
		PriorityQueue<double[]> heap = new PriorityQueue<>((c1, c2) -> c1[2] >= c2[2] ? -1 : 1);
		// n * logn
		for (int[] c : classes) {
			double a = c[0];
			double b = c[1];
			heap.add(new double[] { a, b, (a + 1) / (b + 1) - a / b });
		}
		// 天才一个一个分配
		// m * logn
		while (m-- > 0) {
			double[] cur = heap.poll();
			double a = cur[0] + 1;
			double b = cur[1] + 1;
			heap.add(new double[] { a, b, (a + 1) / (b + 1) - a / b });
		}
		// 计算通过率累加和
		double ans = 0;
		while (!heap.isEmpty()) {
			double[] cur = heap.poll();
			ans += cur[0] / cur[1];
		}
		// 返回最大平均通过率
		return ans / n;
	}

}
