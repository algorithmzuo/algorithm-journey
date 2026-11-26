package class210;

// 通信，C++版
// 一共有k个哨站，从左到右编号为1到k，第i号哨站的频段为arr[i]
// 每个哨站i需要选择以下二者之一
// 方式1，直接连接到控制中心，花费为w
// 方式2，连接左侧的某个哨站j，j < i，花费为|arr[i] - arr[j]|
// 控制中心可以被多个哨站直接连接，每个哨站最多接受一个右侧哨站的直接连接
// 求最小的总花费
// 1 <= k <= 1000
// 0 <= w、arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5331
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 20001;
//const int MAXM = 50001;
//ll INF = 1LL << 60;
//int k, n;
//
//ll arr[MAXN];
//int idx[MAXN];
//int tmp[MAXN];
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
//void merge(int l, int mid, int r) {
//    ll curVal = arr[idx[l]];
//    int curNode = ++n;
//    ll newVal;
//    int newNode;
//    ll c;
//    for (int p1 = l, p2 = mid + 1; p2 <= r; p2++) {
//        while (p1 <= mid && arr[idx[p1]] <= arr[idx[p2]]) {
//            newVal = arr[idx[p1]];
//            if (newVal != curVal) {
//                newNode = ++n;
//                c = newVal - curVal;
//                addEdge(newNode, curNode, k, c);
//                addEdge(curNode, newNode, 0, -c);
//                curVal = newVal;
//                curNode = newNode;
//            }
//            addEdge(curNode, idx[p1] + k, 1, 0);
//            addEdge(idx[p1] + k, curNode, 0, 0);
//            p1++;
//        }
//        if (p1 > l) {
//            c = arr[idx[p2]] - curVal;
//            addEdge(idx[p2], curNode, 1, c);
//            addEdge(curNode, idx[p2], 0, -c);
//        }
//    }
//    curVal = arr[idx[mid]];
//    curNode = ++n;
//    for (int p1 = mid, p2 = r; p2 > mid; p2--) {
//        while (p1 >= l && arr[idx[p1]] > arr[idx[p2]]) {
//            newVal = arr[idx[p1]];
//            if (newVal != curVal) {
//                newNode = ++n;
//                c = curVal - newVal;
//                addEdge(newNode, curNode, k, c);
//                addEdge(curNode, newNode, 0, -c);
//                curVal = newVal;
//                curNode = newNode;
//            }
//            addEdge(curNode, idx[p1] + k, 1, 0);
//            addEdge(idx[p1] + k, curNode, 0, 0);
//            p1--;
//        }
//        if (p1 < mid) {
//            c = curVal - arr[idx[p2]];
//            addEdge(idx[p2], curNode, 1, c);
//            addEdge(curNode, idx[p2], 0, -c);
//        }
//    }
//    int p1 = l, p2 = mid + 1, ti = 0;
//    while (p1 <= mid && p2 <= r) {
//        if (arr[idx[p1]] <= arr[idx[p2]]) {
//            tmp[++ti] = idx[p1++];
//        } else {
//            tmp[++ti] = idx[p2++];
//        }
//    }
//    while (p1 <= mid) {
//        tmp[++ti] = idx[p1++];
//    }
//    while (p2 <= r) {
//        tmp[++ti] = idx[p2++];
//    }
//    for (int i = l, j = 1; i <= r; i++, j++) {
//        idx[i] = tmp[j];
//    }
//}
//
//void cdq(int l, int r) {
//    if (l == r) {
//        return;
//    }
//    int mid = (l + r) / 2;
//    cdq(l, mid);
//    cdq(mid + 1, r);
//    merge(l, mid, r);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int w;
//    cin >> k >> w;
//    n = k << 1;
//    int s = ++n;
//    int t = ++n;
//    cntg = 1;
//    for (int i = 1; i <= k; i++) {
//        cin >> arr[i];
//        idx[i] = i;
//        addEdge(s, i, 1, 0);
//        addEdge(i, s, 0, 0);
//        addEdge(i, t, 1, w);
//        addEdge(t, i, 0, -w);
//        addEdge(i + k, t, 1, 0);
//        addEdge(t, i + k, 0, 0);
//    }
//    cdq(1, k);
//    ssp(s, t);
//    cout << mincost << '\n';
//    return 0;
//}