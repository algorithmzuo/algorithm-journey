package class031;

// 给你两个整数 left 和 right ，表示区间 [left, right]
// 返回此区间内所有数字 & 的结果
// 包含 left 、right 端点
// 测试链接 : https://leetcode.cn/problems/bitwise-and-of-numbers-range/
public class Code04_LeftToRightAnd {

	public static int rangeBitwiseAnd(int left, int right) {
		while (left < right) {
            //todo n&-n会算出n的最右侧的1的值，然后n不断去掉最右侧的1.原理是n与n-1&的结果是去掉n最右侧的1；
			right -= right & -right;
		}
		return right;
	}

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(rangeBitwiseAnd(-2, -1));
    }
}