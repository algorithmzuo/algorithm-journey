package class208;

// HLPP算法，C++版
// 图中一共n个点，m条有向边，给定源点s和汇点t
// 给定每条有向边的起点、终点、容量
// 计算源点s到汇点t的网络最大流
// 1 <= n <= 1200
// 1 <= m <= 120000
// 边的容量为非负整数
// 测试链接 : https://www.luogu.com.cn/problem/P4722
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 1201;
//const int MAXM = 120001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int limit[MAXM << 1];
//int cntg;
//
//ll excess[MAXN];
//int dis[MAXN];
//int que[MAXN];
//int cur[MAXN];
//int gap[MAXN << 1];
//
//int bucket[MAXN << 1];
//int nextActive[MAXN];
//int highest;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    limit[cntg] = w;
//    head[u] = cntg;
//}
//
//void addActive(int u) {
//    highest = max(highest, dis[u]);
//    nextActive[u] = bucket[dis[u]];
//    bucket[dis[u]] = u;
//}
//
//void pushFlow(int u, int v, int e, ll k) {
//    excess[u] -= k;
//    excess[v] += k;
//    limit[e] -= k;
//    limit[e ^ 1] += k;
//}
//
//void bfs(int t) {
//    for (int i = 1; i <= n; i++) {
//        dis[i] = n + 1;
//        cur[i] = head[i];
//    }
//    dis[t] = 0;
//    int l = 1, r = 0;
//    que[++r] = t;
//    while (l <= r) {
//        int u = que[l++];
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (dis[v] == n + 1 && limit[e ^ 1] > 0) {
//                dis[v] = dis[u] + 1;
//                que[++r] = v;
//            }
//        }
//    }
//}
//
//void relabel(int u, int s, int t) {
//    if (dis[u] < n && gap[dis[u]] == 1) {
//        int h = dis[u];
//        for (int i = 1; i <= n; i++) {
//            if (i != s && i != t && dis[i] >= h && dis[i] < n) {
//                gap[dis[i]]--;
//                dis[i] = n + 1;
//                gap[dis[i]]++;
//                cur[i] = head[i];
//            }
//        }
//    } else {
//        int lowest = (n << 1) - 1;
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (u != v && limit[e] > 0) {
//                lowest = min(lowest, dis[v]);
//            }
//        }
//        gap[dis[u]]--;
//        dis[u] = lowest + 1;
//        gap[dis[u]]++;
//        cur[u] = head[u];
//    }
//}
//
//void discharge(int s, int t) {
//    int u = bucket[highest];
//    bucket[highest] = nextActive[u];
//    while (excess[u] > 0) {
//        for (int e = cur[u]; e > 0; e = nxt[e]) {
//            cur[u] = e;
//            int v = to[e];
//            int lim = limit[e];
//            if (dis[u] == dis[v] + 1 && lim > 0) {
//                if (v != s && v != t && excess[v] == 0) {
//                    addActive(v);
//                }
//                pushFlow(u, v, e, min(excess[u], (ll) lim));
//                if (excess[u] == 0) {
//                    return;
//                }
//            }
//        }
//        relabel(u, s, t);
//    }
//}
//
//ll maxflow(int s, int t) {
//    bfs(t);
//    if (dis[s] == n + 1) {
//        return 0;
//    }
//    dis[s] = n;
//    for (int i = 1; i <= n; i++) {
//        gap[dis[i]]++;
//    }
//    for (int e = head[s]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dis[v] < n) {
//            pushFlow(s, v, e, limit[e]);
//        }
//    }
//    for (int i = 0; i <= n << 1; i++) {
//        bucket[i] = 0;
//    }
//    highest = -1;
//    for (int i = 1; i <= n; i++) {
//        if (i != s && i != t && excess[i] > 0) {
//            addActive(i);
//        }
//    }
//    while (highest >= 0) {
//        if (bucket[highest] == 0) {
//            highest--;
//        } else {
//            discharge(s, t);
//        }
//    }
//    return excess[t];
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