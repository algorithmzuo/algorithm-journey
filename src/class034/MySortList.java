package class034;


class MySortList {
    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val){
            this.val=val;
        }
    }
    public ListNode sortList(ListNode head) {
        if(head==null) return null;
        int n=0;
        ListNode cur=head;
        while(cur!=null){
            cur=cur.next;
            n++;
        }
        ListNode r1,r2,l1,l2,lastTeamEnd,next;
        for(int step=1;step<n;step<<=1){
            l1=head;
            r1=findEnd(l1,step);
            l2=r1.next;
            r2=findEnd(l2,step);
            next=r2.next;
            r1.next=null;
            r2.next=null;
            ListNode[] arr=merge(l1,r1,l2,r2);
            lastTeamEnd=arr[1];
            head=arr[0];
            while(next!=null){
                l1=next;
                r1=findEnd(l1,step);
                l2=r1.next;
                if(l2==null){
                    lastTeamEnd.next=l1;
                    break;
                }
                r2=findEnd(l2,step);
                next=r2.next;
                r1.next=null;
                r2.next=null;
                arr=merge(l1,r1,l2,r2);
                lastTeamEnd.next=arr[0];
                lastTeamEnd=arr[1];
            }
        }
        return head;
    }
    public ListNode findEnd(ListNode a,int step){
        while(a.next!=null&&--step>0){
            a=a.next;
        }
        return a;
    }

    public ListNode[] merge(ListNode a,ListNode b,ListNode c,ListNode d){
        ListNode start,end,pre,next=d.next;
        start=a.val<=c.val?a:c;
        pre=start;
        if(start==a){
            a=a.next;
        }else{
            c=c.next;
        }
        while(a!=null&&c!=null){
            if(a.val<=c.val){
                pre.next=a;
                pre=a;
                a=a.next;
            }else{
                pre.next=c;
                pre=c;
                c=c.next;
            }
        }
        if (a!=null){
            pre.next=a;
            end=b;
        }else{
            pre.next=c;
            end=d;
        }
        return new ListNode[]{start,end};
    }

    public static void main(String[] args) {
        ListNode head=new ListNode(4);
        ListNode cur=head;
        //todo 4,19,14,5,-3,1,8,5,11,15
        cur.next=new ListNode(19);
        cur=cur.next;
        cur.next=new ListNode(14);
        cur=cur.next;
        cur.next=new ListNode(5);
        cur=cur.next;
        cur.next=new ListNode(-3);
        cur=cur.next;
        cur.next=new ListNode(1);
        cur=cur.next;
        cur.next=new ListNode(8);
        cur=cur.next;
        cur.next=new ListNode(5);
        cur=cur.next;
        cur.next=new ListNode(11);
        cur=cur.next;
        cur.next=new ListNode(15);
        MySortList mySortList = new MySortList();
        System.out.println(mySortList.sortList(head).val);
    }
}