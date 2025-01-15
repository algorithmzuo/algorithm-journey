package class031;

// 已知n是非负数,n>0才行
// 返回大于等于n的最小的2某次方
// 如果int范围内不存在这样的数，返回整数最小值
public class Code03_Near2power {

	public static int near2power(int n) {
		if (n <= 0) {
			return 1;
		}
        //将n右侧的0全变成1；如果n是2的幂，不变
		n--;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return n + 1;
	}

	public static void main(String[] args) {
		int number = -3;
        System.out.println(Integer.toBinaryString(number-1+3+1));
		System.out.println(near2power(number));
	}
}
