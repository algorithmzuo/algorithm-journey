package class210;

// 方格取数加强版，C++版
// 给定n * n的矩阵，每个格子的值是非负整数，表示该格子的收益
// 从(1, 1)到(n, n)，一共走k次，每步只能向右或向下走一格
// 在这k次行走中，每个格子的收益只在第一次经过时获得
// 求能获得的最大总收益
// 1 <= n <= 50
// 0 <= k <= 10
// 0 <= 格子的值 <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P2045
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXG = 51;
//const int MAXN = 5001;
//const int MAXM = 10001;
//int INF = 1000000001;
//int n;
//
//int mat[MAXG][MAXG];
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int weight[MAXM << 1];
//int cost[MAXM << 1];
//int cntg;
//
//int flow[MAXN];
//int pre[MAXN];
//
//queue<int> que;
//int h[MAXN];
//int update[MAXN];
//bool enter[MAXN];
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
//int dist[MAXN];
//bool vis[MAXN];
//
//int maxflow, mincost;
//
//void addEdge(int u, int v, int w, int c) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    cost[cntg] = c;
//    head[u] = cntg;
//}
//
//bool spfa(int s) {
//    for (int i = 1; i <= n; i++) {
//        h[i] = INF;
//        update[i] = 0;
//        enter[i] = false;
//    }
//    h[s] = 0;
//    update[s] = 1;
//    enter[s] = true;
//    que = queue<int>();
//    que.push(s);
//    while (!que.empty()) {
//        int u = que.front();
//        que.pop();
//        enter[u] = false;
//        for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
//            v = to[e];
//            w = weight[e];
//            c = cost[e];
//            if (w > 0 && h[v] > h[u] + c) {
//                h[v] = h[u] + c;
//                if (!enter[v]) {
//                    if (++update[v] > n) {
//                        return true;
//                    }
//                    que.push(v);
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
//        flow[i] = -1;
//        vis[i] = false;
//    }
//    flow[s] = INF;
//    dist[s] = 0;
//    heap = priority_queue<HeapNode>();
//    heap.push({0, s});
//    while (!heap.empty()) {
//        HeapNode cur = heap.top();
//        heap.pop();
//        int d = cur.dist;
//        int u = cur.u;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u], v, w, c; e > 0; e = nxt[e]) {
//                v = to[e];
//                w = weight[e];
//                c = cost[e] + h[u] - h[v];
//                if (w > 0 && !vis[v] && dist[v] > d + c) {
//                    dist[v] = dist[u] + c;
//                    flow[v] = min(w, flow[u]);
//                    pre[v] = e;
//                    heap.push({dist[v], v});
//                }
//            }
//        }
//    }
//}
//
//bool ssp(int s, int t) {
//    bool negCycle = spfa(s);
//    if (negCycle) {
//        return true;
//    }
//    maxflow = mincost = 0;
//    dijkstra(s);
//    while (flow[t] != -1) {
//        maxflow += flow[t];
//        for (int u = t; u != s; u = to[pre[u] ^ 1]) {
//            weight[pre[u]] -= flow[t];
//            weight[pre[u] ^ 1] += flow[t];
//            mincost += flow[t] * cost[pre[u]];
//        }
//        for (int i = 1; i <= n; i++) {
//            if (dist[i] != INF) {
//                h[i] += dist[i];
//            }
//        }
//        dijkstra(s);
//    }
//    return false;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int side, k;
//    cin >> side >> k;
//    for (int i = 1; i <= side; i++) {
//        for (int j = 1; j <= side; j++) {
//            cin >> mat[i][j];
//        }
//    }
//    int ans = 0;
//    if (k > 0) {
//        int cells = side * side;
//        n = cells << 1;
//        int s = 1;
//        int t = n;
//        cntg = 1;
//        for (int i = 1; i <= side; i++) {
//            for (int j = 1; j <= side; j++) {
//                int u = (i - 1) * side + j;
//                int v = cells + u;
//                int c = mat[i][j];
//                addEdge(u, v, 1, -c);
//                addEdge(v, u, 0, c);
//                addEdge(u, v, k - 1, 0);
//                addEdge(v, u, 0, 0);
//                if (j < side) {
//                    addEdge(v, u + 1, k, 0);
//                    addEdge(u + 1, v, 0, 0);
//                }
//                if (i < side) {
//                    addEdge(v, u + side, k, 0);
//                    addEdge(u + side, v, 0, 0);
//                }
//            }
//        }
//        ssp(s, t);
//        ans = -mincost;
//    }
//    cout << ans << '\n';
//    return 0;
//}