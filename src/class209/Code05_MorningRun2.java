package class209;

// 晨跑，C++版
// 给定n个点、m条有向边，源点为1，汇点为n
// 每条有向边给定起点u、终点v、单位费用c，每条边的容量都是1
// 除源汇外，其他点最多允许通过1单位的流量，源汇没有这种限制
// 求从1到n的最大流，打印最大流数值，以及该最大流对应的最小费用
// 2 <= n <= 200
// 1 <= m <= 2 * 10^4
// 1 <= c <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2153
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 401;
//const int MAXM = 30001;
//int INF = 1000000001;
//int n;
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
//    int k, m;
//    cin >> k >> m;
//    n = k << 1;
//    int s = k + 1;
//    int t = k;
//    cntg = 1;
//    for (int i = 2; i < k; i++) {
//        addEdge(i, i + k, 1, 0);
//        addEdge(i + k, i, 0, 0);
//    }
//    for (int i = 1, u, v, c; i <= m; i++) {
//        cin >> u >> v >> c;
//        addEdge(u + k, v, 1, c);
//        addEdge(v, u + k, 0, -c);
//    }
//    ssp(s, t);
//    cout << maxflow << " " << mincost << '\n';
//    return 0;
//}