package class210;

// 近似排列，C++版
// 给定数组长度n和q条限制，数组中每个数都是1到n之间的整数，可以重复
// 数组下标从1开始，每条限制的格式 op l r v，含义如下
// 如果op为1，要求下标区间[l, r]的每个数 >= v
// 如果op为2，要求下标区间[l, r]的每个数 <= v
// 设c[i]为数字i在数组中出现的次数，数组的代价为c[1]到c[n]的平方和
// 求满足所有限制的数组的最小代价，如果无解，输出-1
// 1 <= n <= 50
// 0 <= q <= 100
// 测试链接 : https://www.luogu.com.cn/problem/CF863F
// 测试链接 : https://codeforces.com/problemset/problem/863/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 201;
//const int MAXM = 10001;
//int INF = 1000000001;
//int n;
//
//int low[MAXN];
//int high[MAXN];
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
//    int len, q;
//    cin >> len >> q;
//    n = len << 1;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= len; i++) {
//        low[i] = 1;
//        high[i] = len;
//    }
//    for (int i = 1, op, l, r, v; i <= q; i++) {
//        cin >> op >> l >> r >> v;
//        for (int j = l; j <= r; j++) {
//            if (op == 1) {
//                low[j] = max(low[j], v);
//            } else {
//                high[j] = min(high[j], v);
//            }
//        }
//    }
//    for (int i = 1; i <= len; i++) {
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//        for (int v = low[i]; v <= high[i]; v++) {
//            addEdge(i, len + v, 1, 0);
//            addEdge(len + v, i, 0, 0);
//        }
//    }
//    for (int v = 1; v <= len; v++) {
//        for (int k = 1, c = 1; k <= len; k++, c += 2) {
//            addEdge(len + v, t, 1, c);
//            addEdge(t, len + v, 0, -c);
//        }
//    }
//    ssp(s, t);
//    if (maxflow == len) {
//        cout << mincost << '\n';
//    } else {
//        cout << -1 << '\n';
//    }
//    return 0;
//}