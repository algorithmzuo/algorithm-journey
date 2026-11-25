package class210;

// 新生舞会，C++版
// 一共k名男生和k名女生，要组成k对舞伴，每对一男一女，每人恰好配对一次
// 给定矩阵a，a[i][j]表示第i号男生和第j号女生配对，产生的喜爱值
// 给定矩阵b，b[i][j]表示第i号男生和第j号女生配对，产生的讨厌值
// 一个配对方案的得分 = k对舞伴的喜爱值之和 / k对舞伴的讨厌值之和
// 求所有配对方案中的最大得分，答案四舍五入保留6位小数
// 1 <= k <= 100
// 1 <= 喜爱值、讨厌值 <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P3705
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 301;
//const int MAXM = 20001;
//ll INF = 1LL << 60;
//int k, n, s, t;
//
//int a[MAXN][MAXN];
//int b[MAXN][MAXN];
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//ll weight[MAXM << 1];
//double cost[MAXM << 1];
//int cntg;
//
//ll flow[MAXN];
//int pre[MAXN];
//
//queue<int> que;
//double h[MAXN];
//int update[MAXN];
//bool enter[MAXN];
//
//struct HeapNode {
//    double dist;
//    int u;
//
//    bool operator<(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//priority_queue<HeapNode> heap;
//double dist[MAXN];
//bool vis[MAXN];
//
//ll maxflow;
//double mincost;
//
//void addEdge(int u, int v, ll w, double c) {
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
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            ll w = weight[e];
//            double c = cost[e];
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
//        double d = cur.dist;
//        int u = cur.u;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                ll w = weight[e];
//                double c = cost[e] + h[u] - h[v];
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
//    maxflow = 0;
//    mincost = 0;
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
//bool check(double x) {
//    fill(head + 1, head + n + 1, 0);
//    cntg = 1;
//    for (int i = 1; i <= k; i++) {
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//        addEdge(k + i, t, 1, 0);
//        addEdge(t, k + i, 0, 0);
//    }
//    for (int i = 1; i <= k; i++) {
//        for (int j = 1; j <= k; j++) {
//            double c = a[i][j] - x * b[i][j];
//            addEdge(i, k + j, 1, -c);
//            addEdge(k + j, i, 0, c);
//        }
//    }
//    ssp(s, t);
//    return -mincost >= 0;
//}
//
//int main() {
//    scanf("%d", &k);
//    for (int i = 1; i <= k; i++) {
//        for (int j = 1; j <= k; j++) {
//            scanf("%d", &a[i][j]);
//        }
//    }
//    for (int i = 1; i <= k; i++) {
//        for (int j = 1; j <= k; j++) {
//            scanf("%d", &b[i][j]);
//        }
//    }
//    n = k << 1;
//    s = ++n;
//    t = ++n;
//    double l = 0, r = 10000;
//    for (int i = 1; i <= 60; i++) {
//        double x = (l + r) / 2;
//        if (check(x)) {
//            l = x;
//        } else {
//            r = x;
//        }
//    }
//    printf("%.6f\n", l);
//    return 0;
//}