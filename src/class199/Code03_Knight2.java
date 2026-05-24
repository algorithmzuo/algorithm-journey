package class199;

// 骑士，C++版
// 图中有n个点、n条无向边，每个点有点权
// 图中可能有多个连通块，保证每个连通块都是一棵基环树
// 图中任意相邻两点不能同时选择，计算能得到的最大点权累加和
// 1 <= n、点权 <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P2607
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 1000001;
//int n;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int cntd;
//
//int x, y, skip;
//
//ll dp[MAXN][2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            dfs(v);
//        } else if (dfn[u] < dfn[v]) {
//            x = u;
//            y = v;
//            skip = (e + 1) >> 1;
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    dp[u][0] = 0;
//    dp[u][1] = arr[u];
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && (e + 1) >> 1 != skip) {
//            dpOnTree(v, u);
//            dp[u][0] += max(dp[v][0], dp[v][1]);
//            dp[u][1] += dp[v][0];
//        }
//    }
//}
//
//ll compute() {
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            x = y = 0;
//            dfs(i);
//            dpOnTree(x, 0);
//            ll cur = dp[x][0];
//            dpOnTree(y, 0);
//            cur = max(cur, dp[y][0]);
//            ans += cur;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int x = 1, y; x <= n; x++) {
//        cin >> arr[x];
//        cin >> y;
//        addEdge(x, y);
//        addEdge(y, x);
//    }
//    ll ans = compute();
//    cout << ans << "\n";
//    return 0;
//}