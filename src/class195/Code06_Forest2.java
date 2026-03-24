package class195;

// 逛森林，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5344
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXM = 1000001;
//const int MAXT = 4000001;
//const int MAXE = 30000001;
//const int MAXP = 17;
//const int INF = 1 << 30;
//int n, m, s;
//
//int u1[MAXM];
//int v1[MAXM];
//int u2[MAXM];
//int v2[MAXM];
//int weight[MAXM];
//int cntq;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int cnt1;
//
//int head2[MAXT];
//int next2[MAXE];
//int to2[MAXE];
//int weight2[MAXE];
//int cnt2;
//
//int father[MAXN];
//
//int dep[MAXN];
//int stfa[MAXN][MAXP];
//int stin[MAXN][MAXP];
//int stout[MAXN][MAXP];
//int cntt;
//
//int dist[MAXT];
//bool vis[MAXT];
//
//struct Node {
//    int u;
//    int d;
//
//    bool operator < (const Node &other) const {
//        return d > other.d;
//    }
//};
//
//priority_queue<Node> heap;
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//int find(int i) {
//    if (i != father[i]) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stfa[u][0] = fa;
//    stin[u][0] = ++cntt;
//    addEdge2(u, cntt, 0);
//    addEdge2(fa, cntt, 0);
//    stout[u][0] = ++cntt;
//    addEdge2(cntt, u, 0);
//    addEdge2(cntt, fa, 0);
//    for (int p = 1; p < MAXP; p++) {
//        stfa[u][p] = stfa[stfa[u][p - 1]][p - 1];
//        stin[u][p] = ++cntt;
//        addEdge2(stin[u][p - 1], cntt, 0);
//        addEdge2(stin[stfa[u][p - 1]][p - 1], cntt, 0);
//        stout[u][p] = ++cntt;
//        addEdge2(cntt, stout[u][p - 1], 0);
//        addEdge2(cntt, stout[stfa[u][p - 1]][p - 1], 0);
//    }
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//void lcaIn(int x, int y, int vnode) {
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    addEdge2(y, vnode, 0);
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stfa[x][p]] >= dep[y]) {
//            addEdge2(stin[x][p], vnode, 0);
//            x = stfa[x][p];
//        }
//    }
//    if (x == y) {
//        return;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stfa[x][p] != stfa[y][p]) {
//            addEdge2(stin[x][p], vnode, 0);
//            addEdge2(stin[y][p], vnode, 0);
//            x = stfa[x][p];
//            y = stfa[y][p];
//        }
//    }
//    addEdge2(stin[x][0], vnode, 0);
//}
//
//void lcaOut(int x, int y, int vnode) {
//    if (dep[x] < dep[y]) {
//    	swap(x, y);
//    }
//    addEdge2(vnode, y, 0);
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stfa[x][p]] >= dep[y]) {
//            addEdge2(vnode, stout[x][p], 0);
//            x = stfa[x][p];
//        }
//    }
//    if (x == y) {
//        return;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stfa[x][p] != stfa[y][p]) {
//            addEdge2(vnode, stout[x][p], 0);
//            addEdge2(vnode, stout[y][p], 0);
//            x = stfa[x][p];
//            y = stfa[y][p];
//        }
//    }
//    addEdge2(vnode, stout[x][0], 0);
//}
//
//void pathToPath(int a, int b, int c, int d, int w) {
//    int vin = ++cntt;
//    int vout = ++cntt;
//    lcaIn(a, b, vin);
//    lcaOut(c, d, vout);
//    addEdge2(vin, vout, w);
//}
//
//void dijkstra() {
//    dist[0] = INF;
//    for (int i = 1; i <= cntt; i++) {
//        dist[i] = INF;
//    }
//    dist[s] = 0;
//    heap.push({s, 0});
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int u = cur.u;
//        int d = cur.d;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head2[u]; e > 0; e = next2[e]) {
//                int v = to2[e];
//                int w = weight2[e];
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
//    cin >> n >> m >> s;
//    cntt = n;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    for (int i = 1, op, a, b, c, d, w, u, v; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> a >> b >> c >> d >> w;
//            if (find(a) == find(b) && find(c) == find(d)) {
//                u1[++cntq] = a;
//                v1[cntq] = b;
//                u2[cntq] = c;
//                v2[cntq] = d;
//                weight[cntq] = w;
//            }
//        } else {
//            cin >> u >> v >> w;
//            int ufa = find(u);
//            int vfa = find(v);
//            if (ufa != vfa) {
//                addEdge1(u, v);
//                addEdge1(v, u);
//                addEdge2(u, v, w);
//                addEdge2(v, u, w);
//                father[ufa] = vfa;
//            }
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dep[i] == 0) {
//            dfs(i, 0);
//        }
//    }
//    for (int i = 1; i <= cntq; i++) {
//        pathToPath(u1[i], v1[i], u2[i], v2[i], weight[i]);
//    }
//    dijkstra();
//    for (int i = 1; i <= n; i++) {
//        cout << (dist[i] == INF ? -1 : dist[i]) << " ";
//    }
//    return 0;
//}