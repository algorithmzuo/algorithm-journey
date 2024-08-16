package class022;

// 小和问题
// 测试链接 : https://www.nowcoder.com/practice/edfe05a1d45c4ea89101d936cac32469
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.*;

import static java.lang.System.*;

public class Code01_mySmallSum {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static int size;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer streamTokenizer = new StreamTokenizer(br);
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
		while(streamTokenizer.nextToken()!=StreamTokenizer.TT_EOF){
			size = (int) streamTokenizer.nval;
			for(int i = 0; i < size; i++){
				streamTokenizer.nextToken();
				arr[i] = (int) streamTokenizer.nval;
			}
		}
		pw.println(smallSum(0,  size - 1));
		pw.flush();
		pw.close();
	}

	// 结果比较大，用int会溢出的，所以返回long类型
	// 特别注意溢出这个点，笔试常见坑
	// 返回arr[l...r]范围上，小和的累加和，同时请把arr[l..r]变有序
	// 时间复杂度O(n * logn)
	public static long smallSum(int l, int r) {
		if(l == r){
			return 0;
		}
		int m = l + ((r - l) >> 1);
		// out.println(l +"-----------" + m + "--------" + r);
		return smallSum(l , m) + smallSum(m + 1 ,r) + merge(l , m, r );
	}

	// 返回跨左右产生的小和累加和，左侧有序、右侧有序，让左右两侧整体有序
	// arr[l...m] arr[m+1...r]
	public static long merge(int l, int m, int r) {
		// 跨左右产生小和累加和
		long sum = 0 ;
		long curSum = 0;
		for(int i = l, j = m +1; j <=r; j++){
			while (arr[i] <= arr[j] && i<=m){
				curSum += arr[i];
				i++;
			}
			sum += curSum;
		}

		// merge
		int i = l, j = m+1, k = 0;
		while(i <= m && j <=r){
			help[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
		}
		while(i<=m){
			help[k++] = arr[i++];
		}
		while(j<=r){
			help[k++] =  arr[j++];
		}

		for(i = l, k = 0 ; i<=r;  ){
			arr[i++] = help[k++];
		}
		return sum;
	}

}