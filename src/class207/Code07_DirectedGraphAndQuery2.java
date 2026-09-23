package class207;

// 有向图与查询，C++版
// 一共n个点，编号1~n，给定m条有向边，没有重边和自环
// 每条边的格式 a b，表示存在从a到b的有向边
// 定义有向路径的代价，路径上所有点的编号最大值，包括起点和终点
// 一共q条查询，每条查询的格式 s t，保证 s != t
// 求从s到t的所有有向路径中，最小的路径代价，不存在路径输出-1
// 2 <= n <= 2000
// 0 <= m <= n * (n - 1)
// 1 <= q <= 10000
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc287_h
// 测试链接 : https://atcoder.jp/contests/abc287/tasks/abc287_h
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 2001;
//const int MAXQ = 10001;
//int n, m, q;
//
//int s[MAXQ];
//int t[MAXQ];
//
//bitset<MAXN> dp[MAXN];
//
//int ans[MAXQ];
//
//void floyd() {
//    for (int bridge = 1; bridge <= n; bridge++) {
//        for (int i = 1; i <= n; i++) {
//            if (dp[i][bridge]) {
//                dp[i] |= dp[bridge];
//            }
//        }
//        for (int i = 1; i <= q; i++) {
//            if (dp[s[i]][t[i]]) {
//                ans[i] = min(ans[i], max(bridge, max(s[i], t[i])));
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, a, b; i <= m; i++) {
//        cin >> a >> b;
//        dp[a][b] = true;
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> s[i] >> t[i];
//    }
//    int inf = n + 1;
//    for (int i = 1; i <= q; i++) {
//        ans[i] = inf;
//    }
//    floyd();
//    for (int i = 1; i <= q; i++) {
//        cout << (ans[i] == inf ? -1 : ans[i]) << '\n';
//    }
//    return 0;
//}