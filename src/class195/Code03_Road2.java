package class195;

// 道路，C++版
// 一共n个点，一共m次操作，格式如下
// 操作 a b c d w: a~b范围每个点与c~d范围每个点之间，都增加权值为w的无向边
// 给定数字k，表示有k次机会，每次在通过一条边时，不用支付这条边的代价
// 所有操作完成后，打印1号点到n号点的最低代价
// 如果不存在通路，打印"CreationAugust is a sb!"
// 1 <= n <= 5 * 10^4
// 1 <= m <= 10^5
// 1 <= k <= 10
// 1 <= w <= 10^3
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5669
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 10;
//const int MAXE = MAXN * 20;
//const int MAXK = 11;
//const int INF = 1 << 30;
//int t, n, m, k;
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
//int dist[MAXT][MAXK];
//bool vis[MAXT][MAXK];
//
//struct Node {
//    int node;
//    int time;
//    int cost;
//
//    bool operator < (const Node &other) const {
//        return cost > other.cost;
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
//void xToRange(int jobx, int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(jobx, i, 0);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            xToRange(jobx, jobl, jobr, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            xToRange(jobx, jobl, jobr, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void rangeToX(int jobl, int jobr, int jobx, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, 0);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            rangeToX(jobl, jobr, jobx, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            rangeToX(jobl, jobr, jobx, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void rangeToRange(int a, int b, int c, int d, int w) {
//    int x = ++cntt;
//    int y = ++cntt;
//    rangeToX(a, b, x, 1, n, rootOut);
//    xToRange(y, c, d, 1, n, rootIn);
//    addEdge(x, y, w);
//}
//
//int dijkstra(int start, int target) {
//    while (!heap.empty()) {
//        heap.pop();
//    }
//    for (int i = 1; i <= cntt; i++) {
//        for (int j = 0; j <= k; j++) {
//            dist[i][j] = INF;
//            vis[i][j] = false;
//        }
//    }
//    dist[start][0] = 0;
//    heap.push({start, 0, 0});
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int node = cur.node;
//        int time = cur.time;
//        int cost = cur.cost;
//        if (!vis[node][time]) {
//            vis[node][time] = true;
//            if (node == target) {
//                return cost;
//            }
//            for (int e = head[node]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                int w = weight[e];
//                if (!vis[v][time] && dist[v][time] > cost + w) {
//                    dist[v][time] = cost + w;
//                    heap.push({v, time, dist[v][time]});
//                }
//                if (time < k && !vis[v][time + 1] && dist[v][time + 1] > cost) {
//                    dist[v][time + 1] = cost;
//                    heap.push({v, time + 1, dist[v][time + 1]});
//                }
//            }
//        }
//    }
//    return -1;
//}
//
//void clear() {
//    for (int i = 1; i <= cntt; i++) {
//        head[i] = 0;
//    }
//    cntg = cntt = 0;
//    while (!heap.empty()) {
//        heap.pop();
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int test = 1; test <= t; test++) {
//        cin >> n >> m >> k;
//        cntt = n;
//        rootOut = buildOut(1, n);
//        rootIn = buildIn(1, n);
//        for (int i = 1, a, b, c, d, w; i <= m; i++) {
//            cin >> a >> b >> c >> d >> w;
//            rangeToRange(a, b, c, d, w);
//            rangeToRange(c, d, a, b, w);
//        }
//        int ans = dijkstra(1, n);
//        if (ans == -1) {
//            cout << "CreationAugust is a sb!" << "\n";
//        } else {
//            cout << ans << "\n";
//        }
//        clear();
//    }
//    return 0;
//}