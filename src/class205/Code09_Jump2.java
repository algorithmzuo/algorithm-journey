package class205;

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
//struct Node {
//    int x, y, i;
//};
//
//struct Jump {
//    int t, l, r, d, u;
//};
//
//struct HeapNode {
//    int dist, i;
//
//    bool operator <(const HeapNode &other) const {
//        return dist > other.dist;
//    }
//};
//
//const int MAXN = 200001;
//const int INF = 1 << 30;
//int n, m, w, h;
//
//Node arr[MAXN];
//Jump jump[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//int root;
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
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = dimension == 0 ? arr[i].x : arr[i].y;
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(arr[first++], arr[i++]);
//        } else {
//            swap(arr[i], arr[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = l + rand() % (r - l + 1);
//        int pivot = dimension == 0 ? arr[idx].x : arr[idx].y;
//        partition(l, r, pivot, dimension);
//        if (i < first) {
//            r = first - 1;
//        } else if (i > last) {
//            l = last + 1;
//        } else {
//            break;
//        }
//    }
//}
//
//void maintain(int i) {
//    xmin[i] = min(arr[i].x, min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i].x, max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i].y, min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i].y, max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    randSelect(l, r, mid, dimension);
//    ls[mid] = build(l, mid - 1, dimension ^ 1);
//    rs[mid] = build(mid + 1, r, dimension ^ 1);
//    maintain(mid);
//    addEdge(n + mid, arr[mid].i);
//    if (ls[mid] != 0) {
//        addEdge(n + mid, n + ls[mid]);
//    }
//    if (rs[mid] != 0) {
//        addEdge(n + mid, n + rs[mid]);
//    }
//    return mid;
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
//    if (jl <= arr[i].x && arr[i].x <= jr && jd <= arr[i].y && arr[i].y <= ju) {
//        update(jdist, arr[i].i);
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
//        int i = cur.i;
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
//    srand((unsigned)time(nullptr));
//    cin >> n >> m >> w >> h;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//        arr[i].i = i;
//    }
//    for (int j = 1, p; j <= m; j++) {
//        cin >> p >> jump[j].t >> jump[j].l >> jump[j].r >> jump[j].d >> jump[j].u;
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