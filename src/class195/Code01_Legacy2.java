package class195;

// 遗产，C++版
// 一共n个点，给定起点s，一共q条操作，操作类型如下
// 操作 1 x y w   : 从点x到点y增加有向边，边权是w
// 操作 2 x l r w : 从点x到l~r范围的每个点增加有向边，边权都是w
// 操作 3 x l r w : 从l~r范围的每个点到点x增加有向边，边权都是w
// 所有操作完成后，计算起点s到每个点的最短距离并打印，如果不连通打印-1
// 1 <= n、q <= 10^5
// 1 <= w <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF786B
// 测试链接 : https://codeforces.com/problemset/problem/786/B
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 10;
//const int MAXE = MAXN * 30;
//const ll INF = 1LL << 60;
//int n, q, s;
//
//int head[MAXT];
//int nxt[MAXE];
//int to[MAXE];
//int weight[MAXE];
//int cntg;
//
//int ls[MAXT];
//int rs[MAXT];
//int rootOut, rootIn;
//int cntt;
//
//ll dist[MAXT];
//bool vis[MAXT];
//
//struct Node {
//    int u;
//    ll d;
//
//    bool operator < (const Node &other) const {
//        return d > other.d;
//    }
//};
//
//priority_queue<Node> heap;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//int buildOut(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = buildOut(l, mid);
//        rs[rt] = buildOut(mid + 1, r);
//        addEdge(ls[rt], rt, 0);
//        addEdge(rs[rt], rt, 0);
//    }
//    return rt;
//}
//
//int buildIn(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = buildIn(l, mid);
//        rs[rt] = buildIn(mid + 1, r);
//        addEdge(rt, ls[rt], 0);
//        addEdge(rt, rs[rt], 0);
//    }
//    return rt;
//}
//
//void xToRange(int jobx, int jobl, int jobr, int jobw, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(jobx, i, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            xToRange(jobx, jobl, jobr, jobw, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            xToRange(jobx, jobl, jobr, jobw, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void rangeToX(int jobl, int jobr, int jobx, int jobw, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            rangeToX(jobl, jobr, jobx, jobw, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            rangeToX(jobl, jobr, jobx, jobw, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void dijkstra() {
//    for (int i = 1; i <= cntt; i++) {
//        dist[i] = INF;
//    }
//    dist[s] = 0;
//    heap.push({s, 0});
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int u = cur.u;
//        ll d = cur.d;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                int w = weight[e];
//                if (!vis[v] && dist[v] > d + w) {
//                    dist[v] = d + w;
//                    heap.push({v, dist[v]});
//                }
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q >> s;
//    cntt = n;
//    rootOut = buildOut(1, n);
//    rootIn = buildIn(1, n);
//    for (int i = 1, op, x, y, l, r, w; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> x >> y >> w;
//            addEdge(x, y, w);
//        } else if (op == 2) {
//            cin >> x >> l >> r >> w;
//            xToRange(x, l, r, w, 1, n, rootIn);
//        } else {
//            cin >> x >> l >> r >> w;
//            rangeToX(l, r, x, w, 1, n, rootOut);
//        }
//    }
//    dijkstra();
//    for (int i = 1; i <= n; i++) {
//        cout << (dist[i] == INF ? -1 : dist[i]) << " ";
//    }
//    return 0;
//}