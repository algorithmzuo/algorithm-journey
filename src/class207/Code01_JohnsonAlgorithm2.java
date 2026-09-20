package class207;

// Johnson全源最短路，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5905
// 如下实现是C++的版本，使用普通版本的dijkstra算法
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 10001;
//int INF = 1000000000;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int weight[MAXN];
//int cntg;
//
//int energy[MAXN];
//int updateCnt[MAXN];
//bool enterQue[MAXN];
//queue<int> que;
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
//    fill(energy + 1, energy + n + 1, INF);
//    energy[s] = 0;
//    updateCnt[s] = 1;
//    enterQue[s] = true;
//    que.push(s);
//    while (!que.empty()) {
//        int u = que.front();
//        que.pop();
//        enterQue[u] = false;
//        for (int ei = head[u], v, w; ei > 0; ei = nxt[ei]) {
//            v = to[ei];
//            w = weight[ei];
//            if (energy[v] > energy[u] + w) {
//                energy[v] = energy[u] + w;
//                if (!enterQue[v]) {
//                    if (++updateCnt[v] > n) {
//                        return true;
//                    }
//                    que.push(v);
//                    enterQue[v] = true;
//                }
//            }
//        }
//    }
//    return false;
//}
//
//void dijkstra(int s) {
//    fill(dist + 1, dist + n + 1, INF);
//    fill(vis + 1, vis + n + 1, false);
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
//    int virtualNode = n + 1;
//    for (int i = 1; i <= n; i++) {
//        addEdge(virtualNode, i, 0);
//    }
//    if (spfa(virtualNode)) {
//        cout << "-1" << '\n';
//    } else {
//        for (int u = 1; u <= n; u++) {
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                weight[e] += energy[u] - energy[v];
//            }
//        }
//        for (int u = 1; u <= n; u++) {
//            dijkstra(u);
//            ll ans = 0;
//            for (int v = 1; v <= n; v++) {
//                if (dist[v] == INF) {
//                    ans += 1LL * v * INF;
//                } else {
//                    ans += 1LL * v * (dist[v] - energy[u] + energy[v]);
//                }
//            }
//            cout << ans << '\n';
//        }
//    }
//    return 0;
//}