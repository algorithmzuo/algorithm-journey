package class034;

// 每k个节点一组翻转链表
// 测试链接：https://leetcode.cn/problems/reverse-nodes-in-k-group/
public class Code02_ReverseNodesInkGroup {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	// 提交如下的方法
	public static ListNode reverseKGroup(ListNode head, int k) {
		ListNode start = head;
		ListNode end = teamEnd(start, k);
		if (end == null) {
			return head;
		}
		// 第一组很特殊因为牵扯到换头的问题
		head = end;
		reverse(start, end);
		// 翻转之后start变成了上一组的结尾节点
		ListNode lastTeamEnd = start;
		while (lastTeamEnd.next != null) {
			start = lastTeamEnd.next;
			end = teamEnd(start, k);
			if (end == null) {
				return head;
			}
			reverse(start, end);
			lastTeamEnd.next = end;
			lastTeamEnd = start;
		}
		return head;
	}

	// 当前组的开始节点是s，往下数k个找到当前组的结束节点返回
	public static ListNode teamEnd(ListNode s, int k) {
		while (--k != 0 && s != null) {
			s = s.next;
		}
		return s;
	}

	// s -> a -> b -> c -> e -> 下一组的开始节点
	// 上面的链表通过如下的reverse方法调整成 : e -> c -> b -> a -> s -> 下一组的开始节点
	public static void reverse(ListNode s, ListNode e) {
		e = e.next;
		ListNode pre = null, cur = s, next = null;
		while (cur != e) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		s.next = e;
	}

}
