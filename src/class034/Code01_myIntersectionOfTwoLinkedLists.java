package class034;

// 返回两个无环链表相交的第一个节点
// 测试链接 : https://leetcode.cn/problems/intersection-of-two-linked-lists/
public class Code01_myIntersectionOfTwoLinkedLists {

	// 提交时不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	public static ListNode getIntersectionNode(ListNode h1, ListNode h2) {
		// 1- 要么一个为空
		if(h1 == null || h2 == null){
			return null;
		}

		/**
		 * 分别遍历h1, h2
		 *
		 * if 最终节点不一样
		 * return null
		 * else  // 有共通节点
		 * {
		 * 	 1- 让长的节点走两者相差的节点的数量
		 * 	 2- 再让双方同时走直到节点一样
		 * }
		 *
		 */

		ListNode p1 = h1;
		ListNode p2 = h2;
		int diff = 0;
		while(p1 != null){
			diff++;
			p1 = p1.next;
		}
		while(p2 != null){
			diff--;
			p2 = p2.next;
		}

		if(p1 != p2){
			return null;
		}

		// 代表有共通节点
		ListNode pLonger = diff > 0 ? h1 : h2;
		ListNode pShorter = pLonger == h1 ? h2 : h1;
		diff = Math.abs(diff);
		// 让longer 链表先走差值的长度
		while(diff-- > 0) {
			pLonger = pLonger.next;
		}
		// 然后再一起走直到碰到相交的节点
		while(pLonger != pShorter){
			pLonger = pLonger.next;
			pShorter = pShorter.next;
		}
		return pLonger;
	}

}
