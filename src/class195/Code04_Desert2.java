package class195;

// 沙漠，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3588
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 5;
//const int MAXE = MAXN * 20;
//const int LIMIT = 1000000000;
//int n, s, m;
//
//int head[MAXT];
//int nxt[MAXE];
//int to[MAXE];
//int weight[MAXE];
//int cntg;
//
//int ls[MAXT];
//int rs[MAXT];
//int root;
//int cntt;
//
//int val[MAXT];
//int indegree[MAXT];
//int dist[MAXT];
//int que[MAXT];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//    indegree[v]++;
//}
//
//int build(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
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
//bool topo() {
//    int qi = 1, qsiz = 0;
//    for (int i = 1; i <= cntt; i++) {
//        if (indegree[i] == 0) {
//            que[++qsiz] = i;
//        }
//        dist[i] = val[i] > 0 ? val[i] : LIMIT;
//    }
//    while (qi <= qsiz) {
//        int u = que[qi++];
//        if (dist[u] < 1) {
//            return false;
//        }
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            int w = weight[e];
//            if (dist[v] > dist[u] + w) {
//                dist[v] = dist[u] + w;
//                if (val[v] != 0 && dist[v] < val[v]) {
//                    return false;
//                }
//            }
//            if (--indegree[v] == 0) {
//                que[++qsiz] = v;
//            }
//        }
//    }
//    return qsiz == cntt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> s >> m;
//    cntt = n;
//    root = build(1, n);
//    for (int i = 1; i <= s; i++) {
//        int x, v;
//        cin >> x >> v;
//        val[x] = v;
//    }
//    for (int i = 1; i <= m; i++) {
//        int l, r, k;
//        cin >> l >> r >> k;
//        int vnode = ++cntt;
//        for (int j = 1; j <= k; j++) {
//            int x;
//            cin >> x;
//            addEdge(x, vnode, 0);
//            if (l < x) {
//                xToRange(vnode, l, x - 1, -1, 1, n, root);
//            }
//            l = x + 1;
//        }
//        if (l <= r) {
//            xToRange(vnode, l, r, -1, 1, n, root);
//        }
//    }
//    bool check = topo();
//    if (check) {
//        cout << "TAK" << "\n";
//        for (int i = 1; i <= n; i++) {
//            cout << dist[i] << " ";
//        }
//        cout << "\n";
//    } else {
//        cout << "NIE" << "\n";
//    }
//    return 0;
//}