package class174;

// 桥梁，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5443
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m, q;
//int blen, bnum;
//int u[MAXN];
//int v[MAXN];
//int w[MAXN];
//
//int op[MAXN];
//int a[MAXN];
//int b[MAXN];
//
//int eid[MAXN];
//int change[MAXN];
//int unchange[MAXN];
//
//int qid[MAXN];
//int update[MAXN];
//int query[MAXN];
//
//int fa[MAXN];
//int siz[MAXN];
//int rollback[MAXN][2];
//int opsize = 0;
//
//bool vis[MAXN];
//int curw[MAXN];
//int ans[MAXN];
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
//void merge(int l1, int r1, int l2, int r2) {
//    int i = 0;
//    while (l1 <= r1 && l2 <= r2) {
//        eid[++i] = w[change[l1]] >= w[unchange[l2]] ? change[l1++] : unchange[l2++];
//    }
//    while (l1 <= r1) {
//        eid[++i] = change[l1++];
//    }
//    while (l2 <= r2) {
//        eid[++i] = unchange[l2++];
//    }
//}
//
//void compute(int l, int r) {
//    build();
//    fill(vis + 1, vis + m + 1, false);
//    int cntu = 0, cntq = 0;
//    for (int i = l; i <= r; i++) {
//        if (op[qid[i]] == 1) {
//            vis[a[qid[i]]] = true;
//            update[++cntu] = qid[i];
//        } else {
//            query[++cntq] = qid[i];
//        }
//    }
//    sort(query + 1, query + cntq + 1, [&](int x, int y) { return b[x] > b[y]; });
//    int k = 1;
//    for (int i = 1; i <= cntq; i++) {
//        for (; k <= m && w[eid[k]] >= b[query[i]]; k++) {
//            if (!vis[eid[k]]) {
//                Union(u[eid[k]], v[eid[k]]);
//            }
//        }
//        opsize = 0;
//        for (int j = 1; j <= cntu; j++) {
//            curw[a[update[j]]] = w[a[update[j]]];
//        }
//        for (int j = 1; j <= cntu; j++) {
//            if (update[j] < query[i]) {
//                curw[a[update[j]]] = b[update[j]];
//            }
//        }
//        for (int j = 1; j <= cntu; j++) {
//            if (curw[a[update[j]]] >= b[query[i]]) {
//                Union(u[a[update[j]]], v[a[update[j]]]);
//            }
//        }
//        ans[query[i]] = siz[find(a[query[i]])];
//        undo();
//    }
//    for (int i = 1; i <= cntu; i++) {
//        w[a[update[i]]] = b[update[i]];
//    }
//    int siz1 = 0, siz2 = 0;
//    for (int i = 1; i <= m; i++) {
//        if (vis[eid[i]]) {
//            change[++siz1] = eid[i];
//        } else {
//            unchange[++siz2] = eid[i];
//        }
//    }
//    if (siz1 > 0) {
//        std::sort(change + 1, change + siz1 + 1, [&](int x, int y) { return w[x] > w[y]; });
//    }
//    merge(1, siz1, 1, siz2);
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(q * log2(n)));
//    bnum = (q + blen - 1) / blen;
//    for (int i = 1; i <= m; i++) {
//        eid[i] = i;
//    }
//    for (int i = 1; i <= q; i++) {
//        qid[i] = i;
//    }
//    std::sort(eid + 1, eid + m + 1, [&](int x, int y) { return w[x] > w[y]; });
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> u[i] >> v[i] >> w[i];
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> op[i] >> a[i] >> b[i];
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