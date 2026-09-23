package class207;

// Johnson全源最短路，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5905
// 如下实现是C++的版本，普通堆实现的Dijkstra算法
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 10001;
//const int MAXQ = 5000001;
//int INF = 1000000000;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int weight[MAXN];
//int cntg;
//
//int h[MAXN];
//int update[MAXN];
//bool enter[MAXN];
//int que[MAXQ];
//
//int dist[MAXN];
//bool vis[MAXN];
//
//struct HeapNode {
//    int dist;
//    int u;
//
//    bool operator<(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//priority_queue<HeapNode> heap;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//bool spfa(int s) {
//    for (int i = 1; i <= n; i++) {
//        h[i] = INF;
//    }
//    h[s] = 0;
//    update[s] = 1;
//    enter[s] = true;
//    int ql = 1, qr = 0;
//    que[++qr] = s;
//    while (ql <= qr) {
//        int u = que[ql++];
//        enter[u] = false;
//        for (int ei = head[u], v, w; ei > 0; ei = nxt[ei]) {
//            v = to[ei];
//            w = weight[ei];
//            if (h[v] > h[u] + w) {
//                h[v] = h[u] + w;
//                if (!enter[v]) {
//                    if (++update[v] > n) {
//                        return true;
//                    }
//                    que[++qr] = v;
//                    enter[v] = true;
//                }
//            }
//        }
//    }
//    return false;
//}
//
//void dijkstra(int s) {
//    for (int i = 1; i <= n; i++) {
//        dist[i] = INF;
//        vis[i] = false;
//    }
//    dist[s] = 0;
//    heap.push({0, s});
//    while (!heap.empty()) {
//        HeapNode cur = heap.top();
//        heap.pop();
//        int d = cur.dist;
//        int u = cur.u;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                int w = weight[e];
//                if (!vis[v] && dist[v] > d + w) {
//                    dist[v] = d + w;
//                    heap.push({dist[v], v});
//                }
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    n = 0;
//    m = 0;
//    cin >> n >> m;
//    for (int i = 1, u, v, w; i <= m; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//    }
//    int virtualNode = 0;
//    for (int i = 1; i <= n; i++) {
//        addEdge(virtualNode, i, 0);
//    }
//    if (spfa(virtualNode)) {
//        cout << "-1" << '\n';
//    } else {
//        for (int u = 1; u <= n; u++) {
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                weight[e] += h[u] - h[v];
//            }
//        }
//        for (int u = 1; u <= n; u++) {
//            dijkstra(u);
//            ll ans = 0;
//            for (int v = 1; v <= n; v++) {
//                if (dist[v] == INF) {
//                    ans += 1LL * v * INF;
//                } else {
//                    ans += 1LL * v * (dist[v] - h[u] + h[v]);
//                }
//            }
//            cout << ans << '\n';
//        }
//    }
//    return 0;
//}