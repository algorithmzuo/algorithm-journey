package class190;

// 缩点结合动态规划模版题，C++版
// 给定一张n个点，m条边的有向图，每个点给定非负点权
// 如果重复经过一个点，点权只获得一次
// 找到一条路径，使得点权累加和最大，打印这个值
// 1 <= n <= 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3387
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXM = 100001;
//int n, m;
//
//int arr[MAXN];
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
//int sccCnt;
//
//int indegree[MAXN];
//int que[MAXN];
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
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sum[sccCnt] += arr[pop];
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
//        if (scc1 != scc2) {
//            indegree[scc2]++;
//            addEdge(scc1, scc2);
//        }
//    }
//}
//
//int topo() {
//    int l = 1, r = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        if (indegree[i] == 0) {
//            dp[i] = sum[i];
//            que[++r] = i;
//        }
//    }
//    while (l <= r) {
//        int u = que[l++];
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            dp[v] = max(dp[v], dp[u] + sum[v]);
//            if (--indegree[v] == 0) {
//                que[++r] = v;
//            }
//        }
//    }
//    int ans = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        ans = max(ans, dp[i]);
//    }
//    return ans;
//}
//
//int dpOnDAG() {
//    for (int u = sccCnt; u > 0; u--) {
//        if (indegree[u] == 0) {
//            dp[u] = sum[u];
//        }
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            dp[v] = max(dp[v], dp[u] + sum[v]);
//        }
//    }
//    int ans = 0;
//    for (int u = 1; u <= sccCnt; u++) {
//        ans = max(ans, dp[u]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    condense();
//    // int ans = topo();
//    int ans = dpOnDAG();
//    cout << ans << "\n";
//    return 0;
//}