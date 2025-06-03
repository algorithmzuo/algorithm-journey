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
//    int i, v, min, max;
//};
//
//bool CmpI(Node a, Node b) {
//    return a.i < b.i;
//}
//
//bool CmpV(Node a, Node b) {
//    return a.v < b.v;
//}
//
//bool CmpMin(Node a, Node b) {
//    return a.min < b.min;
//}
//
//const int MAXN = 100001;
//int n, m;
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
//    sort(arr + l, arr + m + 1, CmpV);
//    sort(arr + m + 1, arr + r + 1, CmpMin);
//    int p1 = l - 1, p2 = m + 1;
//    for (; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].v <= arr[p2].min) {
//            p1++;
//            more(arr[p1].max, dp[arr[p1].i]);
//        }
//        dp[arr[p2].i] = max(dp[arr[p2].i], query(arr[p2].v) + 1);
//    }
//    for (int i = l; i <= p1; i++) {
//        clear(arr[i].max);
//    }
//    sort(arr + l, arr + r + 1, CmpI);
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
//        arr[i].i = i;
//        cin >> arr[i].v;
//        arr[i].min = arr[i].v;
//        arr[i].max = arr[i].v;
//        dp[i] = 1;
//    }
//    for (int i = 1, idx, num; i <= m; i++) {
//        cin >> idx >> num;
//        arr[idx].min = min(arr[idx].min, num);
//        arr[idx].max = max(arr[idx].max, num);
//    }
//    cdq(1, n);
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        ans = max(ans, dp[i]);
//    }
//    cout << ans << '\n';
//    return 0;
//}