package class206;

// 弹跳，C++版
// 一共有n个点，编号1~n，给定每个点的坐标(x, y)
// 一共有m个弹跳装置，给定每个弹跳装置的参数 p t l r d u
// 表示装置在p号点，花费t的时间，可以从p号点跳到[l, r] * [d, u]中的任意点
// 从1号点出发，可以重复经过点，也可以重复使用弹跳装置，题目保证可以到达每个点
// 打印从1号点到达2、3 .. n号点各自的最短用时
// 1 <= n <= 7 * 10^4
// 1 <= m <= 1.5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5471
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Jump {
//    int t, l, r, d, u;
//};
//
//const int MAXN = 200001;
//const int INF = 1 << 30;
//int n, m, w, h;
//
//int x[MAXN];
//int y[MAXN];
//int arr[MAXN];
//
//Jump jump[MAXN];
//
//int root;
//int ls[MAXN];
//int rs[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//int headg[MAXN];
//int nextg[MAXN];
//int tog[MAXN];
//int cntg;
//
//int headj[MAXN];
//int nextj[MAXN];
//int toj[MAXN];
//int cntj;
//
//int dist[MAXN];
//bool vis[MAXN];
//
//struct HeapNode {
//    int dist;
//    int id;
//
//    bool operator <(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//priority_queue<HeapNode> heap;
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addJump(int p, int j) {
//    nextj[++cntj] = headj[p];
//    toj[cntj] = j;
//    headj[p] = cntj;
//}
//
//void maintain(int i) {
//    xmin[i] = min(x[i], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(x[i], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(y[i], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(y[i], max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//bool XCmp(int i, int j) {
//    return x[i] < x[j];
//}
//
//bool YCmp(int i, int j) {
//    return y[i] < y[j];
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (dimension == 0) {
//        nth_element(arr + l, arr + mid, arr + r + 1, XCmp);
//    } else {
//        nth_element(arr + l, arr + mid, arr + r + 1, YCmp);
//    }
//    int rt = arr[mid];
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    addEdge(n + rt, rt);
//    if (ls[rt] != 0) {
//        addEdge(n + rt, n + ls[rt]);
//    }
//    if (rs[rt] != 0) {
//        addEdge(n + rt, n + rs[rt]);
//    }
//    return rt;
//}
//
//void update(int d, int i) {
//    if (!vis[i] && dist[i] > d) {
//        dist[i] = d;
//        heap.push({d, i});
//    }
//}
//
//void xToRectangle(int jl, int jr, int jd, int ju, int jdist, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (dist[n + i] <= jdist) {
//        return;
//    }
//    if (xmax[i] < jl || jr < xmin[i] || ymax[i] < jd || ju < ymin[i]) {
//        return;
//    }
//    if (jl <= xmin[i] && xmax[i] <= jr && jd <= ymin[i] && ymax[i] <= ju) {
//        update(jdist, n + i);
//        return;
//    }
//    if (jl <= x[i] && x[i] <= jr && jd <= y[i] && y[i] <= ju) {
//        update(jdist, i);
//    }
//    xToRectangle(jl, jr, jd, ju, jdist, ls[i]);
//    xToRectangle(jl, jr, jd, ju, jdist, rs[i]);
//}
//
//void dijkstra() {
//    fill(dist + 1, dist + (n << 1) + 1, INF);
//    dist[1] = 0;
//    heap.push({0, 1});
//    while (!heap.empty()) {
//        HeapNode cur = heap.top();
//        heap.pop();
//        int d = cur.dist;
//        int i = cur.id;
//        if (!vis[i]) {
//            vis[i] = true;
//            for (int e = headg[i]; e > 0; e = nextg[e]) {
//                update(d, tog[e]);
//            }
//            if (i <= n) {
//                for (int e = headj[i]; e > 0; e = nextj[e]) {
//                    int j = toj[e];
//                    int jt = jump[j].t;
//                    int jl = jump[j].l;
//                    int jr = jump[j].r;
//                    int jd = jump[j].d;
//                    int ju = jump[j].u;
//                    xToRectangle(jl, jr, jd, ju, d + jt, root);
//                }
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> w >> h;
//    for (int i = 1; i <= n; i++) {
//        cin >> x[i] >> y[i];
//        arr[i] = i;
//    }
//    for (int j = 1, p; j <= m; j++) {
//        cin >> p;
//        cin >> jump[j].t >> jump[j].l >> jump[j].r >> jump[j].d >> jump[j].u;
//        addJump(p, j);
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    root = build(1, n, 0);
//    dijkstra();
//    for (int i = 2; i <= n; i++) {
//        cout << dist[i] << "\n";
//    }
//    return 0;
//}