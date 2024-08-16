package utils;


public class BaseListNode {
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return val + "->" + next;
        }
    }

    private static ListNode head;

    public static ListNode createList(int length){
        if(length <= 0)
            return null;
        head = new ListNode(1);
        ListNode p = head;
        for (int i = 2; i <= length ; i++) {
            ListNode curNode = new ListNode(i);
            p.setNext(curNode);
            p = p.next;
        }
        return head;
    }

    public static void printListNode(ListNode head){
        while(head != null){
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        ListNode head = createList(3);
        printListNode(head);
    }


}
