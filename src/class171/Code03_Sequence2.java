package class171;

// 序列，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4093
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int i, num, lv, rv;
//};
//
//bool CmpNum(Node a, Node b) {
//    return a.num < b.num;
//}
//
//bool CmpLv(Node a, Node b) {
//    return a.lv < b.lv;
//}
//
//const int MAXN = 100001;
//int n, m;
//int num[MAXN];
//int lv[MAXN];
//int rv[MAXN];
//
//Node arr[MAXN];
//int tree[MAXN];
//int dp[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void more(int i, int num) {
//    while (i <= n) {
//        tree[i] = max(tree[i], num);
//        i += lowbit(i);
//    }
//}
//
//int query(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret = max(ret, tree[i]);
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void clear(int i) {
//    while (i <= n) {
//        tree[i] = 0;
//        i += lowbit(i);
//    }
//}
//
//void merge(int l, int m, int r) {
//    for (int i = l; i <= r; i++) {
//        arr[i].i = i;
//        arr[i].num = num[i];
//        arr[i].lv = lv[i];
//        arr[i].rv = rv[i];
//    }
//    sort(arr + l, arr + m + 1, CmpNum);
//    sort(arr + m + 1, arr + r + 1, CmpLv);
//    int p1, p2;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].num <= arr[p2].lv) {
//            p1++;
//            more(arr[p1].rv, dp[arr[p1].i]);
//        }
//        dp[arr[p2].i] = max(dp[arr[p2].i], query(arr[p2].num) + 1);
//    }
//    for (int i = l; i <= p1; i++) {
//        clear(arr[i].rv);
//    }
//}
//
//void cdq(int l, int r) {
//    if (l == r) {
//        return;
//    }
//    int mid = (l + r) / 2;
//    cdq(l, mid);
//    merge(l, mid, r);
//    cdq(mid + 1, r);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> num[i];
//        lv[i] = num[i];
//        rv[i] = num[i];
//    }
//    for (int i = 1, idx, val; i <= m; i++) {
//        cin >> idx >> val;
//        lv[idx] = min(lv[idx], val);
//        rv[idx] = max(rv[idx], val);
//    }
//    for (int i = 1; i <= n; i++) {
//        dp[i] = 1;
//    }
//    cdq(1, n);
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        ans = max(ans, dp[i]);
//    }
//    cout << ans << '\n';
//    return 0;
//}