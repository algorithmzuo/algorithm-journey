package class208;

// Dinic算法，C++版
// 图中一共n个点，m条有向边，给定源点s和汇点t
// 给定每条有向边的起点、终点、容量
// 计算源点s到汇点t的网络最大流
// 1 <= n <= 200
// 1 <= m <= 5000
// 边的容量为非负整数
// 测试链接 : https://www.luogu.com.cn/problem/P3376
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 201;
//const int MAXM = 5001;
//ll INF = 1LL << 60;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int limit[MAXM << 1];
//int cntg;
//
//int dis[MAXN];
//int que[MAXN];
//int cur[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    limit[cntg] = w;
//    head[u] = cntg;
//}
//
//void bfs(int s) {
//    for (int i = 1; i <= n; i++) {
//        dis[i] = n;
//        cur[i] = head[i];
//    }
//    dis[s] = 0;
//    int l = 1, r = 0;
//    que[++r] = s;
//    while (l <= r) {
//        int u = que[l++];
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            int lim = limit[e];
//            if (dis[v] == n && lim > 0) {
//                dis[v] = dis[u] + 1;
//                que[++r] = v;
//            }
//        }
//    }
//}
//
//ll dfs(int u, ll rest, int t) {
//    if (u == t) {
//        return rest;
//    }
//    ll flow = 0;
//    for (int e = cur[u]; e > 0 && rest > 0; e = nxt[e]) {
//        cur[u] = e;
//        int v = to[e];
//        ll c = min(rest, (ll) limit[e]);
//        if (dis[u] + 1 == dis[v] && c > 0) {
//            ll k = dfs(v, c, t);
//            flow += k;
//            rest -= k;
//            limit[e] -= k;
//            limit[e ^ 1] += k;
//        }
//    }
//    if (flow == 0) {
//        dis[u] = n;
//    }
//    return flow;
//}
//
//ll maxflow(int s, int t) {
//    ll ans = 0;
//    bfs(s);
//    while (dis[t] != n) {
//        ans += dfs(s, INF, t);
//        bfs(s);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int s, t;
//    cin >> n >> m >> s >> t;
//    cntg = 1;
//    for (int i = 1, u, v, w; i <= m; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, 0);
//    }
//    ll ans = maxflow(s, t);
//    cout << ans << '\n';
//    return 0;
//}