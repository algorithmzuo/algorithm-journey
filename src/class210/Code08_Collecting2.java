package class210;

// 收集，C++版
// 一共有n个点，m条有向边，图中没有自环，也没有重边
// 第i号边从a[i]指向b[i]，第i号点有arr[i]件物品
// 一共k个人，依次从1号点出发，每步沿着有向边前进，可以随时停下
// 每个点可以反复访问，第一个访问的人拿走全部物品，以后不会再有收益
// 求这k个人最多能收集多少件物品
// 1 <= n、m <= 2 * 10^5
// 1 <= k <= 10
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc214_h
// 测试链接 : https://atcoder.jp/contests/abc214/tasks/abc214_h
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const int MAXM = 1000001;
//ll INF = 1LL << 60;
//int n, m, k;
//
//int arr[MAXN];
//int a[MAXN];
//int b[MAXN];
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int top;
//int belong[MAXN];
//ll sum[MAXN];
//int sccCnt;
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
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void addEdge(int u, int v, ll w, ll c) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    cost[cntg] = c;
//    head[u] = cntg;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (belong[v] == 0) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//            sum[sccCnt] += arr[pop];
//        } while (pop != u);
//    }
//}
//
//void condense(int s, int t) {
//    fill(head + 1, head + n + 1, 0);
//    cntg = 1;
//    addEdge(s, belong[1], k, 0);
//    addEdge(belong[1], s, 0, 0);
//    for (int i = 1; i <= sccCnt; i++) {
//        int out = i + sccCnt;
//        addEdge(i, out, 1, -sum[i]);
//        addEdge(out, i, 0, sum[i]);
//        addEdge(i, out, k - 1, 0);
//        addEdge(out, i, 0, 0);
//        addEdge(out, t, k, 0);
//        addEdge(t, out, 0, 0);
//    }
//    for (int i = 1; i <= m; i++) {
//        int u = belong[a[i]];
//        int v = belong[b[i]];
//        if (u != v) {
//            addEdge(u + sccCnt, v, k, 0);
//            addEdge(v, u + sccCnt, 0, 0);
//        }
//    }
//}
//
//void init(int s) {
//    fill(h + 1, h + n + 1, INF);
//    h[s] = h[belong[1]] = 0;
//    for (int i = sccCnt, out = sccCnt << 1; i >= 1; i--, out--) {
//        if (h[i] != INF) {
//            h[out] = h[i] - sum[i];
//            for (int e = head[out]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                ll c = cost[e];
//                if (weight[e] > 0) {
//                    h[v] = min(h[v], h[out] + c);
//                }
//            }
//        }
//    }
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
//    maxflow = mincost = 0;
//    init(s);
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
//    cin >> n >> m >> k;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge(a[i], b[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    n = sccCnt << 1;
//    int s = ++n;
//    int t = ++n;
//    condense(s, t);
//    ssp(s, t);
//    cout << -mincost << '\n';
//    return 0;
//}