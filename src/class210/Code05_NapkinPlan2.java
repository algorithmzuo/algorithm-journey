package class210;

// 餐巾计划，C++版
// 餐厅连续营业openDays天，第i天需要need[i]个干净餐巾，餐巾用完就变脏了
// 每天都可以买新餐巾，每个花费buyCost，买来当天就能使用
// 每天结束时，每个脏餐巾可以送去快洗或慢洗，也可以留到以后再洗
// 快洗每个花费fastCost，第i天送洗，第 i + fastDays 天可以使用
// 慢洗每个花费slowCost，第i天送洗，第 i + slowDays 天可以使用
// 求满足每天餐巾需求的最小总花费
// 1 <= openDays <= 2000    1 <= need[i] <= 10^7
// 1 <= buyCost、slowCost、fastCost <= 10^4
// slowCost < fastCost      fastDays < slowDays
// 测试链接 : https://www.luogu.com.cn/problem/P1251
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 5001;
//const int MAXM = 12001;
//ll INF = 1LL << 60;
//int n;
//
//int need[MAXN];
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
//    int openDays;
//    cin >> openDays;
//    for (int i = 1; i <= openDays; i++) {
//        cin >> need[i];
//    }
//    int buyCost, fastDays, fastCost, slowDays, slowCost;
//    cin >> buyCost >> fastDays >> fastCost >> slowDays >> slowCost;
//    n = openDays << 1;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= openDays; i++) {
//        int clean = i;
//        int dirty = i + openDays;
//        addEdge(s, clean, need[i], buyCost);
//        addEdge(clean, s, 0, -buyCost);
//        addEdge(s, dirty, need[i], 0);
//        addEdge(dirty, s, 0, 0);
//        addEdge(clean, t, need[i], 0);
//        addEdge(t, clean, 0, 0);
//        if (i < openDays) {
//            addEdge(dirty, dirty + 1, INF, 0);
//            addEdge(dirty + 1, dirty, 0, 0);
//        }
//        int nextClean = i + fastDays;
//        if (nextClean <= openDays) {
//            addEdge(dirty, nextClean, INF, fastCost);
//            addEdge(nextClean, dirty, 0, -fastCost);
//        }
//        nextClean = i + slowDays;
//        if (nextClean <= openDays) {
//            addEdge(dirty, nextClean, INF, slowCost);
//            addEdge(nextClean, dirty, 0, -slowCost);
//        }
//    }
//    ssp(s, t);
//    cout << mincost << '\n';
//    return 0;
//}