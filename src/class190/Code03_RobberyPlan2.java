package class190;

// 劫掠计划，C++版
// 一共有n个城市，给定m条有向道路，每个城市给定拥有的钱数
// 给定起点城市s，给定p个有酒吧的城市，有酒吧的城市才能作为终点
// 路线必须从s出发到任意终点停止，重复经过城市的话，钱仅获得一次
// 题目保证一定存在这样的路线，打印能获得的最大钱数
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3627
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXM = 500001;
//const int INF = 1000000001;
//int n, m, s, p;
//
//int money[MAXN];
//bool isBar[MAXN];
//int a[MAXM];
//int b[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
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
//int sum[MAXN];
//bool hasBar[MAXN];
//int sccCnt;
//
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
//        sum[sccCnt] = 0;
//        hasBar[sccCnt] = false;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sum[sccCnt] += money[pop];
//            hasBar[sccCnt] |= isBar[pop];
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
//        dp[u] = -INF;
//    }
//    dp[belong[s]] = sum[belong[s]];
//    for (int u = sccCnt; u > 0; u--) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            dp[v] = max(dp[v], dp[u] + sum[v]);
//        }
//    }
//    int ans = 0;
//    for (int u = 1; u <= sccCnt; u++) {
//        if (hasBar[u]) {
//            ans = max(ans, dp[u]);
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> money[i];
//    }
//    cin >> s >> p;
//    for (int i = 1, x; i <= p; i++) {
//        cin >> x;
//        isBar[x] = true;
//    }
//    tarjan(s);
//    condense();
//    int ans = dpOnDAG();
//    cout << ans << "\n";
//    return 0;
//}