package class191;

// 贝尔敦道路，C++版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 每条无向边需要指定一个方向，变成有向边，还要保证任意两点的连通性
// 如果不存在方案打印0，如果存在方案，打印m条有向边
// 可以任意次序打印有向边，如果方案不只一种，打印其中一种即可
// 1 <= n <= 10^5
// 1 <= m <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF118E
// 测试链接 : https://codeforces.com/problemset/problem/118/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 300001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//bool check;
//int ans1[MAXM];
//int ans2[MAXM];
//int cnta;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0 || dfn[v] < dfn[u]) {
//            cnta++;
//            ans1[cnta] = u;
//            ans2[cnta] = v;
//        }
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            low[u] = min(low[u], low[v]);
//            if (low[v] > dfn[u]) {
//                check = false;
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    check = true;
//    tarjan(1, 0);
//    if (!check) {
//        cout << 0 << "\n";
//    } else {
//        for (int i = 1; i <= m; i++) {
//            cout << ans1[i] << " " << ans2[i] << "\n";
//        }
//    }
//    return 0;
//}