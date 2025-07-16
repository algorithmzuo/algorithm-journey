package class174;

// 桥梁，C++版
// 有n个点组成的无向图，依次给出m条无向边
// u v w : u到v的边，边权为w，边权同时代表限重
// 如果开车从边上经过，车的重量 <= 边的限重，车才能走过这条边
// 接下来有q条操作，每条操作的格式如下
// 操作 1 eid tow : 编号为eid的边，边权变成tow
// 操作 2 nid car : 编号为nid的点出发，车重为car，查询能到达几个不同的点
// 1 <= n <= 5 * 10^4    0 <= m <= 10^5
// 1 <= q <= 10^5        1 <= 其他数据 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5443
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXM = 100001;
//const int MAXQ = 100001;
//int n, m, q;
//int blen, bnum;
//
//int u[MAXM];
//int v[MAXM];
//int w[MAXM];
//
//int op[MAXQ];
//int eid[MAXQ];
//int tow[MAXQ];
//int nid[MAXQ];
//int car[MAXQ];
//
//int edge[MAXM];
//bool change[MAXM];
//int curw[MAXM];
//
//int operate[MAXQ];
//int query[MAXQ];
//int update[MAXQ];
//
//int fa[MAXN];
//int siz[MAXN];
//int rollback[MAXM][2];
//int opsize = 0;
//
//int arr1[MAXM];
//int arr2[MAXM];
//
//int ans[MAXQ];
//
//void build() {
//    for (int i = 1; i <= n; i++) {
//        fa[i] = i;
//        siz[i] = 1;
//    }
//}
//
//int find(int x) {
//    while (x != fa[x]) {
//        x = fa[x];
//    }
//    return x;
//}
//
//void Union(int x, int y) {
//    int fx = find(x), fy = find(y);
//    if (fx == fy) {
//        return;
//    }
//    if (siz[fx] < siz[fy]) {
//        swap(fx, fy);
//    }
//    fa[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//}
//
//void undo() {
//    for (int fx, fy; opsize > 0; opsize--) {
//        fx = rollback[opsize][0];
//        fy = rollback[opsize][1];
//        fa[fy] = fy;
//        siz[fx] -= siz[fy];
//    }
//}
//
//void merge() {
//    int siz1 = 0, siz2 = 0;
//    for (int i = 1; i <= m; i++) {
//        if (change[edge[i]]) {
//            arr1[++siz1] = edge[i];
//        } else {
//            arr2[++siz2] = edge[i];
//        }
//    }
//    sort(arr1 + 1, arr1 + siz1 + 1, [&](int x, int y) { return w[x] > w[y]; });
//    int i = 0, p1 = 1, p2 = 1;
//    while (p1 <= siz1 && p2 <= siz2) {
//        edge[++i] = w[arr1[p1]] >= w[arr2[p2]] ? arr1[p1++] : arr2[p2++];
//    }
//    while (p1 <= siz1) {
//        edge[++i] = arr1[p1++];
//    }
//    while (p2 <= siz2) {
//        edge[++i] = arr2[p2++];
//    }
//}
//
//void compute(int l, int r) {
//    build();
//    fill(change + 1, change + m + 1, false);
//    int cntu = 0, cntq = 0;
//    for (int i = l; i <= r; i++) {
//        if (op[operate[i]] == 1) {
//            change[eid[operate[i]]] = true;
//            update[++cntu] = operate[i];
//        } else {
//            query[++cntq] = operate[i];
//        }
//    }
//    sort(query + 1, query + cntq + 1, [&](int x, int y) { return car[x] > car[y]; });
//    for (int i = 1, j = 1; i <= cntq; i++) {
//        while (j <= m && w[edge[j]] >= car[query[i]]) {
//            if (!change[edge[j]]) {
//                Union(u[edge[j]], v[edge[j]]);
//            }
//            j++;
//        }
//        opsize = 0;
//        for (int k = 1; k <= cntu; k++) {
//            curw[eid[update[k]]] = w[eid[update[k]]];
//        }
//        for (int k = 1; k <= cntu && update[k] < query[i]; k++) {
//            curw[eid[update[k]]] = tow[update[k]];
//        }
//        for (int k = 1; k <= cntu; k++) {
//            if (curw[eid[update[k]]] >= car[query[i]]) {
//                Union(u[eid[update[k]]], v[eid[update[k]]]);
//            }
//        }
//        ans[query[i]] = siz[find(nid[query[i]])];
//        undo();
//    }
//    for (int i = 1; i <= cntu; i++) {
//        w[eid[update[i]]] = tow[update[i]];
//    }
//    merge();
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(q * log2(n)));
//    bnum = (q + blen - 1) / blen;
//    sort(edge + 1, edge + m + 1, [&](int x, int y) { return w[x] > w[y]; });
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> u[i] >> v[i] >> w[i];
//        edge[i] = i;
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> op[i];
//        if (op[i] == 1) {
//            cin >> eid[i] >> tow[i];
//        } else {
//            cin >> nid[i] >> car[i];
//        }
//        operate[i] = i;
//    }
//    prepare();
//    for (int i = 1, l, r; i <= bnum; i++) {
//        l = (i - 1) * blen + 1;
//        r = min(i * blen, q);
//        compute(l, r);
//    }
//    for (int i = 1; i <= q; i++) {
//        if (ans[i]) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}