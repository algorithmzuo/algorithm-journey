package class185;

// 捉迷藏，正解是树的括号序，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXS = MAXN * 3;
//const int MAXT = MAXS << 2;
//const int INF = 1000000001;
//const int PAR = -1;
//const int PAL = -2;
//int n, m;
//bool light[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int seg[MAXS];
//int cntd;
//
//int pr[MAXT];
//int pl[MAXT];
//int ladd[MAXT];
//int lminus[MAXT];
//int radd[MAXT];
//int rminus[MAXT];
//int distv[MAXT];
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
//void setSingle(int i, int v) {
//    pr[i] = pl[i] = 0;
//    ladd[i] = lminus[i] = radd[i] = rminus[i] = -INF;
//    distv[i] = -INF;
//    if (v == PAR) {
//        pr[i] = 1;
//    } else if (v == PAL) {
//        pl[i] = 1;
//    } else if (!light[v]) {
//    	ladd[i] = lminus[i] = radd[i] = rminus[i] = 0;
//    }
//}
//
//void up(int i) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    if (pl[l] > pr[r]) {
//        pr[i] = pr[l];
//        pl[i] = pl[l] - pr[r] + pl[r];
//    } else {
//        pr[i] = pr[l] + pr[r] - pl[l];
//        pl[i] = pl[r];
//    }
//    ladd[i] = max(ladd[l], max(pr[l] + ladd[r] - pl[l], pr[l] + pl[l] + lminus[r]));
//    lminus[i] = max(lminus[l], pl[l] - pr[l] + lminus[r]);
//    radd[i] = max(radd[r], max(radd[l] - pr[r] + pl[r], rminus[l] + pr[r] + pl[r]));
//    rminus[i] = max(rminus[r], rminus[l] + pr[r] - pl[r]);
//    distv[i] = max(max(distv[l], distv[r]), max(radd[l] + lminus[r], ladd[r] + rminus[l]));
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        setSingle(i, seg[l]);
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
//        setSingle(i, seg[l]);
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
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    build(1, cntd, 1);
//    cin >> m;
//    int offCnt = n;
//    char op;
//    for (int i = 1, x; i <= m; i++) {
//        cin >> op;
//        if (op == 'C') {
//            cin >> x;
//            light[x] = !light[x];
//            if (!light[x]) {
//                offCnt++;
//            } else {
//                offCnt--;
//            }
//            update(dfn[x], 1, cntd, 1);
//        } else {
//            if (offCnt <= 1) {
//                cout << (offCnt - 1) << '\n';
//            } else {
//                cout << distv[1] << '\n';
//            }
//        }
//    }
//    return 0;
//}