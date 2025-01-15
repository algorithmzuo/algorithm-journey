package class031;

// Brian Kernighan算法
// 提取出二进制里最右侧的1
// 判断一个整数是不是2的幂
// 测试链接 : https://leetcode.cn/problems/power-of-two/
public class Code01_PowerOfTwo {
    //2的幂2进制中只会出现一个1，所以n&-n会获取到最右边的一个1，判断是否等于n就知道是否是2的幂
	public static boolean isPowerOfTwo(int n) {
		return n > 0 && n == (n & -n);
	}

    public static void main(String[] args) {
        int n=0;
        System.out.println(String.format("%32s",Integer.toBinaryString(-n)).replace(" ","0"));
        System.out.println(String.format("%32s",Integer.toBinaryString(~-n)).replace(" ","0"));
        System.out.println(String.format("%32s",Integer.toBinaryString(n)).replace(" ","0"));
    }
}
