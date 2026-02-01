package class190;

// 最优贸易，C++版
// 一共有n个城市，每个城市给定水晶球的销售价格，给定m条道路
// 格式 a b op : op为1代表a到b的单向路，否则表示双向路
// 你要从1号城市出发，可以任选道路，最终来到n号城市，沿途可以买卖一次水晶球
// 在途中你可以任选一座城市买入，可以立即卖出，或者在之后的任何城市卖出
// 如果从1号城市能到达n号城市，打印挣到的最大钱数，如果不能到达打印0
// 1 <= n <= 10^5
// 1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1073
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 500001;
//const int INF = 1000000001;
//int n, m;
//
//int val[MAXN];
//int a[MAXM];
//int b[MAXM];
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
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccMin[MAXN];
//int sccMax[MAXN];
//int sccCnt;
//
//int premin[MAXN];
//int dp[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (belong[v] == 0) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        sccMin[sccCnt] = INF;
//        sccMax[sccCnt] = -INF;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sccMin[sccCnt] = min(sccMin[sccCnt], val[pop]);
//            sccMax[sccCnt] = max(sccMax[sccCnt], val[pop]);
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        if (scc1 > 0 && scc2 > 0 && scc1 != scc2) {
//            addEdge(scc1, scc2);
//        }
//    }
//}
//
//int dpOnDAG() {
//    for (int u = 1; u <= sccCnt; u++) {
//        premin[u] = INF;
//        dp[u] = -INF;
//    }
//    int s = belong[1];
//    premin[s] = sccMin[s];
//    dp[s] = sccMax[s] - sccMin[s];
//    for (int u = sccCnt; u > 0; u--) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            premin[v] = min(premin[v], min(premin[u], sccMin[v]));
//            dp[v] = max(dp[v], max(dp[u], sccMax[v] - premin[v]));
//        }
//    }
//    return dp[belong[n]];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> val[i];
//    }
//    for (int i = 1, op; i <= m; i++) {
//        cin >> a[i] >> b[i] >> op;
//        if (op == 1) {
//            addEdge(a[i], b[i]);
//        } else {
//            addEdge(a[i], b[i]);
//            addEdge(b[i], a[i]);
//        }
//    }
//    tarjan(1);
//    if (belong[n] == 0) {
//        cout << 0 << "\n";
//    } else {
//        condense();
//        int ans = dpOnDAG();
//        cout << ans << "\n";
//    }
//    return 0;
//}