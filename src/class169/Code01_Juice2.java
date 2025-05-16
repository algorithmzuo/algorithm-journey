package class169;

// 混合果汁，C++版
// 一共有n种果汁，每种果汁给定，美味度d、每升价格p、添加上限l
// 制作混合果汁时每种果汁不能超过添加上限，其中美味度最低的果汁，决定混合果汁的美味度
// 一共有m个小朋友，给每位制作混合果汁时，钱数不超过money[i]，体积不少于least[i]
// 打印每个小朋友能得到的混合果汁最大美味度，如果无法满足，打印-1
// 1 <= n、m、d、p、l <= 10^5
// 1 <= money[i]、least[i] <= 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P4602
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Juice {
//    int d, p, l;
//};
//
//bool JuiceCmp(Juice x, Juice y) {
//    return x.d > y.d;
//}
//
//const int MAXN = 100001;
//int n, m;
//
//Juice juice[MAXN];
//int qid[MAXN];
//long long money[MAXN];
//long long least[MAXN];
//
//int maxp = 0;
//long long suml[MAXN << 2];
//long long cost[MAXN << 2];
//int used = 0;
//
//int lset[MAXN];
//int rset[MAXN];
//
//int ans[MAXN];
//
//void up(int i) {
//    suml[i] = suml[i << 1] + suml[i << 1 | 1];
//    cost[i] = cost[i << 1] + cost[i << 1 | 1];
//}
//
//void add(int jobi, int jobv, int l, int r, int i) {
//    if (l == r) {
//        suml[i] += jobv;
//        cost[i] = suml[i] * l;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            add(jobi, jobv, l, mid, i << 1);
//        } else {
//            add(jobi, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//long long query(long long volume, int l, int r, int i) {
//    if (l == r) {
//        return volume * l;
//    }
//    int mid = (l + r) >> 1;
//    if (suml[i << 1] >= volume) {
//        return query(volume, l, mid, i << 1);
//    } else {
//        return cost[i << 1] + query(volume - suml[i << 1], mid + 1, r, i << 1 | 1);
//    }
//}
//
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            ans[qid[i]] = vl;
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        while (used < mid) {
//            used++;
//            add(juice[used].p, juice[used].l, 1, maxp, 1);
//        }
//        while (used > mid) {
//            add(juice[used].p, -juice[used].l, 1, maxp, 1);
//            used--;
//        }
//        int lsiz = 0, rsiz = 0;
//        for (int i = ql, id; i <= qr; i++) {
//            id = qid[i];
//            if (suml[1] >= least[id] && query(least[id], 1, maxp, 1) <= money[id]) {
//                lset[++lsiz] = id;
//            } else {
//                rset[++rsiz] = id;
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            qid[ql + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            qid[ql + lsiz + i - 1] = rset[i];
//        }
//        compute(ql + lsiz, qr, mid + 1, vr);
//        compute(ql, ql + lsiz - 1, vl, mid);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> juice[i].d >> juice[i].p >> juice[i].l;
//        maxp = max(maxp, juice[i].p);
//    }
//    for (int i = 1; i <= m; i++) {
//        qid[i] = i;
//        cin >> money[i] >> least[i];
//    }
//    sort(juice + 1, juice + n + 1, JuiceCmp);
//    compute(1, m, 1, n + 1);
//    for (int i = 1; i <= m; i++) {
//        if (ans[i] == n + 1) {
//            cout << -1 << '\n';
//        } else {
//            cout << juice[ans[i]].d << '\n';
//        }
//    }
//    return 0;
//}