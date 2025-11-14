package class183;

// 权值和为k的路径的最少边数，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定数字k，要求路径权值和等于k，并且边的数量最小
// 打印最小边数，如果不存在路径打印-1，注意点的编号从0开始
// 1 <= n <= 2 * 10^5
// 0 <= 边权、k <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4149
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int dis, edge;
//};
//
//const int MAXN = 200001;
//const int MAXK = 1000001;
//const int INF = 1000000001;
//int n, k;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Node arr[MAXN];
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
//int getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            siz[u] += getSize(v, u);
//        }
//    }
//    return siz[u];
//}
//
//int getCentroid(int u, int fa) {
//    int half = getSize(u, fa) >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void dfs(int u, int fa, int dis, int edge) {
//    if (dis > k) {
//        return;
//    }
//    arr[++cnta] = { dis, edge };
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dis + weight[e], edge + 1);
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
//            dfs(v, u, weight[e], 1);
//            for (int i = tmp + 1; i <= cnta; i++) {
//                ans = min(ans, dp[k - arr[i].dis] + arr[i].edge);
//            }
//            for (int i = tmp + 1; i <= cnta; i++) {
//                dp[arr[i].dis] = min(dp[arr[i].dis], arr[i].edge);
//            }
//        }
//    }
//    for (int i = 1; i <= cnta; i++) {
//        dp[arr[i].dis] = INF;
//    }
//    return ans;
//}
//
//int solve(int u) {
//    int ans = INF;
//    vis[u] = true;
//    ans = min(ans, calc(u));
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans = min(ans, solve(getCentroid(v, u)));
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
//    fill(dp, dp + MAXK, INF);
//    int ans = solve(getCentroid(1, 0));
//    if (ans == INF) {
//        ans = -1;
//    }
//    cout << ans << '\n';
//    return 0;
//}