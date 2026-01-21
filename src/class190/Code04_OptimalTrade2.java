package class190;

// 最优贸易，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1073
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 1000001;
//const int INF = 1000000001;
//int n, m;
//int val[MAXN];
//int a[MAXM];
//int b[MAXM];
//
//int head1[MAXN];
//int nxt1[MAXM];
//int to1[MAXM];
//int cnt1;
//
//int head2[MAXN];
//int nxt2[MAXM];
//int to2[MAXM];
//int cnt2;
//
//int head3[MAXN];
//int nxt3[MAXM];
//int to3[MAXM];
//int cnt3;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//bool ins[MAXN];
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int minv[MAXN];
//int maxv[MAXN];
//int sccCnt;
//
//int indegree[MAXN];
//int que[MAXN];
//int dp[MAXN];
//bool reachEnd[MAXN];
//
//void addEdge1(int u, int v) {
//    nxt1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    nxt2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void addEdge3(int u, int v) {
//    nxt3[++cnt3] = head3[u];
//    to3[cnt3] = v;
//    head3[u] = cnt3;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    ins[u] = true;
//    for (int e = head1[u]; e > 0; e = nxt1[e]) {
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (ins[v]) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        minv[sccCnt] = INF;
//        maxv[sccCnt] = -INF;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            minv[sccCnt] = min(minv[sccCnt], val[pop]);
//            maxv[sccCnt] = max(maxv[sccCnt], val[pop]);
//            ins[pop] = false;
//        } while (pop != u);
//    }
//}
//
//void topo() {
//    int l = 1, r = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        dp[i] = INF;
//        if (indegree[i] == 0) {
//            que[++r] = i;
//        }
//    }
//    dp[belong[1]] = minv[belong[1]];
//    while (l <= r) {
//        int u = que[l++];
//        for (int e = head2[u]; e > 0; e = nxt2[e]) {
//            int v = to2[e];
//            if (dp[u] != INF) {
//                dp[v] = min(dp[v], min(dp[u], minv[v]));
//            }
//            if (--indegree[v] == 0) {
//                que[++r] = v;
//            }
//        }
//    }
//}
//
//void bfs() {
//    int l = 1, r = 0;
//    reachEnd[belong[n]] = true;
//    que[++r] = belong[n];
//    while (l <= r) {
//        int u = que[l++];
//        for (int e = head3[u]; e > 0; e = nxt3[e]) {
//            int v = to3[e];
//            if (!reachEnd[v]) {
//                reachEnd[v] = true;
//                que[++r] = v;
//            }
//        }
//    }
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
//            addEdge1(a[i], b[i]);
//        } else {
//            addEdge1(a[i], b[i]);
//            addEdge1(b[i], a[i]);
//        }
//    }
//    tarjan(1);
//    if (belong[n] == 0) {
//        cout << 0 << "\n";
//    } else {
//        for (int i = 1; i <= m; i++) {
//            int scc1 = belong[a[i]];
//            int scc2 = belong[b[i]];
//            if (scc1 > 0 && scc2 > 0 && scc1 != scc2) {
//                indegree[scc2]++;
//                addEdge2(scc1, scc2);
//                addEdge3(scc2, scc1);
//            }
//        }
//        topo();
//        bfs();
//        int ans = 0;
//        for (int i = 1; i <= sccCnt; i++) {
//            if (dp[i] != INF && reachEnd[i]) {
//                ans = max(ans, maxv[i] - dp[i]);
//            }
//        }
//        cout << ans << "\n";
//    }
//    return 0;
//}