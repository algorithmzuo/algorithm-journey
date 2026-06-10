package class200;

// 仙人掌加边方案数，C++版
// 给定n个点、m条边的无向连通图，输入保证没有自环、没有重边
// 可以在图中加入一些新的边，如果加入后得到的图是仙人掌，就叫有效的加边方案
// 允许一条边也不加，但不允许加边后，形成自环或二元环，计算有效的加边方案数
// 答案对 998244353 取余，每次输入可能有多组测试数据
// 1 <= n <= 5 * 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3687
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const int MAXM = 1000001;
//const int MOD = 998244353;
//int t, n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//bool cycleEdge[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int top;
//
//int fromEdge[MAXN];
//int cycleCnt[MAXN];
//
//bool vis[MAXN];
//ll f[MAXN];
//ll dp[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    cycleEdge[cntg] = false;
//    head[u] = cntg;
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            fromEdge[v] = e;
//            if (low[v] < dfn[u]) {
//                low[u] = min(low[u], low[v]);
//                cycleCnt[u]++;
//            } else if (low[v] > dfn[u]) {
//                top--;
//            } else {
//                int pop;
//                int edge = fromEdge[u];
//                cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
//                do {
//                    pop = sta[top--];
//                    edge = fromEdge[pop];
//                    cycleEdge[edge] = cycleEdge[edge ^ 1] = true;
//                } while (pop != v);
//            }
//        } else if (dfn[v] < dfn[u]) {
//            fromEdge[v] = e;
//            low[u] = min(low[u], dfn[v]);
//            cycleCnt[u]++;
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    vis[u] = true;
//    ll ans = 1;
//    int son = fa == 0 ? 0 : 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !cycleEdge[e]) {
//            dpOnTree(v, u);
//            ans = ans * dp[v] % MOD;
//            son++;
//        }
//    }
//    dp[u] = ans * f[son] % MOD;
//}
//
//void prepare() {
//    f[0] = f[1] = 1;
//    for (int i = 2; i <= n; i++) {
//        f[i] = (f[i - 1] + f[i - 2] * (i - 1)) % MOD;
//    }
//    for (int i = 1; i <= n; i++) {
//        head[i] = dfn[i] = low[i] = fromEdge[i] = cycleCnt[i] = 0;
//        vis[i] = false;
//        dp[i] = 0;
//    }
//    cntg = 1;
//    cntd = top = 0;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> m;
//        prepare();
//        for (int i = 1, u, v; i <= m; i++) {
//            cin >> u >> v;
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//        tarjan(1, 0);
//        bool check = true;
//        for (int i = 1; i <= n; i++) {
//            if (cycleCnt[i] >= 2) {
//                check = false;
//                break;
//            }
//        }
//        if (check) {
//            ll ans = 1;
//            for (int i = 1; i <= n; i++) {
//                if (!vis[i]) {
//                    dpOnTree(i, 0);
//                    ans = ans * dp[i] % MOD;
//                }
//            }
//            cout << ans << "\n";
//        } else {
//            cout << 0 << "\n";
//        }
//    }
//    return 0;
//}