package class210;

// 修车，C++版
// 一共a个技师，同时到达了b辆车，mat[i][j]表示第j号技师修理第i号辆车需要的时间
// 每辆车只能交给一个技师修理，每个技师修完一辆车之后，才能去修下一辆
// 车主等待时间 = 从到达时刻开始，到该车被修好的时刻为止，总共花费的时间
// 你来安排修理，希望车主的平均等待时间最小，打印这个最小值，答案保留小数点后两位
// 2 <= a <= 9
// 1 <= b <= 60
// 1 <= mat[i][j] <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P2053
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1001;
//const int MAXM = 50001;
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
//    int a, b, s, t;
//    scanf("%d", &a);
//    scanf("%d", &b);
//    n = b + a * b;
//    s = ++n;
//    t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= b; i++) {
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//        for (int j = 1, time; j <= a; j++) {
//            scanf("%d", &time);
//            for (int k = 1; k <= b; k++) {
//                int u = j * b + k;
//                int c = time * k;
//                addEdge(i, u, 1, c);
//                addEdge(u, i, 0, -c);
//            }
//        }
//    }
//    for (int u = b + 1; u < s; u++) {
//        addEdge(u, t, 1, 0);
//        addEdge(t, u, 0, 0);
//    }
//    ssp(s, t);
//    double ans = (double) mincost / b;
//    printf("%.2f\n", ans);
//    return 0;
//}