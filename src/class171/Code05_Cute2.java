package class171;

// 德丽莎世界第一可爱，C++版
// 一共有n个怪兽，每个怪兽有a、b、c、d四个能力值，以及打败之后的收益v
// 可以选择任意顺序打怪兽，每次打的怪兽的四种能力值都不能小于上次打的怪兽
// 打印能获得的最大收益，可能所有怪兽收益都是负数，那也需要至少打一只怪兽
// 1 <= n <= 5 * 10^4
// -10^5 <= a、b、c、d <= +10^5
// -10^9 <= v <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5621
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
//const int MAXN = 50001;
//const long long INF = 1e18 + 1;
//int n, s;
//
//Node arr[MAXN];
//int sortd[MAXN];
//
//Node tmp1[MAXN];
//Node tmp2[MAXN];
//long long tree[MAXN];
//long long dp[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void more(int i, long long num) {
//    while (i <= s) {
//        tree[i] = max(tree[i], num);
//        i += lowbit(i);
//    }
//}
//
//long long query(int i) {
//    long long ret = -INF;
//    while (i > 0) {
//        ret = max(ret, tree[i]);
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void clear(int i) {
//    while (i <= s) {
//        tree[i] = -INF;
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
//    int p1, p2;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && tmp2[p1 + 1].c <= tmp2[p2].c) {
//            p1++;
//            if (tmp2[p1].left) {
//                more(tmp2[p1].d, dp[tmp2[p1].i]);
//            }
//        }
//        if (!tmp2[p2].left) {
//            dp[tmp2[p2].i] = max(dp[tmp2[p2].i], query(tmp2[p2].d) + tmp2[p2].v);
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
//        tmp1[i].left = i <= mid;
//    }
//    sort(tmp1 + l, tmp1 + r + 1, Cmp2);
//    cdq2(l, r);
//    cdq1(mid + 1, r);
//}
//
//int lower(long long num) {
//    int l = 1, r = s, ans = 1;
//    while (l <= r) {
//        int m = (l + r) / 2;
//        if (sortd[m] >= num) {
//            ans = m;
//            r = m - 1;
//        } else {
//            l = m + 1;
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
//            if (arr[i].v > 0) {
//                arr[m].v += arr[i].v;
//            }
//        } else {
//            arr[++m] = arr[i];
//        }
//    }
//    n = m;
//    for (int i = 1; i <= n; i++) {
//        arr[i].i = i;
//        dp[i] = arr[i].v;
//    }
//    for (int i = 1; i <= s; i++) {
//        tree[i] = -INF;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].a >> arr[i].b >> arr[i].c >> arr[i].d >> arr[i].v;
//    }
//    prepare();
//    cdq1(1, n);
//    long long ans = -INF;
//    for (int i = 1; i <= n; i++) {
//        ans = max(ans, dp[i]);
//    }
//    cout << ans << '\n';
//    return 0;
//}