package class195;

// 美丽的树，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF1904F
// 测试链接 : https://codeforces.com/problemset/problem/1904/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXT = MAXN * 41;
//const int MAXE = MAXN * 201;
//const int MAXP = 17;
//int n, m;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int cnt1;
//
//int indegree[MAXT];
//int head2[MAXT];
//int next2[MAXE];
//int to2[MAXE];
//int cnt2;
//
//int dep[MAXN];
//int dfn[MAXN];
//int siz[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int stout[MAXN][MAXP];
//int stin[MAXN][MAXP];
//int cntt;
//
//int que[MAXT];
//int ans[MAXN];
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    indegree[v]++;
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void build(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    dfn[u] = ++cntd;
//    siz[u] = 1;
//    stjump[u][0] = fa;
//    stout[u][0] = ++cntt;
//    addEdge2(u, cntt);
//    addEdge2(fa, cntt);
//    stin[u][0] = ++cntt;
//    addEdge2(cntt, u);
//    addEdge2(cntt, fa);
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//        stout[u][p] = ++cntt;
//        addEdge2(stout[u][p - 1], cntt);
//        addEdge2(stout[stjump[u][p - 1]][p - 1], cntt);
//        stin[u][p] = ++cntt;
//        addEdge2(cntt, stin[u][p - 1]);
//        addEdge2(cntt, stin[stjump[u][p - 1]][p - 1]);
//    }
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa) {
//            build(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//bool isAncestor(int a, int b) {
//    return dfn[a] <= dfn[b] && dfn[b] < dfn[a] + siz[a];
//}
//
//int kthAncestor(int x, int k) {
//    for (int p = 0; p < MAXP; p++) {
//        if (((k >> p) & 1) != 0) {
//            x = stjump[x][p];
//        }
//    }
//    return x;
//}
//
//int nearest(int x, int c) {
//    if (isAncestor(c, x)) {
//        return kthAncestor(x, dep[x] - dep[c] - 1);
//    } else {
//        return stjump[c][0];
//    }
//}
//
//void pathOut(int x, int y, int c) {
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    addEdge2(y, c);
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[x][p]] >= dep[y]) {
//            addEdge2(stout[x][p], c);
//            x = stjump[x][p];
//        }
//    }
//    if (x == y) {
//        return;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[x][p] != stjump[y][p]) {
//            addEdge2(stout[x][p], c);
//            addEdge2(stout[y][p], c);
//            x = stjump[x][p];
//            y = stjump[y][p];
//        }
//    }
//    addEdge2(stout[x][0], c);
//}
//
//void pathIn(int x, int y, int c) {
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    addEdge2(c, y);
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[x][p]] >= dep[y]) {
//            addEdge2(c, stin[x][p]);
//            x = stjump[x][p];
//        }
//    }
//    if (x == y) {
//        return;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[x][p] != stjump[y][p]) {
//            addEdge2(c, stin[x][p]);
//            addEdge2(c, stin[y][p]);
//            x = stjump[x][p];
//            y = stjump[y][p];
//        }
//    }
//    addEdge2(c, stin[x][0]);
//}
//
//void pathMin(int a, int b, int c) {
//    if (a != c) {
//        pathIn(a, nearest(a, c), c);
//    }
//    if (b != c) {
//        pathIn(b, nearest(b, c), c);
//    }
//}
//
//void pathMax(int a, int b, int c) {
//    if (a != c) {
//        pathOut(a, nearest(a, c), c);
//    }
//    if (b != c) {
//        pathOut(b, nearest(b, c), c);
//    }
//}
//
//bool topo() {
//    int qi = 1, qsiz = 0;
//    for (int i = 1; i <= cntt; i++) {
//        if (indegree[i] == 0) {
//            que[++qsiz] = i;
//        }
//    }
//    int val = 0;
//    while (qi <= qsiz) {
//        int u = que[qi++];
//        if (u >= 1 && u <= n) {
//            ans[u] = ++val;
//        }
//        for (int e = head2[u]; e > 0; e = next2[e]) {
//            int v = to2[e];
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
//    cin >> n >> m;
//    cntt = n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    build(1, 1);
//    for (int i = 1, op, a, b, c; i <= m; i++) {
//        cin >> op >> a >> b >> c;
//        if (op == 1) {
//            pathMin(a, b, c);
//        } else {
//            pathMax(a, b, c);
//        }
//    }
//    bool check = topo();
//    if (check) {
//        for (int i = 1; i <= n; i++) {
//            cout << ans[i] << " ";
//        }
//    } else {
//        cout << -1 << "\n";
//    }
//    return 0;
//}