package class171;

// 奶牛音量和，C++版
// 一共有n只奶牛，每只奶牛给定，听力v、坐标x
// 任何一对奶牛产生的音量 = max(vi, vj) * 两只奶牛的距离
// 一共有n * (n - 1) / 2对奶牛，打印音量总和
// 1 <= n、v、x <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P5094
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int v, x;
//};
//
//bool NodeCmp(Node a, Node b) {
//    return a.v < b.v;
//}
//
//const int MAXN = 50001;
//int n;
//Node arr[MAXN];
//Node tmp[MAXN];
//
//long long merge(int l, int m, int r) {
//    int p1, p2;
//    long long sum1 = 0, sum2 = 0, ans = 0;
//    for (p1 = l; p1 <= m; p1++) {
//        sum1 += arr[p1].x;
//    }
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].x < arr[p2].x) {
//            p1++;
//            sum1 -= arr[p1].x;
//            sum2 += arr[p1].x;
//        }
//        ans += (1LL * (p1 - l + 1) * arr[p2].x - sum2 + sum1 - 1LL * (m - p1) * arr[p2].x) * arr[p2].v;
//    }
//    p1 = l;
//    p2 = m + 1;
//    int i = l;
//    while (p1 <= m && p2 <= r) {
//        tmp[i++] = arr[p1].x <= arr[p2].x ? arr[p1++] : arr[p2++];
//    }
//    while (p1 <= m) {
//        tmp[i++] = arr[p1++];
//    }
//    while (p2 <= r) {
//        tmp[i++] = arr[p2++];
//    }
//    for (i = l; i <= r; i++) {
//        arr[i] = tmp[i];
//    }
//    return ans;
//}
//
//long long cdq(int l, int r) {
//    if (l == r) {
//        return 0;
//    }
//    int mid = (l + r) / 2;
//    return cdq(l, mid) + cdq(mid + 1, r) + merge(l, mid, r);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].v >> arr[i].x;
//    }
//    sort(arr + 1, arr + n + 1, NodeCmp);
//    cout << cdq(1, n) << '\n';
//    return 0;
//}