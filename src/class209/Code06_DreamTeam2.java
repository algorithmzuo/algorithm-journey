package class209;

// 梦之队，C++版
// 一共有k个人，第i个人所属大学为a[i]，擅长领域为b[i]，实力为c[i]
// 从中选出一些人组成队伍，要求任意两人的所属大学不同，擅长领域也不同
// 满足要求的情况下，计算队伍最多能选几个人，假设该值是q，打印该值
// 接下来q行，对于每个i = 1、2、...、q，打印恰好选出i个人时，队伍最大总实力
// 1 <= k <= 3 * 10^4
// 1 <= a[i]、b[i] <= 150
// 1 <= c[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc247_g
// 测试链接 : https://atcoder.jp/contests/abc247/tasks/abc247_g
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 401;
//const int MAXM = 40001;
//int OFFSET = 150;
//ll INF = 1LL << 60;
//int n;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//ll weight[MAXM << 1];
//ll cost[MAXM << 1];
//int cntg;
//
//ll flow[MAXN];
//int pre[MAXN];
//
//queue<int> que;
//ll h[MAXN];
//int update[MAXN];
//bool enter[MAXN];
//
//struct HeapNode {
//    ll dist;
//    int u;
//
//    bool operator<(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//priority_queue<HeapNode> heap;
//ll dist[MAXN];
//bool vis[MAXN];
//
//ll maxflow, mincost;
//ll ans[MAXN];
//
//void addEdge(int u, int v, ll w, ll c) {
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
//            ll c = cost[e];
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
//        ll d = cur.dist;
//        int u = cur.u;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                ll w = weight[e];
//                ll c = cost[e] + h[u] - h[v];
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
//        ans[(int) maxflow] = -mincost;
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
//    n = OFFSET << 1;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= OFFSET; i++) {
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//        addEdge(i + OFFSET, t, 1, 0);
//        addEdge(t, i + OFFSET, 0, 0);
//    }
//    int k;
//    cin >> k;
//    for (int i = 1, u, v, c; i <= k; i++) {
//        cin >> u >> v >> c;
//        addEdge(u, v + OFFSET, 1, -c);
//        addEdge(v + OFFSET, u, 0, c);
//    }
//    ssp(s, t);
//    cout << maxflow << '\n';
//    for (int i = 1; i <= maxflow; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}