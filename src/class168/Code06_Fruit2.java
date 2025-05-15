package class168;

// 接水果，C++版
// 一共有n个点，给定n-1条无向边，所有点连成一棵树
// 一共有p个盘子，每个盘子格式 a b c : 盘子是点a到点b的路径，盘子权值为c
// 一共有q个水果，每个水果格式 u v k : 水果是点u到点v的路径，k含义如下
// 如果一个盘子路径完全在一个水果路径的内部，那么该盘子可以接住该水果
// 那么对于每个水果，可能有很多盘子都可以将其接住，打印其中第k小的权值
// 1 <= n、p、q <= 4 * 10^4
// 0 <= 盘子权值 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3242
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int op, x, yl, yr, v, d;
//};
//
//bool EventCmp(Event e1, Event e2) {
//    if (e1.x != e2.x) {
//        return e1.x < e2.x;
//    }
//    return e1.op < e2.op;
//}
//
//const int MAXN = 40001;
//const int MAXH = 16;
//const int INF = 1000000001;
//int n, p, q;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int dep[MAXN];
//int ldfn[MAXN];
//int rdfn[MAXN];
//int stjump[MAXN][MAXH];
//int cntd = 0;
//
//int tree[MAXN];
//
//Event event[MAXN << 3];
//int cnte = 0;
//
//Event lset[MAXN << 3];
//Event rset[MAXN << 3];
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    ldfn[u] = ++cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if (to[e] != fa) {
//            dfs(to[e], u);
//        }
//    }
//    rdfn[u] = cntd;
//}
//
//int lca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        int tmp = a;
//        a = b;
//        b = tmp;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int lcaSon(int a, int b) {
//    if (dep[a] < dep[b]) {
//        int tmp = a;
//        a = b;
//        b = tmp;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] > dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    return a;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//void add(int l, int r, int v) {
//    add(l, v);
//    add(r + 1, -v);
//}
//
//int query(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void addPlate(int x, int yl, int yr, int v, int d) {
//    event[++cnte].op = 1;
//    event[cnte].x = x;
//    event[cnte].yl = yl;
//    event[cnte].yr = yr;
//    event[cnte].v = v;
//    event[cnte].d = d;
//}
//
//void addFruit(int x, int y, int v, int d) {
//    event[++cnte].op = 2;
//    event[cnte].x = x;
//    event[cnte].yl = y;
//    event[cnte].v = v;
//    event[cnte].d = d;
//}
//
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            if (event[i].op == 2) {
//                ans[event[i].d] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        int lsiz = 0, rsiz = 0;
//        for (int i = ql; i <= qr; i++) {
//            if (event[i].op == 1) {
//                if (event[i].v <= mid) {
//                    add(event[i].yl, event[i].yr, event[i].d);
//                    lset[++lsiz] = event[i];
//                } else {
//                    rset[++rsiz] = event[i];
//                }
//            } else {
//                int check = query(event[i].yl);
//                if (check >= event[i].v) {
//                    lset[++lsiz] = event[i];
//                } else {
//                    event[i].v -= check;
//                    rset[++rsiz] = event[i];
//                }
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            event[ql + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            event[ql + lsiz + i - 1] = rset[i];
//        }
//        compute(ql, ql + lsiz - 1, vl, mid);
//        compute(ql + lsiz, qr, mid + 1, vr);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> p >> q;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    for (int i = 1, a, b, c; i <= p; i++) {
//        cin >> a >> b >> c;
//        if (ldfn[a] > ldfn[b]) {
//            int tmp = a;
//            a = b;
//            b = tmp;
//        }
//        int ablca = lca(a, b);
//        if (ablca == a) {
//            int son = lcaSon(a, b);
//            addPlate(1, ldfn[b], rdfn[b], c, 1);
//            addPlate(ldfn[son], ldfn[b], rdfn[b], c, -1);
//            addPlate(ldfn[b], rdfn[son] + 1, n, c, 1);
//            addPlate(rdfn[b] + 1, rdfn[son] + 1, n, c, -1);
//        } else {
//            addPlate(ldfn[a], ldfn[b], rdfn[b], c, 1);
//            addPlate(rdfn[a] + 1, ldfn[b], rdfn[b], c, -1);
//        }
//    }
//    for (int i = 1, u, v, k; i <= q; i++) {
//        cin >> u >> v >> k;
//        addFruit(min(ldfn[u], ldfn[v]), max(ldfn[u], ldfn[v]), k, i);
//    }
//    sort(event + 1, event + cnte + 1, EventCmp);
//    compute(1, cnte, 0, INF);
//    for (int i = 1; i <= q; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}