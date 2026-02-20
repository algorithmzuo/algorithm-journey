package class192;

// 建造军营，C++版
// 一共n个城市、m条道路，道路是无向边，保证所有城市连通
// 你可以选择任何城市，在城市内建造军营，至少要选一座城市
// 你可以选择任何道路，在道路上派兵看守，也可以一条都不选
// 选择的城市集合 + 选择的道路集合，被认为是一种方案
// 敌人会袭击任意一条道路，如果有兵看守就不会被切断，否则会被切断
// 敌人袭击之后，如果造成任意两座军营无法连通，那么算你失败
// 确保不会失败的情况下，计算方案数，答案对 1000000007 取余
// 1 <= n <= 5 * 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P8867
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
//const int MOD = 1000000007;
//int n, m;
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
//int ebccSiz[MAXN];
//int ebccCnt;
//
//ll power2[MAXM];
//ll dp[MAXN];
//int cut[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
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
//            low[u] = min(low[u], low[v]);
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//    if (dfn[u] == low[u]) {
//        ebccCnt++;
//        ebccSiz[ebccCnt] = 0;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = ebccCnt;
//            ebccSiz[ebccCnt]++;
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= ebccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int ebcc1 = belong[a[i]];
//        int ebcc2 = belong[b[i]];
//        if (ebcc1 != ebcc2) {
//            addEdge(ebcc1, ebcc2);
//            addEdge(ebcc2, ebcc1);
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    cut[u] = 0;
//    dp[u] = power2[ebccSiz[u]] - 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dpOnTree(v, u);
//            dp[u] = (dp[u] * power2[cut[v] + 1] % MOD + power2[cut[u]] * dp[v] % MOD + dp[u] * dp[v] % MOD) % MOD;
//            cut[u] += cut[v] + 1;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cntg = 1;
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//        addEdge(b[i], a[i]);
//    }
//    tarjan(1, 0);
//    condense();
//    power2[0] = 1;
//    for (int i = 1; i <= m; i++) {
//        power2[i] = power2[i - 1] * 2 % MOD;
//    }
//    dpOnTree(1, 0);
//    ll ans = dp[1] * power2[m - cut[1]] % MOD;
//    for (int i = 2; i <= ebccCnt; i++) {
//        ans = (ans + dp[i] * power2[m - cut[i] - 1] % MOD) % MOD;
//    }
//    cout << ans << "\n";
//    return 0;
//}