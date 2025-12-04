package class185;

// 成都七中，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5311
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int l, r, id;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.r < y.r;
//}
//
//const int MAXN = 100001;
//int n, m;
//int color[MAXN];
//
//int headg[MAXN];
//int nxtg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headq[MAXN];
//int nxtq[MAXN];
//int ql[MAXN];
//int qr[MAXN];
//int qid[MAXN];
//int cntq;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int tree[MAXN];
//
//Node arrx[MAXN];
//int cntx;
//
//Node arry[MAXN];
//int cnty;
//
//int pos[MAXN];
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxtg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addQuery(int u, int l, int r, int id) {
//    nxtq[++cntq] = headq[u];
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qid[cntq] = id;
//    headq[u] = cntq;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    if (i <= 0) {
//        return;
//    }
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//int query(int l, int r) {
//    return sum(r) - sum(l - 1);
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = headg[u]; e > 0; e = nxtg[e]) {
//            int v = tog[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void dfs(int u, int fa, int nl, int nr) {
//    arrx[++cntx] = { nl, nr, color[u] };
//    for (int e = headq[u]; e > 0; e = nxtq[e]) {
//        int qui = qid[e];
//        int qul = ql[e];
//        int qur = qr[e];
//        if (qul <= nl && nr <= qur) {
//            arry[++cnty] = { qul, qur, qui };
//        }
//    }
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, min(v, nl), max(v, nr));
//        }
//    }
//}
//
//void calc(int u) {
//    cntx = cnty = 0;
//    dfs(u, 0, u, u);
//    sort(arrx + 1, arrx + cntx + 1, NodeCmp);
//    sort(arry + 1, arry + cnty + 1, NodeCmp);
//    for (int i = 1, j = 1; i <= cnty; i++) {
//        while (j <= cntx && arrx[j].r <= arry[i].r) {
//            if (arrx[j].l > pos[arrx[j].id]) {
//                add(pos[arrx[j].id], -1);
//                pos[arrx[j].id] = arrx[j].l;
//                add(pos[arrx[j].id], 1);
//            }
//            j++;
//        }
//        ans[arry[i].id] = max(ans[arry[i].id], query(arry[i].l, n));
//    }
//    for (int i = 1; i <= cntx; i++) {
//        add(pos[arrx[i].id], -1);
//        pos[arrx[i].id] = 0;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    calc(u);
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            solve(getCentroid(v, u));
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1, l, r, x; i <= m; i++) {
//        cin >> l >> r >> x;
//        addQuery(x, l, r, i);
//    }
//    solve(getCentroid(1, 0));
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}