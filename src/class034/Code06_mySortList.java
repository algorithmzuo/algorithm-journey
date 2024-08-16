package class034;

// 排序链表
// 要求时间复杂度O(n*logn)，额外空间复杂度O(1)，还要求稳定性
// 数组排序做不到，链表排序可以
// 测试链接 : https://leetcode.cn/problems/sort-list/
public class Code06_mySortList {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;

		public ListNode(int val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.format("%d -> %s", val,next);
		}
	}

	// 提交如下的方法
	// 时间复杂度O(n*logn)，额外空间复杂度O(1)，有稳定性
	// 注意为了额外空间复杂度O(1)，所以不能使用递归
	// 因为mergeSort递归需要O(log n)的额外空间
	public static ListNode sortList(ListNode head) {
		// 1- 得到链表长度
		int size = 0;
		ListNode cur = head;
		while(cur!=null){
			size++;
			cur = cur.next;
		}

		// l1...r1 每组的左部分
		// l2...r2 每组的右部分
		// next 下一组的开头
		// lastTeamEnd 上一组的结尾
		ListNode l1, r1, l2, r2, next, lastTeamEnd;
		for (int step = 1; step < size; step <<= 1) {
			// 1- 先解决第一组
			 // 找到第一个小组
			l1 = head;
			r1 = findEnd(l1,step);
			 // 第二个小组
			l2 = r1.next;
			r2 = findEnd(l2, step);
			//先保存下一个节点 + preparation for merge
			next = r2.next;
			r1.next = null;
			r2.next = null;

			merge(l1,r1,l2,r2);
			head = start;
			lastTeamEnd = end;
			// 2- 再解决其余的
			while(next!= null){
				l1 = next;
				r1 = findEnd(l1, step);

				l2 = r1.next;
				if(l2 == null){
					lastTeamEnd.next = l1;
					break;
				}
				r2 = findEnd(l2,step);

				next= r2.next;
				r1.next = null;
				r2.next = null;
				merge(l1,r1,l2,r2);
				lastTeamEnd.next= start;
				lastTeamEnd = end;

			}
		}
		return head;
	}

	// 包括s在内，往下数k个节点返回
	// 如果不够，返回最后一个数到的非空节点
	public static ListNode findEnd(ListNode s, int k) {
		while(s.next != null && --k != 0){
			s = s.next;
		}
		return s;
	}

	public static ListNode start;
	public static ListNode end;

	// l1...r1 -> null : 有序的左部分
	// l2...r2 -> null : 有序的右部分
	// 整体merge在一起，保证有序
	// 并且把全局变量start设置为整体的头，全局变量end设置为整体的尾
	public static void  merge( ListNode l1, ListNode r1, ListNode l2, ListNode r2) {
		ListNode pre = null;
		// 1- first find the head;
		if(l1.val <= l2.val){
			pre = l1;
			start= l1;
			l1 = l1.next;
		}else{
			pre = l2;
			start =l2;
			l2 = l2.next;
		}
		// 2- iterate both and link both
		while(l1 != null && l2 != null){
			if(l1.val <= l2.val){
				pre.next = l1;
				l1 = l1.next;
			}else{
				pre.next = l2;
				l2 = l2.next;
			}
			pre = pre.next;
		}

		// 3- link the remain
		if(l1 != null){
			pre.next= l1;
			end = r1;
		}else{
			pre.next= l2;
			end = r2;
		}

	}

	public static void main(String[] args) {
		ListNode node0 = new ListNode(0);
		ListNode node1= new ListNode(2);
		ListNode node2 = new ListNode(4);
		ListNode node3 = new ListNode(1);
		ListNode node4 = new ListNode(3);
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		System.out.println(node0);
		System.out.println("-----------");


		sortList(node0);


	}

}
