package class031;

// 逆序二进制的状态
// 测试链接 : https://leetcode.cn/problems/reverse-bits/
public class Code05_myReverseBits {
	public static void printBinary(int n){
		for(int i=0 ; i<32 ;i++){
			System.out.print( (n & (1 << i)) == 0 ? '0' : '1');
		}
		System.out.println();
	}
	// 逆序二进制的状态
	// 是不是看着头皮发麻啊？代码看着很魔幻吧？别慌
	public static int reverseBits(int n) {
		n = ((0xaaaaaaaa & n) >>> 1) | ((0x55555555 & n) << 1);
		n = ((0xcccccccc & n) >>> 2) | ((0x33333333 & n) << 2);
		n = ((0xf0f0f0f0 & n) >>> 4) | ((0x0f0f0f0f & n) << 4);
		n = ((0xff00ff00 & n) >>> 8) | ((0x00ff00ff & n) << 8);
		n = n >>> 16 | n << 16;
		return n;
	}

	public static void main(String[] args) {
		printBinary(8);
		System.out.println(reverseBits(8));

	}

}
