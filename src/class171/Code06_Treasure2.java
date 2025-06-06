package class171;

// 寻找宝藏，C++版
// 一共有n个宝藏，每个宝藏有a、b、c、d四个属性值，以及拿取之后的收益v
// 你可以选择任意顺序拿取宝藏，每次拿的宝藏的四种属性值都不能小于上次拿的宝藏
// 打印你能获得的最大收益，打印有多少种拿取方法能获得最大的收益
// 1 <= n <= 8 * 10^4
// 1 <= a、b、c、d、v <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4849
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int a, b, c, d;
//    long long v;
//    int i;
//    bool left;
//};
//
//bool Cmp1(Node x, Node y) {
//    if (x.a != y.a) return x.a < y.a;
//    if (x.b != y.b) return x.b < y.b;
//    if (x.c != y.c) return x.c < y.c;
//    if (x.d != y.d) return x.d < y.d;
//    return x.v > y.v;
//}
//
//bool Cmp2(Node x, Node y) {
//    if (x.b != y.b) return x.b < y.b;
//    if (x.c != y.c) return x.c < y.c;
//    if (x.d != y.d) return x.d < y.d;
//    return x.a < y.a;
//}
//
//bool Cmp3(Node x, Node y) {
//    if (x.c != y.c) return x.c < y.c;
//    if (x.d != y.d) return x.d < y.d;
//    if (x.a != y.a) return x.a < y.a;
//    return x.b < y.b;
//}
//
//const int MAXN = 80001;
//const long long INF = 1e18 + 1;
//const int MOD = 998244353;
//int n, s;
//
//Node arr[MAXN];
//Node tmp1[MAXN];
//Node tmp2[MAXN];
//int sortd[MAXN];
//
//long long treeVal[MAXN];
//int treeCnt[MAXN];
//
//long long dp[MAXN];
//int cnt[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, long long v, int c) {
//    while (i <= s) {
//        if (v > treeVal[i]) {
//            treeVal[i] = v;
//            treeCnt[i] = c % MOD;
//        } else if (v == treeVal[i]) {
//            treeCnt[i] = (treeCnt[i] + c) % MOD;
//        }
//        i += lowbit(i);
//    }
//}
//
//long long queryVal;
//int queryCnt;
//
//void query(int i) {
//    queryVal = -INF;
//    queryCnt = 0;
//    while (i > 0) {
//        if (treeVal[i] > queryVal) {
//            queryVal = treeVal[i];
//            queryCnt = treeCnt[i];
//        } else if (treeVal[i] == queryVal) {
//            queryCnt = (queryCnt + treeCnt[i]) % MOD;
//        }
//        i -= lowbit(i);
//    }
//}
//
//void clear(int i) {
//    while (i <= s) {
//        treeVal[i] = -INF;
//        treeCnt[i] = 0;
//        i += lowbit(i);
//    }
//}
//
//void merge(int l, int m, int r) {
//    for (int i = l; i <= r; i++) {
//        tmp2[i] = tmp1[i];
//    }
//    sort(tmp2 + l, tmp2 + m + 1, Cmp3);
//    sort(tmp2 + m + 1, tmp2 + r + 1, Cmp3);
//    int p1, p2, id;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && tmp2[p1 + 1].c <= tmp2[p2].c) {
//            p1++;
//            if (tmp2[p1].left) {
//                add(tmp2[p1].d, dp[tmp2[p1].i], cnt[tmp2[p1].i]);
//            }
//        }
//        if (!tmp2[p2].left) {
//            query(tmp2[p2].d);
//            id = tmp2[p2].i;
//            if (queryVal + tmp2[p2].v > dp[id]) {
//                dp[id] = queryVal + tmp2[p2].v;
//                cnt[id] = queryCnt;
//            } else if (queryVal + tmp2[p2].v == dp[id]) {
//                cnt[id] = (cnt[id] + queryCnt) % MOD;
//            }
//        }
//    }
//    for (int i = l; i <= p1; i++) {
//        if (tmp2[i].left) {
//            clear(tmp2[i].d);
//        }
//    }
//}
//
//void cdq2(int l, int r) {
//    if (l == r) return;
//    int mid = (l + r) / 2;
//    cdq2(l, mid);
//    merge(l, mid, r);
//    cdq2(mid + 1, r);
//}
//
//void cdq1(int l, int r) {
//    if (l == r) return;
//    int mid = (l + r) / 2;
//    cdq1(l, mid);
//    for (int i = l; i <= r; i++) {
//        tmp1[i] = arr[i];
//        tmp1[i].left = (i <= mid);
//    }
//    sort(tmp1 + l, tmp1 + r + 1, Cmp2);
//    cdq2(l, r);
//    cdq1(mid + 1, r);
//}
//
//int lower(int num) {
//    int l = 1, r = s, ans = 1;
//    while (l <= r) {
//        int mid = (l + r) / 2;
//        if (sortd[mid] >= num) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sortd[i] = arr[i].d;
//    }
//    sort(sortd + 1, sortd + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sortd[s] != sortd[i]) {
//            sortd[++s] = sortd[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i].d = lower(arr[i].d);
//    }
//    sort(arr + 1, arr + n + 1, Cmp1);
//    int m = 1;
//    for (int i = 2; i <= n; i++) {
//        if (arr[m].a == arr[i].a && arr[m].b == arr[i].b &&
//            arr[m].c == arr[i].c && arr[m].d == arr[i].d) {
//            arr[m].v += arr[i].v;
//        } else {
//            arr[++m] = arr[i];
//        }
//    }
//    n = m;
//    for (int i = 1; i <= n; i++) {
//        arr[i].i = i;
//        dp[i] = arr[i].v;
//        cnt[i] = 1;
//    }
//    for (int i = 1; i <= s; i++) {
//        treeVal[i] = -INF;
//        treeCnt[i] = 0;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int m;
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].a >> arr[i].b >> arr[i].c >> arr[i].d >> arr[i].v;
//    }
//    prepare();
//    cdq1(1, n);
//    long long best = 0;
//    int ways = 0;
//    for (int i = 1; i <= n; i++) {
//        best = max(best, dp[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dp[i] == best) {
//            ways = (ways + cnt[i]) % MOD;
//        }
//    }
//    cout << best << '\n';
//    cout << ways << '\n';
//    return 0;
//}