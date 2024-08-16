package class027;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// 合并K个有序链表
// 测试链接：https://www.nowcoder.com/practice/65cfde9e5b9b4cf2b6bafa5f3ef33fa6
public class Code01_myMergeKSortedLists {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;

		public ListNode() {
		}
		public ListNode(int val) {
			this.val =  val;
		}
	}

	// 提交以下的方法
	public static ListNode mergeKLists(ArrayList<ListNode> arr) {
		PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
		// 1- 把所有的头节点都放入
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) != null)
				heap.add(arr.get(i));
		}
		if (heap.isEmpty()) {
			return null;
		}
		// 2- 先 pop 一个作为头节点
		ListNode head = new ListNode();
		ListNode pre = head;

		head = heap.poll();
		pre = head;
		if (pre.next != null) {
			heap.add(pre.next);
		}
		// 3- 依次Pop out 节点
		while (!heap.isEmpty()) {
			ListNode cur = heap.poll();
			if (cur.next != null) {
				heap.add(cur.next);
			}
			pre.next = cur;
			pre = pre.next;
		}
		return head;

	}

	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		ArrayList<ListNode> arr = new ArrayList<>();
		System.out.println(arr.size());
		arr.add(head);

		ListNode head1 = new ListNode(3);
		head1.next = new ListNode(4);
		head1.next.next = new ListNode(5);
		arr.add(head1);

		ListNode head3 = mergeKLists(arr);

		while(head3 !=null){
			System.out.println(head3.val);
			head3 = head3.next;
		}

	}

}
