package class190;

// 糖果，C++版
// 一共n个人，每人至少得到一个糖果，一共k条要求，要求有5种类型
// 1 u v : u的糖果 == v的糖果    2 u v : u的糖果 < v的糖果
// 3 u v : u的糖果 >= v的糖果    4 u v : u的糖果 > v的糖果
// 5 u v : u的糖果 <= v的糖果
// 如果无法满足所有要求打印-1，否则打印需要的最少糖果数
// 1 <= n、k <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3275
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//int n, k, m;
//int a[MAXM];
//int b[MAXM];
//int c[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int weight[MAXM];
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
//int sccSiz[MAXN];
//int sccCnt;
//
//int indegree[MAXN];
//ll dp[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
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
//        sccSiz[sccCnt] = 0;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sccSiz[sccCnt]++;
//        } while (pop != u);
//    }
//}
//
//bool condense() {
//    cntg = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        int w = c[i];
//        if (scc1 == scc2 && w > 0) {
//            return false;
//        }
//        if (scc1 != scc2) {
//            indegree[scc2]++;
//            addEdge(scc1, scc2, w);
//        }
//    }
//    return true;
//}
//
//ll dpOnDAG() {
//    for (int u = sccCnt; u > 0; u--) {
//        if (indegree[u] == 0) {
//            dp[u] = 1;
//        }
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            int w = weight[e];
//            dp[v] = max(dp[v], dp[u] + w);
//        }
//    }
//    ll ans = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        ans += dp[i] * sccSiz[i];
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> k;
//    m = 0;
//    for (int i = 1, op, u, v; i <= k; i++) {
//    	cin >> op >> u >> v;
//        if (op == 1) {
//            a[++m] = u; b[m] = v; c[m] = 0;
//            a[++m] = v; b[m] = u; c[m] = 0;
//        } else if (op == 2) {
//            a[++m] = u; b[m] = v; c[m] = 1;
//        } else if (op == 3) {
//            a[++m] = v; b[m] = u; c[m] = 0;
//        } else if (op == 4) {
//            a[++m] = v; b[m] = u; c[m] = 1;
//        } else {
//            a[++m] = u; b[m] = v; c[m] = 0;
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        addEdge(a[i], b[i], c[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = condense();
//    if (!check) {
//        cout << -1 << "\n";
//    } else {
//        ll ans = dpOnDAG();
//        cout << ans << "\n";
//    }
//    return 0;
//}