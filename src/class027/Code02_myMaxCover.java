package class027;

// 最多线段重合问题
// 测试链接 : https://www.nowcoder.com/practice/1ae8d0b6bb4e4bcdbf64ec491f63fc37
// 测试链接 : https://leetcode.cn/problems/meeting-rooms-ii/
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code02_myMaxCover {

	public static int MAXN = 10001;
	// 小根堆，堆顶0位置
	public static int[] heap = new int[MAXN];

	public static int[][] line = new int[MAXN][2];

	// 堆的大小
	public static int size;
	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while(in.nextToken() != StreamTokenizer.TT_EOF){
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				line[i][0] = (int) in.nval;
				in.nextToken();
				line[i][1] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		size = 0;
		// 1- sort by left boundary
		Arrays.sort(line, 0, n, (o1, o2) -> o1[0] - o2[0]);

		// 2-
		int ans = 0;
		for (int i = 0; i < n; i++) {
			while(size > 0 && line[i][0] >= heap[0]){
				pop();
			}
			add(line[i][1]);
			ans = Math.max(size, ans);

		}
		return ans;
	}

	//小根堆， 只要父节点比自己大就swap
	public static void add(int x) {
		heap[size] = x;
		int xIndex = size++;

		while( heap[(xIndex -1)/2] > x){
			swap((xIndex -1)/2, xIndex);
			xIndex = (xIndex -1)/2;
		}
	}

	public static void pop() {
		swap(0, --size);
		int i = 0, l =1;
		while(l< size){
			int smallerIndex = l + 1 < size && heap[l+1] < heap[l] ? l+1 : l;
			smallerIndex = heap[i] < heap[smallerIndex] ? i: smallerIndex;
			if(smallerIndex == i){
				break;
			}
			swap(smallerIndex, i);
			i = smallerIndex;
			l = (i *2) +1;
		}

	}

	public static void swap(int i, int j) {
		int tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

	// 也找到了leetcode测试链接
	// 测试链接 : https://leetcode.cn/problems/meeting-rooms-ii/
	// 提交如下代码可以直接通过
	public static int minMeetingRooms(int[][] meeting) {
		int n = meeting.length;
		Arrays.sort(meeting, (a, b) -> a[0] - b[0]);
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			while (!heap.isEmpty() && heap.peek() <= meeting[i][0]) {
				heap.poll();
			}
			heap.add(meeting[i][1]);
			ans = Math.max(ans, heap.size());
		}
		return ans;
	}

}
