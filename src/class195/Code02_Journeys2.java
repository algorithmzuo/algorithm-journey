package class195;

// 旅程，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P6348
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXT = MAXN * 10;
//const int MAXE = MAXN * 20;
//const int INF = 1 << 30;
//int n, m, p;
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
//int dist[MAXT];
//deque<int> deq;
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
//void rangeToRange(int a, int b, int c, int d) {
//    int vnode = ++cntt;
//    rangeToX(a, b, vnode, 1, 1, n, rootOut);
//    xToRange(vnode, c, d, 0, 1, n, rootIn);
//}
//
//void bfs01() {
//    for (int i = 1; i <= cntt; i++) {
//        dist[i] = INF;
//    }
//    dist[p] = 0;
//    deq.clear();
//    deq.push_front(p);
//    while (!deq.empty()) {
//        int u = deq.front();
//        deq.pop_front();
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            int w = weight[e];
//            if (dist[v] > dist[u] + w) {
//                dist[v] = dist[u] + w;
//                if (w == 0) {
//                    deq.push_front(v);
//                } else {
//                    deq.push_back(v);
//                }
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> p;
//    cntt = n;
//    rootOut = buildOut(1, n);
//    rootIn = buildIn(1, n);
//    for (int i = 1, a, b, c, d; i <= m; i++) {
//        cin >> a >> b >> c >> d;
//        rangeToRange(a, b, c, d);
//        rangeToRange(c, d, a, b);
//    }
//    bfs01();
//    for (int i = 1; i <= n; i++) {
//        cout << dist[i] << "\n";
//    }
//    return 0;
//}