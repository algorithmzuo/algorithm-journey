package class195;

// 监狱，C++版
// 一共有n个房间，给定n-1条无向边，所有房间组成一棵树
// 一共有m个囚犯，每个囚犯给出卧室房间号和工作室房间号，囚犯初始时都在卧室
// 每个囚犯的卧室和工作室一定不同，任何两个囚犯既不共用卧室，也不共用工作室
// 但是有可能某个房间，作为一个囚犯的卧室，同时作为另一个囚犯的工作室
// 你的任务是让所有囚犯从自己的卧室出发，只走最短路去自己的工作室
// 你可以随意下达指令，每条指令只能选择一个囚犯，沿一条边移动一步
// 任何时刻不能让任何房间里出现两个囚犯，打印你能否完成任务
// 1 <= n、m <= 1.2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P9520
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 120001;
//const int MAXT = MAXN * 50;
//const int MAXE = MAXN * 200;
//const int MAXP = 18;
//int t, n, m;
//
//int startTag[MAXN];
//int endTag[MAXN];
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
//    addEdge2(startTag[u], cntt);
//    addEdge2(startTag[fa], cntt);
//    stin[u][0] = ++cntt;
//    addEdge2(cntt, endTag[u]);
//    addEdge2(cntt, endTag[fa]);
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
//int nearest(int x, int y) {
//    if (isAncestor(y, x)) {
//        return kthAncestor(x, dep[x] - dep[y] - 1);
//    } else {
//        return stjump[y][0];
//    }
//}
//
//void pathSet(int x, int y, int prisoner) {
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    addEdge2(startTag[y], prisoner);
//    addEdge2(prisoner, endTag[y]);
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[x][p]] >= dep[y]) {
//            addEdge2(stout[x][p], prisoner);
//            addEdge2(prisoner, stin[x][p]);
//            x = stjump[x][p];
//        }
//    }
//    if (x == y) {
//        return;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[x][p] != stjump[y][p]) {
//            addEdge2(stout[x][p], prisoner);
//            addEdge2(stout[y][p], prisoner);
//            addEdge2(prisoner, stin[x][p]);
//            addEdge2(prisoner, stin[y][p]);
//            x = stjump[x][p];
//            y = stjump[y][p];
//        }
//    }
//    addEdge2(stout[x][0], prisoner);
//    addEdge2(prisoner, stin[x][0]);
//}
//
//void link(int x, int y) {
//    int prisoner = ++cntt;
//    addEdge2(prisoner, startTag[x]);
//    addEdge2(prisoner, endTag[x]);
//    addEdge2(startTag[y], prisoner);
//    addEdge2(endTag[y], prisoner);
//    if (stjump[x][0] != y && stjump[y][0] != x) {
//        int a = nearest(y, x);
//        int b = nearest(x, y);
//        pathSet(a, b, prisoner);
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
//    while (qi <= qsiz) {
//        int u = que[qi++];
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
//void clear() {
//    for (int i = 1; i <= n; i++) {
//        head1[i] = 0;
//    }
//    for (int i = 1; i <= cntt; i++) {
//        head2[i] = indegree[i] = 0;
//    }
//    cnt1 = cnt2 = cntt = cntd = 0;
//    dep[1] = 0;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n;
//        cntt = n << 1;
//        for (int i = 1; i <= n; i++) {
//            startTag[i] = i;
//            endTag[i] = i + n;
//        }
//        for (int i = 1, u, v; i < n; i++) {
//            cin >> u >> v;
//            addEdge1(u, v);
//            addEdge1(v, u);
//        }
//        build(1, 1);
//        cin >> m;
//        for (int i = 1, x, y; i <= m; i++) {
//            cin >> x >> y;
//            link(x, y);
//        }
//        bool ans = topo();
//        cout << (ans ? "Yes" : "No") << "\n";
//        clear();
//    }
//    return 0;
//}