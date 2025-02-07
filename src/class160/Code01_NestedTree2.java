package class160;

// 树套树，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 160;
//const int INF = INT_MAX;
//int n,m,s;
//int arr[MAXN];
//int ques[MAXN][4];
//int sorted[MAXN*2];
//int root[MAXN];
//int sum[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int cntt;
//int pos[MAXN];
//int pre[MAXN];
//int cntpos,cntpre;
//
//int kth(int num){
//    int l=1,r=s;
//    while(l<=r){
//        int mid=(l+r)>>1;
//        if(sorted[mid]==num) return mid;
//        if(sorted[mid]<num) l=mid+1;
//        else r=mid-1;
//    }
//    return -1;
//}
//
//int lowbit(int x){
//    return x&(-x);
//}
//
//void up(int i){
//    sum[i]=sum[ls[i]]+sum[rs[i]];
//}
//
//int add(int idx,int v,int L,int R,int i){
//    if(!i) i=++cntt;
//    if(L==R){
//        sum[i]+=v;
//    } else {
//        int mid=(L+R)>>1;
//        if(idx<=mid) ls[i]=add(idx,v,L,mid,ls[i]);
//        else rs[i]=add(idx,v,mid+1,R,rs[i]);
//        up(i);
//    }
//    return i;
//}
//
//void add(int i,int v){
//    for(int j=i;j<=n;j+=lowbit(j)){
//        root[j]=add(arr[i],v,1,s,root[j]);
//    }
//}
//
//int queryNumber(int k,int L,int R){
//    if(L==R) return L;
//    int mid=(L+R)>>1;
//    int leftsum=0;
//    for(int i=1;i<=cntpos;i++){
//        leftsum+=sum[ls[pos[i]]];
//    }
//    for(int i=1;i<=cntpre;i++){
//        leftsum-=sum[ls[pre[i]]];
//    }
//    if(k<=leftsum){
//        for(int i=1;i<=cntpos;i++){
//            pos[i]=ls[pos[i]];
//        }
//        for(int i=1;i<=cntpre;i++){
//            pre[i]=ls[pre[i]];
//        }
//        return queryNumber(k,L,mid);
//    } else {
//        for(int i=1;i<=cntpos;i++){
//            pos[i]=rs[pos[i]];
//        }
//        for(int i=1;i<=cntpre;i++){
//            pre[i]=rs[pre[i]];
//        }
//        return queryNumber(k-leftsum,mid+1,R);
//    }
//}
//
//int findNumber(int L,int R,int k){
//    cntpos=cntpre=0;
//    for(int i=R;i>0;i-=lowbit(i)){
//        pos[++cntpos]=root[i];
//    }
//    for(int i=L-1;i>0;i-=lowbit(i)){
//        pre[++cntpre]=root[i];
//    }
//    return queryNumber(k,1,s);
//}
//
//int queryRank(int idx,int L,int R){
//    if(L==R) return 0;
//    int mid=(L+R)>>1;
//    if(idx<=mid){
//        for(int i=1;i<=cntpos;i++){
//            pos[i]=ls[pos[i]];
//        }
//        for(int i=1;i<=cntpre;i++){
//            pre[i]=ls[pre[i]];
//        }
//        return queryRank(idx,L,mid);
//    } else {
//        int leftsum=0;
//        for(int i=1;i<=cntpos;i++){
//            leftsum+=sum[ls[pos[i]]];
//            pos[i]=rs[pos[i]];
//        }
//        for(int i=1;i<=cntpre;i++){
//            leftsum-=sum[ls[pre[i]]];
//            pre[i]=rs[pre[i]];
//        }
//        return leftsum+queryRank(idx,mid+1,R);
//    }
//}
//
//int findRank(int L,int R,int val){
//    cntpos=cntpre=0;
//    for(int i=R;i>0;i-=lowbit(i)){
//        pos[++cntpos]=root[i];
//    }
//    for(int i=L-1;i>0;i-=lowbit(i)){
//        pre[++cntpre]=root[i];
//    }
//    return queryRank(val,1,s)+1;
//}
//
//int findLast(int L,int R,int k){
//    int rk=findRank(L,R,k);
//    if(rk==1) return 0;
//    return findNumber(L,R,rk-1);
//}
//
//int findNext(int L,int R,int k){
//    if(k==s) return s+1;
//    int rk=findRank(L,R,k+1);
//    if(rk==R-L+2) return s+1;
//    return findNumber(L,R,rk);
//}
//
//void prepare(){
//    s=0;
//    for(int i=1;i<=n;i++){
//        sorted[++s]=arr[i];
//    }
//    for(int i=1;i<=m;i++){
//        if(ques[i][0]==3){
//            sorted[++s]=ques[i][2];
//        } else if(ques[i][0]!=2){
//            sorted[++s]=ques[i][3];
//        }
//    }
//    sort(sorted+1,sorted+s+1);
//    int len=1;
//    for(int i=2;i<=s;i++){
//        if(sorted[len]!=sorted[i]){
//            sorted[++len]=sorted[i];
//        }
//    }
//    s=len;
//    for(int i=1;i<=n;i++){
//        arr[i]=kth(arr[i]);
//        add(i,1);
//    }
//    sorted[0]=-INF;
//    sorted[s+1]=INF;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin>>n>>m;
//    for(int i=1;i<=n;i++){
//        cin>>arr[i];
//    }
//    for(int i=1;i<=m;i++){
//        cin>>ques[i][0]>>ques[i][1]>>ques[i][2];
//        if(ques[i][0]!=3){
//            cin>>ques[i][3];
//        }
//    }
//    prepare();
//    for(int i=1;i<=m;i++){
//        if(ques[i][0]==1){
//            cout<<findRank(ques[i][1],ques[i][2],kth(ques[i][3]))<<"\n";
//        } else if(ques[i][0]==2){
//            cout<<sorted[findNumber(ques[i][1],ques[i][2],ques[i][3])]<< "\n";
//        } else if(ques[i][0]==3){
//            add(ques[i][1],-1);
//            arr[ques[i][1]]=kth(ques[i][2]);
//            add(ques[i][1],1);
//        } else if(ques[i][0]==4){
//            cout<<sorted[findLast(ques[i][1],ques[i][2],kth(ques[i][3]))]<<"\n";
//        } else {
//            cout<<sorted[findNext(ques[i][1],ques[i][2],kth(ques[i][3]))]<<"\n";
//        }
//    }
//    return 0;
//}