package class185;

// 捉迷藏，最优解，树的括号序 + 巧妙线段树，C++版
// 树上有n个点，点的初始颜色为黑，给定n-1条边，边权都是1
// 一共有m条操作，每条操作是如下两种类型中的一种
// 操作 C x : 改变点x的颜色，黑变成白，白变成黑
// 操作 G   : 打印最远的两个黑点的距离，只有一个黑点打印0，无黑点打印-1
// 1 <= n <= 10^5    1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 12;
//const int INF = 1000000001;
//const int PAR = -1;
//const int PAL = -2;
//int n, m;
//bool black[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int seg[MAXN * 3];
//int cntd;
//
//int pr[MAXT];
//int pl[MAXT];
//int ladd[MAXT];
//int lminus[MAXT];
//int radd[MAXT];
//int rminus[MAXT];
//int dist[MAXT];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int fa) {
//    seg[++cntd] = PAL;
//    seg[++cntd] = u;
//    dfn[u] = cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//    seg[++cntd] = PAR;
//}
//
//void up(int i) {
//    int pre = i << 1;
//    int post = i << 1 | 1;
//    if (pl[pre] > pr[post]) {
//        pr[i] = pr[pre];
//        pl[i] = pl[pre] - pr[post] + pl[post];
//    } else {
//        pr[i] = pr[pre] + pr[post] - pl[pre];
//        pl[i] = pl[post];
//    }
//    ladd[i] = max(ladd[pre], max(pr[pre] + ladd[post] - pl[pre], pr[pre] + pl[pre] + lminus[post]));
//    lminus[i] = max(lminus[pre], pl[pre] - pr[pre] + lminus[post]);
//    radd[i] = max(radd[post], max(radd[pre] - pr[post] + pl[post], rminus[pre] + pr[post] + pl[post]));
//    rminus[i] = max(rminus[post], rminus[pre] + pr[post] - pl[post]);
//    dist[i] = max(max(dist[pre], dist[post]), max(radd[pre] + lminus[post], rminus[pre] + ladd[post]));
//}
//
//void point(int i, int v) {
//    pr[i] = pl[i] = 0;
//    ladd[i] = lminus[i] = radd[i] = rminus[i] = dist[i] = -INF;
//    if (v == PAR) {
//        pr[i] = 1;
//    } else if (v == PAL) {
//        pl[i] = 1;
//    } else if (black[v]) {
//        ladd[i] = lminus[i] = radd[i] = rminus[i] = dist[i] = 0;
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        point(i, seg[l]);
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void update(int jobi, int l, int r, int i) {
//    if (l == r) {
//        point(i, seg[l]);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            update(jobi, l, mid, i << 1);
//        } else {
//            update(jobi, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        black[i] = true;
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    build(1, cntd, 1);
//    cin >> m;
//    int blackCnt = n;
//    char op;
//    for (int i = 1, x; i <= m; i++) {
//        cin >> op;
//        if (op == 'C') {
//            cin >> x;
//            black[x] = !black[x];
//            if (black[x]) {
//                blackCnt++;
//            } else {
//                blackCnt--;
//            }
//            update(dfn[x], 1, cntd, 1);
//        } else {
//            if (blackCnt <= 1) {
//                cout << (blackCnt - 1) << '\n';
//            } else {
//                cout << dist[1] << '\n';
//            }
//        }
//    }
//    return 0;
//}