package class168;

// 混合果汁，C++版
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
//int arr[MAXN];
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
//void compute(int al, int ar, int jl, int jr) {
//    if (jl == jr) {
//        for (int i = al; i <= ar; i++) {
//            ans[arr[i]] = jl;
//        }
//    } else {
//        int mid = (jl + jr) >> 1;
//        while (used < mid) {
//            used++;
//            add(juice[used].p, juice[used].l, 1, maxp, 1);
//        }
//        while (used > mid) {
//            add(juice[used].p, -juice[used].l, 1, maxp, 1);
//            used--;
//        }
//        int lsiz = 0, rsiz = 0;
//        for (int i = al, id; i <= ar; i++) {
//            id = arr[i];
//            if (suml[1] >= least[id] && query(least[id], 1, maxp, 1) <= money[id]) {
//                lset[++lsiz] = id;
//            } else {
//                rset[++rsiz] = id;
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            arr[al + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            arr[al + lsiz + i - 1] = rset[i];
//        }
//        compute(al, al + lsiz - 1, jl, mid);
//        compute(al + lsiz, ar, mid + 1, jr);
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
//        arr[i] = i;
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