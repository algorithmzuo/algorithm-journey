package class168;

// 陨石雨，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3527
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//
//int n, m, k;
//
//int arr[MAXN];
//int need[MAXN];
//
//int rainl[MAXN];
//int rainr[MAXN];
//int num[MAXN];
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cnt = 0;
//
//long long tree[MAXN << 1];
//
//int lset[MAXN];
//int rset[MAXN];
//
//int ans[MAXN];
//
//void addEdge(int i, int v) {
//    nxt[++cnt] = head[i];
//    to[cnt] = v;
//    head[i] = cnt;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    int siz = m * 2;
//    while (i <= siz) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//void add(int l, int r, int v) {
//    add(l, v);
//    add(r + 1, -v);
//}
//
//long long query(int i) {
//    long long ret = 0;
//    while (i > 0) {
//    	ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void compute(int al, int ar, int tl, int tr) {
//    if (tl == tr) {
//        for (int i = al; i <= ar; i++) {
//            ans[arr[i]] = tl;
//        }
//    } else {
//        int mid = (tl + tr) >> 1;
//        int lsiz = 0, rsiz = 0, nation;
//        long long times;
//        for (int i = tl; i <= mid; i++) {
//            add(rainl[i], rainr[i], num[i]);
//        }
//        for (int i = al; i <= ar; i++) {
//            nation = arr[i];
//            times = 0;
//            for (int e = head[nation]; e > 0; e = nxt[e]) {
//                times += query(to[e]) + query(to[e] + m);
//                if (times >= need[nation]) {
//                    break;
//                }
//            }
//            if (times >= need[nation]) {
//                lset[++lsiz] = nation;
//            } else {
//                need[nation] -= static_cast<int>(times);
//                rset[++rsiz] = nation;
//            }
//        }
//        for (int i = tl; i <= mid; i++) {
//            add(rainl[i], rainr[i], -num[i]);
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            arr[al + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            arr[al + lsiz + i - 1] = rset[i];
//        }
//        compute(al, al + lsiz - 1, tl, mid);
//        compute(al + lsiz, ar, mid + 1, tr);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, nation; i <= m; i++) {
//        cin >> nation;
//        addEdge(nation, i);
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = i;
//        cin >> need[i];
//    }
//    cin >> k;
//    for (int i = 1; i <= k; i++) {
//        cin >> rainl[i] >> rainr[i] >> num[i];
//        if (rainr[i] < rainl[i]) {
//            rainr[i] += m;
//        }
//    }
//    compute(1, n, 1, k + 1);
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == k + 1) {
//            cout << "NIE" << '\n';
//        } else {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}