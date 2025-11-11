package class183;

// 权值和为k的路径的最少边数，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4149
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXK = 1000001;
//const int INF = 1000000001;
//int n, k, total;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//int siz[MAXN];
//int maxPart[MAXN];
//int centroid;
//
//int sumArr[MAXN];
//int edgeArr[MAXN];
//int cnta;
//
//int dp[MAXK];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void getCentroid(int u, int fa) {
//    siz[u] = 1;
//    maxPart[u] = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getCentroid(v, u);
//            siz[u] += siz[v];
//            maxPart[u] = max(maxPart[u], siz[v]);
//        }
//    }
//    maxPart[u] = max(maxPart[u], total - siz[u]);
//    if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
//        centroid = u;
//    }
//}
//
//void getPath(int u, int fa, int sum, int edge) {
//    if (sum > k) {
//        return;
//    }
//    sumArr[++cnta] = sum;
//    edgeArr[cnta] = edge;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getPath(v, u, sum + weight[e], edge + 1);
//        }
//    }
//}
//
//int calc(int u) {
//    int ans = INF;
//    cnta = 0;
//    dp[0] = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            int tmp = cnta;
//            getPath(v, u, weight[e], 1);
//            for (int i = tmp + 1; i <= cnta; i++) {
//                ans = min(ans, dp[k - sumArr[i]] + edgeArr[i]);
//            }
//            for (int i = tmp + 1; i <= cnta; i++) {
//                dp[sumArr[i]] = min(dp[sumArr[i]], edgeArr[i]);
//            }
//        }
//    }
//    for (int i = 1; i <= cnta; i++) {
//        dp[sumArr[i]] = INF;
//    }
//    return ans;
//}
//
//int compute(int u) {
//    int ans = INF;
//    vis[u] = true;
//    ans = min(ans, calc(u));
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            total = siz[v];
//            centroid = 0;
//            getCentroid(v, 0);
//            ans = min(ans, compute(centroid));
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> k;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        u++;
//        v++;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    total = n;
//    centroid = 0;
//    getCentroid(1, 0);
//    fill(dp, dp + MAXK, INF);
//    int ans = compute(centroid);
//    if (ans == INF) {
//        ans = -1;
//    }
//    cout << ans << '\n';
//    return 0;
//}