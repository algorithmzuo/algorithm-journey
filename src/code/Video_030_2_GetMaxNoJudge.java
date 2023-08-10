package code;

// 不用任何判断语句和比较操作，返回两个数的最大值
// 测试链接 : https://www.nowcoder.com/practice/d2707eaf98124f1e8f1d9c18ad487f76
public class Video_030_2_GetMaxNoJudge {

	// 必须保证n一定是0或者1
	// 0变1，1变0
	public static int flip(int n) {
		return n ^ 1;
	}

	// 非负数返回1
	// 负数返回0
	public static int sign(int n) {
		return flip(n >>> 31);
	}

	// 有溢出风险
	public static int getMax1(int a, int b) {
		int c = a - b;
		int returnA = sign(c);
		int returnB = flip(returnA);
		return a * returnA + b * returnB;
	}

	// 没有任何问题
	public static int getMax2(int a, int b) {
		int c = a - b;
		int sa = sign(a);
		int sb = sign(b);
		int sc = sign(c);
		int diffAB = sa ^ sb;
		int sameAB = flip(diffAB);
		int returnA = diffAB * sa + sameAB * sc;
		int returnB = flip(returnA);
		return a * returnA + b * returnB;
	}

	public static void main(String[] args) {
		int a = Integer.MIN_VALUE;
		int b = Integer.MAX_VALUE;
		// getMax1方法会错误，因为溢出
		System.out.println(getMax1(a, b));
		// getMax2方法永远正确
		System.out.println(getMax2(a, b));
	}

}
