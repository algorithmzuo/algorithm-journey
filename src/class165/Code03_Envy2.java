package class165;

// 同在最小生成树里，C++版
// 一共有n个点，m条无向边，每条边有边权
// 一共有q次查询，每条查询都给定参数k，表示该查询涉及k条边
// 然后依次给出k条边的编号，打印这k条边能否同时出现在一颗最小生成树上
// 1 <= n、m、q、所有查询涉及边的总量 <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF891C
// 测试链接 : https://codeforces.com/problemset/problem/891/C
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v, w;
//};
//
//bool EdgeCmp(Edge x, Edge y) {
//    return x.w < y.w;
//}
//
//struct QueryEdge {
//    int u, v, w, i;
//};
//
//bool QueryEdgeCmp(QueryEdge x, QueryEdge y) {
//    if(x.w != y.w) {
//        return x.w < y.w;
//    } else {
//        return x.i < y.i;
//    }
//}
//
//const int MAXN = 500001;
//int n, m, q, k;
//
//Edge edge[MAXN];
//QueryEdge queryEdge[MAXN];
//
//int father[MAXN];
//int siz[MAXN];
//int rollback[MAXN << 1][2];
//int opsize;
//
//bool ans[MAXN];
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//void Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//}
//
//void undo() {
//    int fx = rollback[opsize][0];
//    int fy = rollback[opsize--][1];
//    father[fy] = fy;
//    siz[fx] -= siz[fy];
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    sort(edge + 1, edge + m + 1, EdgeCmp);
//    sort(queryEdge + 1, queryEdge + k + 1, QueryEdgeCmp);
//    for (int i = 1; i <= q; i++) {
//        ans[i] = true;
//    }
//}
//
//void compute() {
//    int ei = 1;
//    for (int l = 1, r = 1; l <= k; l = ++r) {
//        for (; ei <= m && edge[ei].w < queryEdge[l].w; ei++) {
//            if (find(edge[ei].u) != find(edge[ei].v)) {
//                Union(edge[ei].u, edge[ei].v);
//            }
//        }
//        while (r + 1 <= k && queryEdge[l].w == queryEdge[r + 1].w && queryEdge[l].i == queryEdge[r + 1].i) {
//            r++;
//        }
//        int unionCnt = 0;
//        for (int i = l; i <= r; i++) {
//            if (find(queryEdge[i].u) == find(queryEdge[i].v)) {
//                ans[queryEdge[i].i] = false;
//                break;
//            } else {
//                Union(queryEdge[i].u, queryEdge[i].v);
//                unionCnt++;
//            }
//        }
//        for (int i = 1; i <= unionCnt; i++) {
//            undo();
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i].u >> edge[i].v >> edge[i].w;
//    }
//    cin >> q;
//    k = 0;
//    for (int i = 1, s; i <= q; i++) {
//        cin >> s;
//        for (int j = 1, ei; j <= s; j++) {
//            cin >> ei;
//            queryEdge[++k].u = edge[ei].u;
//            queryEdge[k].v = edge[ei].v;
//            queryEdge[k].w = edge[ei].w;
//            queryEdge[k].i = i;
//        }
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= q; i++) {
//        if (ans[i]) {
//            cout << "YES" << "\n";
//        } else {
//            cout << "NO" << "\n";
//        }
//    }
//    return 0;
//}