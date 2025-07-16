package class174;

// 最小公倍数，C++版
// 有n个点组成的无向图，依次给出m条无向边，每条边都有边权，并且边权很特殊
// u v a b : u到v的边，边权 = 2的a次方 * 3的b次方
// 接下来有q条查询，每条查询的格式如下
// u v a b : 从u出发可以随意选择边到达v，打印是否存在一条路径，满足如下条件
//           路径上所有边权的最小公倍数 = 2的a次方 * 3的b次方
// 1 <= n、q <= 5 * 10^4
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3247
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXM = 100001;
//const int MAXQ = 50001;
//int n, m, q;
//int blen, bnum;
//
//int eu[MAXM];
//int ev[MAXM];
//int ea[MAXM];
//int eb[MAXM];
//
//int qu[MAXQ];
//int qv[MAXQ];
//int qa[MAXQ];
//int qb[MAXQ];
//
//int edge[MAXM];
//int query[MAXQ];
//
//int cur[MAXQ];
//int cursiz;
//
//int fa[MAXN];
//int siz[MAXN];
//int maxa[MAXN];
//int maxb[MAXN];
//int rollback[MAXN][5];
//int opsize = 0;
//
//bool ans[MAXQ];
//
//void build() {
//    for (int i = 1; i <= n; i++) {
//        fa[i] = i;
//        siz[i] = 1;
//        maxa[i] = -1;
//        maxb[i] = -1;
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
//void Union(int x, int y, int a, int b) {
//    int fx = find(x), fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        swap(fx, fy);
//    }
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//    rollback[opsize][2] = siz[fx];
//    rollback[opsize][3] = maxa[fx];
//    rollback[opsize][4] = maxb[fx];
//    if (fx != fy) {
//        fa[fy] = fx;
//        siz[fx] += siz[fy];
//    }
//    maxa[fx] = max(max(maxa[fx], maxa[fy]), a);
//    maxb[fx] = max(max(maxb[fx], maxb[fy]), b);
//}
//
//void undo() {
//    for (int fx, fy; opsize > 0; opsize--) {
//        fx = rollback[opsize][0];
//        fy = rollback[opsize][1];
//        fa[fy] = fy;
//        siz[fx] = rollback[opsize][2];
//        maxa[fx] = rollback[opsize][3];
//        maxb[fx] = rollback[opsize][4];
//    }
//}
//
//bool check(int x, int y, int a, int b) {
//    int fx = find(x), fy = find(y);
//    return fx == fy && maxa[fx] == a && maxb[fx] == b;
//}
//
//void compute(int l, int r) {
//    build();
//    cursiz = 0;
//    for (int i = 1; i <= q; i++) {
//        if (ea[edge[l]] <= qa[query[i]] && (r + 1 > m || qa[query[i]] < ea[edge[r + 1]])) {
//            cur[++cursiz] = query[i];
//        }
//    }
//    if (cursiz > 0) {
//        sort(edge + 1, edge + l, [&](int x, int y) { return eb[x] < eb[y]; });
//        for (int i = 1, j = 1; i <= cursiz; i++) {
//            while (j < l && eb[edge[j]] <= qb[cur[i]]) {
//                Union(eu[edge[j]], ev[edge[j]], ea[edge[j]], eb[edge[j]]);
//                j++;
//            }
//            opsize = 0;
//            for (int k = l; k <= r; k++) {
//                if (ea[edge[k]] <= qa[cur[i]] && eb[edge[k]] <= qb[cur[i]]) {
//            	      Union(eu[edge[k]], ev[edge[k]], ea[edge[k]], eb[edge[k]]);
//                }
//            }
//            ans[cur[i]] = check(qu[cur[i]], qv[cur[i]], qa[cur[i]], qb[cur[i]]);
//            undo();
//        }
//    }
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(m * log2(n)));
//    bnum = (m + blen - 1) / blen;
//    sort(edge + 1, edge + m + 1, [&](int x, int y) { return ea[x] < ea[y]; });
//    sort(query + 1, query + q + 1, [&](int x, int y) { return qb[x] < qb[y]; });
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> eu[i] >> ev[i] >> ea[i] >> eb[i];
//        edge[i] = i;
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> qu[i] >> qv[i] >> qa[i] >> qb[i];
//        query[i] = i;
//    }
//    prepare();
//    for (int i = 1, l, r; i <= bnum; i++) {
//        l = (i - 1) * blen + 1;
//        r = min(i * blen, m);
//        compute(l, r);
//    }
//    for (int i = 1; i <= q; i++) {
//        cout << (ans[i] ? "Yes" : "No") << '\n';
//    }
//    return 0;
//}