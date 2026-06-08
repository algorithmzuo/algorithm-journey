package class200;

// 仙人掌路径翻转，C++版
// 给定n个点、m条边的仙人掌图，没有自环，没有重边，1号节点是仙人掌的根
// 输入保证每个简单环的边数都为奇数，所以点x到根的最短路和最长路都是唯一的
// 以点x为头的子仙人掌，是指删掉根到x路径上的边后，包含x的连通块
// 每个节点只有黑白两种颜色，初始时所有节点为黑，一共有q条操作，类型如下
// 操作 1 x : 点x到根的最短路上所有节点，颜色翻转
// 操作 2 x : 点x到根的最长路上所有节点，颜色翻转
// 操作 3 x : 查询以点x为头的子仙人掌中，黑色节点的数量
// 1 <= n、m、q <= 5 * 10^4
// 测试链接 : https://uoj.ac/problem/158
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m, q, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN];
//int to2[MAXN];
//int cnt2;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int stasiz;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int seg[MAXN];
//
//int belong[MAXN];
//int pos[MAXN];
//int cycleRoot[MAXN];
//int cycleOther[MAXN];
//
//int nodeType[MAXN];
//
//int cyclel[MAXN];
//int cycler[MAXN];
//
//int treel[MAXN];
//int treer[MAXN];
//
//int all1[MAXN << 2];
//int black1[MAXN << 2];
//bool lazy1[MAXN << 2];
//
//int all2[MAXN << 2];
//int black2[MAXN << 2];
//bool lazy2[MAXN << 2];
//
//int all3[MAXN << 2];
//int black3[MAXN << 2];
//bool lazy3[MAXN << 2];
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void cycleLink(int u, int v) {
//    cntn++;
//    cycleRoot[cntn] = u;
//    addEdge2(u, cntn);
//    int tmp = stasiz;
//    int pop;
//    int cnt = 0;
//    do {
//        pop = sta[tmp--];
//        cnt++;
//    } while (pop != v);
//    cycleOther[cntn] = cnt;
//    do {
//        pop = sta[stasiz--];
//        belong[pop] = cntn;
//        pos[pop] = cnt--;
//        addEdge2(cntn, pop);
//    } while (pop != v);
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++stasiz] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            if (low[v] < dfn[u]) {
//                low[u] = min(low[u], low[v]);
//            } else if (low[v] > dfn[u]) {
//                stasiz--;
//                addEdge2(u, v);
//            } else {
//                cycleLink(u, v);
//            }
//        } else if (dfn[v] < dfn[u]) {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != f) {
//            dfs1(v, u);
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void cycleDfn(int u) {
//    int h = son[u];
//    bool near = pos[h] * 2 <= cycleOther[u];
//    cyclel[u] = cntd + 1;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa[u] && v != h) {
//            if ((near && pos[v] < pos[h]) || (!near && pos[v] > pos[h])) {
//                nodeType[v] = 1;
//            } else {
//                nodeType[v] = 2;
//            }
//            dfn[v] = ++cntd;
//            seg[cntd] = v;
//        }
//    }
//    cycler[u] = cntd;
//    dfn[h] = ++cntd;
//    seg[cntd] = h;
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if (dfn[u] == 0) {
//        dfn[u] = ++cntd;
//        seg[cntd] = u;
//    }
//    if (u > n) {
//        cycleDfn(u);
//    }
//    treel[u] = cntd + 1;
//    if (son[u] != 0) {
//        dfs2(son[u], t);
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//    treer[u] = cntd;
//}
//
//void up(int i) {
//    all1[i] = all1[i << 1] + all1[i << 1 | 1];
//    all2[i] = all2[i << 1] + all2[i << 1 | 1];
//    all3[i] = all3[i << 1] + all3[i << 1 | 1];
//    black1[i] = black1[i << 1] + black1[i << 1 | 1];
//    black2[i] = black2[i << 1] + black2[i << 1 | 1];
//    black3[i] = black3[i << 1] + black3[i << 1 | 1];
//}
//
//void reverse1(int i) {
//    black1[i] = all1[i] - black1[i];
//    lazy1[i] = !lazy1[i];
//}
//
//void reverse2(int i) {
//    black2[i] = all2[i] - black2[i];
//    lazy2[i] = !lazy2[i];
//}
//
//void reverse3(int i) {
//    black3[i] = all3[i] - black3[i];
//    lazy3[i] = !lazy3[i];
//}
//
//void down(int i) {
//    if (lazy1[i]) {
//        reverse1(i << 1);
//        reverse1(i << 1 | 1);
//        lazy1[i] = false;
//    }
//    if (lazy2[i]) {
//        reverse2(i << 1);
//        reverse2(i << 1 | 1);
//        lazy2[i] = false;
//    }
//    if (lazy3[i]) {
//        reverse3(i << 1);
//        reverse3(i << 1 | 1);
//        lazy3[i] = false;
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        int u = seg[l];
//        int t = nodeType[u];
//        if (u <= n) {
//            if (t == 1) {
//                all1[i] = black1[i] = 1;
//            }
//            if (t == 2) {
//                all2[i] = black2[i] = 1;
//            }
//            if (t == 3) {
//                all3[i] = black3[i] = 1;
//            }
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void reverse(int jobl, int jobr, int jobt, int l, int r, int i) {
//    if (jobl > jobr) {
//        return;
//    }
//    if (jobl <= l && r <= jobr) {
//        if (jobt == 1 || jobt == 3) {
//            reverse1(i);
//        }
//        if (jobt == 2 || jobt == 3) {
//            reverse2(i);
//        }
//        reverse3(i);
//        return;
//    }
//    down(i);
//    int mid = (l + r) >> 1;
//    if (jobl <= mid) {
//        reverse(jobl, jobr, jobt, l, mid, i << 1);
//    }
//    if (mid < jobr) {
//        reverse(jobl, jobr, jobt, mid + 1, r, i << 1 | 1);
//    }
//    up(i);
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl > jobr) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return black1[i] + black2[i] + black3[i];
//    }
//    down(i);
//    int mid = (l + r) >> 1;
//    int ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, i << 1);
//    }
//    if (mid < jobr) {
//        ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
//    }
//    return ans;
//}
//
//void flipCycle(int u, int x, int op) {
//    int h = son[u];
//    bool near = pos[x] * 2 <= cycleOther[u];
//    if ((near && op == 1) || (!near && op == 2)) {
//        reverse(cyclel[u], dfn[x], 3, 1, cntn, 1);
//        if (pos[h] < pos[x]) {
//            reverse(dfn[h], dfn[h], 3, 1, cntn, 1);
//        }
//    } else {
//        reverse(dfn[x], cycler[u], 3, 1, cntn, 1);
//        if (pos[h] > pos[x]) {
//            reverse(dfn[h], dfn[h], 3, 1, cntn, 1);
//        }
//    }
//}
//
//void flip(int x, int op) {
//    while (x != 0) {
//        int xtop = top[x];
//        if (x == xtop) {
//            if (belong[x] != 0) {
//                flipCycle(belong[x], x, op);
//                x = cycleRoot[belong[x]];
//            } else {
//                reverse(dfn[x], dfn[x], op, 1, cntn, 1);
//                x = fa[x];
//            }
//        } else if (xtop <= n) {
//            if (belong[xtop] != 0) {
//                reverse(dfn[son[xtop]], dfn[x], op, 1, cntn, 1);
//                x = xtop;
//            } else {
//                reverse(dfn[xtop], dfn[x], op, 1, cntn, 1);
//                x = fa[xtop];
//            }
//        } else {
//            reverse(cyclel[xtop], dfn[x], op, 1, cntn, 1);
//            x = fa[xtop];
//        }
//    }
//}
//
//int query(int x) {
//    if (belong[x] == 0 || x == son[belong[x]]) {
//        return query(dfn[x], treer[x], 1, cntn, 1);
//    } else {
//        return query(dfn[x], dfn[x], 1, cntn, 1) + query(treel[x], treer[x], 1, cntn, 1);
//    }
//}
//
//void prepare() {
//    tarjan(1, 0);
//    cntd = 0;
//    for (int i = 1; i <= n; i++) {
//        dfn[i] = 0;
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    for (int i = 1; i <= n; i++) {
//        if (nodeType[i] == 0) {
//            nodeType[i] = 3;
//        }
//    }
//    build(1, cntn, 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    cntn = n;
//    cnt1 = 1;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    prepare();
//    for (int i = 1, op, x; i <= q; i++) {
//        cin >> op >> x;
//        if (op == 1 || op == 2) {
//            flip(x, op);
//        } else {
//            cout << query(x) << "\n";
//        }
//    }
//    return 0;
//}