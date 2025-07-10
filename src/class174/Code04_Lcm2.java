package class174;

// 最小公倍数，C++版
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
//int qid[MAXQ];
//
//int arre[MAXM];
//int arrq[MAXQ];
//
//int cur[MAXQ];
//int cntq = 0;
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
//bool query(int x, int y, int a, int b) {
//    int fx = find(x), fy = find(y);
//    return fx == fy && maxa[fx] == a && maxb[fx] == b;
//}
//
//void compute(int l, int r) {
//    build();
//    cntq = 0;
//    for (int i = 1; i <= q; i++) {
//        if (ea[arre[l]] <= qa[arrq[i]] && (r + 1 > m || qa[arrq[i]] < ea[arre[r + 1]])) {
//            cur[++cntq] = arrq[i];
//        }
//    }
//    if (cntq > 0) {
//        sort(arre + 1, arre + l, [&](int x, int y) { return eb[x] < eb[y]; });
//        int pos = 1;
//        for (int i = 1; i <= cntq; i++) {
//            for (int edge = arre[pos]; pos < l && eb[edge] <= qb[cur[i]]; pos++, edge = arre[pos]) {
//                Union(eu[edge], ev[edge], ea[edge], eb[edge]);
//            }
//            opsize = 0;
//            for (int j = l; j <= r; j++) {
//                int edge = arre[j];
//                if (ea[edge] <= qa[cur[i]] && eb[edge] <= qb[cur[i]]) {
//            	    Union(eu[edge], ev[edge], ea[edge], eb[edge]);
//                }
//            }
//            ans[qid[cur[i]]] = query(qu[cur[i]], qv[cur[i]], qa[cur[i]], qb[cur[i]]);
//            undo();
//        }
//    }
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(m * log2(n)));
//    bnum = (m + blen - 1) / blen;
//    for (int i = 1; i <= m; i++) {
//        arre[i] = i;
//    }
//    for (int i = 1; i <= q; i++) {
//        arrq[i] = i;
//    }
//    sort(arre + 1, arre + m + 1, [&](int x, int y) { return ea[x] < ea[y]; });
//    sort(arrq + 1, arrq + q + 1, [&](int x, int y) { return qb[x] < qb[y]; });
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> eu[i] >> ev[i] >> ea[i] >> eb[i];
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> qu[i] >> qv[i] >> qa[i] >> qb[i];
//        qid[i] = i;
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