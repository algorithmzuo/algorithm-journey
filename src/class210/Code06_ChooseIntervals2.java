package class210;

// 最长k可重区间集，C++版
// 给定a个开区间，第i个区间为(l[i], r[i])，长度为r[i] - l[i]
// 选出若干区间，要求数轴上的任意一点，最多被k个选中的区间覆盖
// 因为开区间不包含左右端点，所以两个区间只在端点处相接，认为没有重叠
// 求所选区间长度之和的最大值，重叠部分的长度，在各自的区间都要计入
// 1 <= a <= 500
// 1 <= k <= 3
// 1 <= l[i] < r[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3358
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 2001;
//const int MAXM = 2001;
//int INF = 1000000001;
//int n;
//
//int l[MAXN];
//int r[MAXN];
//int sortv[MAXN << 1];
//int cntv;
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
//void build(int intervals) {
//    for (int i = 1; i <= intervals; i++) {
//        sortv[++cntv] = l[i];
//        sortv[++cntv] = r[i];
//    }
//    sort(sortv + 1, sortv + cntv + 1);
//    int siz = 1;
//    for (int i = 2; i <= cntv; i++) {
//        if (sortv[siz] != sortv[i]) {
//            sortv[++siz] = sortv[i];
//        }
//    }
//    cntv = siz;
//}
//
//int kth(int num) {
//    int l = 1, r = cntv, mid;
//    while (l <= r) {
//        mid = (l + r) / 2;
//        if (sortv[mid] == num) {
//            return mid;
//        } else if (sortv[mid] < num) {
//            l = mid + 1;
//        } else {
//            r = mid - 1;
//        }
//    }
//    return -1;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int a, k;
//    cin >> a >> k;
//    for (int i = 1; i <= a; i++) {
//        cin >> l[i] >> r[i];
//    }
//    build(a);
//    n = cntv;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    addEdge(s, 1, k, 0);
//    addEdge(1, s, 0, 0);
//    addEdge(cntv, t, k, 0);
//    addEdge(t, cntv, 0, 0);
//    for (int i = 1; i < cntv; i++) {
//        addEdge(i, i + 1, k, 0);
//        addEdge(i + 1, i, 0, 0);
//    }
//    for (int i = 1, u, v, c; i <= a; i++) {
//        u = kth(l[i]);
//        v = kth(r[i]);
//        c = r[i] - l[i];
//        addEdge(u, v, 1, -c);
//        addEdge(v, u, 0, c);
//    }
//    ssp(s, t);
//    cout << -mincost << '\n';
//    return 0;
//}