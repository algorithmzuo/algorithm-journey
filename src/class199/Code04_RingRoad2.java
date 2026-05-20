package class199;

// 城市环路，C++版
// 图中有n个点、n条无向边，每个点有点权，图是一棵基环树
// 图中任意相邻两点不能同时选择，先得到最大的点权累加和
// 然后乘以题目给定的系数k，打印结果
// 1 <= n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1453
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//using namespace std;
//using ll = long long;
//
//const int MAXN = 100001;
//int n;
//double k;
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
//int dp[MAXN][2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (e != (preEdge ^ 1)) {
//            if (dfn[v] == 0) {
//                dfs(v, e);
//            } else if (dfn[u] < dfn[v]) {
//                x = u;
//                y = v;
//                skip = e >> 1;
//            }
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    dp[u][0] = 0;
//    dp[u][1] = arr[u];
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && (e >> 1) != skip) {
//            dpOnTree(v, u);
//            dp[u][0] += max(dp[v][0], dp[v][1]);
//            dp[u][1] += dp[v][0];
//        }
//    }
//}
//
//ll compute() {
//    x = y = 0;
//    dfs(1, 0);
//    dpOnTree(x, 0);
//    ll ans = dp[x][0];
//    dpOnTree(y, 0);
//    ans = max(ans, 1ll * dp[y][0]);
//    return ans;
//}
//
//int main() {
//    scanf("%d", &n);
//    cntg = 1;
//    for (int i = 1; i <= n; i++) {
//        scanf("%d", &arr[i]);
//    }
//    for (int i = 1, u, v; i <= n; i++) {
//        scanf("%d%d", &u, &v);
//        u++;
//        v++;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    scanf("%lf", &k);
//    ll ans = compute();
//    printf("%.1f\n", k * ans);
//    return 0;
//}