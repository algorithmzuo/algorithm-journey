package class209;

// 分配问题，C++版
// 给定正整数n，表示有n个人、n件工作
// 每人恰好做一件工作，每件工作恰好分配给一个人
// 给定c[i][j]，表示第i号人做第j号工作产生的效益
// 打印最小总效益和最大总效益
// 1 <= n <= 50
// 0 <= c[i][j] <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P4014
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 301;
//const int MAXM = 20001;
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
//    int k;
//    cin >> k;
//    n = k + k;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= k; i++) {
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//    }
//    for (int j = 1; j <= k; j++) {
//        addEdge(k + j, t, 1, 0);
//        addEdge(t, k + j, 0, 0);
//    }
//    for (int i = 1, c; i <= k; i++) {
//        for (int j = 1; j <= k; j++) {
//            cin >> c;
//            addEdge(i, k + j, 1, c);
//            addEdge(k + j, i, 0, -c);
//        }
//    }
//    ssp(s, t);
//    int ans1 = mincost;
//    for (int e = 2; e <= cntg; e += 2) {
//        weight[e] += weight[e ^ 1];
//        weight[e ^ 1] = 0;
//        cost[e] = -cost[e];
//        cost[e ^ 1] = -cost[e ^ 1];
//    }
//    ssp(s, t);
//    int ans2 = -mincost;
//    cout << ans1 << '\n';
//    cout << ans2 << '\n';
//    return 0;
//}