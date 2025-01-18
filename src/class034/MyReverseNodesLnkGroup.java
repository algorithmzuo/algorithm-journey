package class034;
//这个使用了假头节点
class MyReverseNodesLnkGroup {
    public static class ListNode {
        public int val;
        public ListNode next;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode tmp=new ListNode();
        tmp.next=head;
        ListNode LastTeamGroupEnd=tmp;
        ListNode start,end;
        while(LastTeamGroupEnd.next!=null){
            start=LastTeamGroupEnd.next;
            end=findEnd(start,k);
            if(end==null) return tmp.next;
            reverse(start,end);
            LastTeamGroupEnd.next=end;
            LastTeamGroupEnd=start;
        }
        return tmp.next;
    }
    public ListNode findEnd(ListNode head,int k){
        while(head!=null&&--k>0){
            head=head.next;
        }
        return head;
    }
    public void reverse(ListNode start,ListNode end){
        end=end.next;
        ListNode pre=null,cur=start,next=null;
        while(cur!=end){
            next=cur.next;
            cur.next=pre;
            pre=cur;
            cur=next;
        }
        start.next=cur;
    }
}
