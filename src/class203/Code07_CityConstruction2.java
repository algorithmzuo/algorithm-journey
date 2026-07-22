package class203;

// 城市建设，C++版
// 给定一个n个点、m条边的无向图
// 接下来有q次修改，每次把某条边的权值修改为新值
// 每次修改之后，打印当前图的最小生成树边权和
// 1 <= n <= 2 * 10^4
// 1 <= m、q <= 5 * 10^4
// 0 <= 边权 <= 5 * 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3206
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 200001;
//const int MAXQ = 50001;
//const int MAXT = 4000001;
//int n, m, q;
//
//int ex[MAXN];
//int ey[MAXN];
//int ew[MAXN];
//int edgeCnt;
//
//int changeEdge[MAXQ];
//int endTime[MAXN];
//
//int head[MAXQ << 2];
//int nxt[MAXT];
//int toEdge[MAXT];
//int taskCnt;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int maxEdge[MAXN];
//
//int rollbackEdge[MAXN << 1];
//int rollbackType[MAXN << 1];
//int opsize;
//
//ll mstSum;
//ll ans[MAXQ];
//
//void addTask(int i, int e) {
//    nxt[++taskCnt] = head[i];
//    toEdge[taskCnt] = e;
//    head[i] = taskCnt;
//}
//
//void add(int jobl, int jobr, int jobe, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addTask(i, jobe);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobe, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobe, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void up(int x) {
//    maxEdge[x] = x <= n ? 0 : x - n;
//    if (ew[maxEdge[ls[x]]] > ew[maxEdge[x]]) {
//        maxEdge[x] = maxEdge[ls[x]];
//    }
//    if (ew[maxEdge[rs[x]]] > ew[maxEdge[x]]) {
//        maxEdge[x] = maxEdge[rs[x]];
//    }
//}
//
//bool isroot(int x) {
//    return ls[fa[x]] != x && rs[fa[x]] != x;
//}
//
//int lr(int x) {
//    return ls[fa[x]] == x ? 0 : 1;
//}
//
//void reverse(int x) {
//    if (x != 0) {
//        swap(ls[x], rs[x]);
//        rev[x] = !rev[x];
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//}
//
//void rotate(int x) {
//    int f = fa[x], g = fa[f];
//    if (lr(x) == 0) {
//        ls[f] = rs[x];
//        if (ls[f] != 0) {
//            fa[ls[f]] = f;
//        }
//        rs[x] = f;
//    } else {
//        rs[f] = ls[x];
//        if (rs[f] != 0) {
//            fa[rs[f]] = f;
//        }
//        ls[x] = f;
//    }
//    if (!isroot(f)) {
//        if (lr(f) == 0) {
//            ls[g] = x;
//        } else {
//            rs[g] = x;
//        }
//    }
//    fa[f] = x;
//    fa[x] = g;
//    up(f);
//    up(x);
//}
//
//void splay(int x) {
//    int siz = 0;
//    sta[++siz] = x;
//    for (int y = x; !isroot(y); y = fa[y]) {
//        sta[++siz] = fa[y];
//    }
//    while (siz != 0) {
//        down(sta[siz--]);
//    }
//    while (!isroot(x)) {
//        int f = fa[x];
//        if (!isroot(f)) {
//            if (lr(x) == lr(f)) {
//                rotate(f);
//            } else {
//                rotate(x);
//            }
//        }
//        rotate(x);
//    }
//}
//
//void access(int x) {
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        up(x);
//    }
//}
//
//void makeroot(int x) {
//    access(x);
//    splay(x);
//    reverse(x);
//}
//
//int findroot(int x) {
//    access(x);
//    splay(x);
//    down(x);
//    while (ls[x] != 0) {
//        x = ls[x];
//        down(x);
//    }
//    splay(x);
//    return x;
//}
//
//void split(int x, int y) {
//    makeroot(x);
//    access(y);
//    splay(y);
//}
//
//void link(int x, int y) {
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//    }
//}
//
//void cut(int x, int y) {
//    makeroot(x);
//    if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
//        fa[y] = rs[x] = 0;
//        up(x);
//    }
//}
//
//void backup(int edge, int type) {
//    rollbackEdge[++opsize] = edge;
//    rollbackType[opsize] = type;
//}
//
//void addEdge(int e) {
//    int x = ex[e];
//    int y = ey[e];
//    makeroot(x);
//    if (findroot(y) != x) {
//        link(x, n + e);
//        link(y, n + e);
//        backup(e, 1);
//        mstSum += ew[e];
//    } else {
//        split(x, y);
//        int pre = maxEdge[y];
//        if (ew[pre] > ew[e]) {
//            cut(ex[pre], n + pre);
//            cut(ey[pre], n + pre);
//            backup(pre, 2);
//            mstSum -= ew[pre];
//            link(x, n + e);
//            link(y, n + e);
//            backup(e, 1);
//            mstSum += ew[e];
//        }
//    }
//}
//
//void undo() {
//    int e = rollbackEdge[opsize];
//    int t = rollbackType[opsize--];
//    if (t == 1) {
//        cut(ex[e], n + e);
//        cut(ey[e], n + e);
//        mstSum -= ew[e];
//    } else {
//        link(ex[e], n + e);
//        link(ey[e], n + e);
//        mstSum += ew[e];
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int tmp = opsize;
//    for (int k = head[i]; k != 0; k = nxt[k]) {
//        addEdge(toEdge[k]);
//    }
//    if (l == r) {
//        ans[l] = mstSum;
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    while (opsize > tmp) {
//        undo();
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= m; i++) {
//        endTime[i] = q + 1;
//    }
//    for (int i = q; i >= 1; i--) {
//        int e = changeEdge[i];
//        add(i, endTime[e] - 1, m + i, 1, q, 1);
//        endTime[e] = i;
//    }
//    for (int i = 1; i <= m; i++) {
//        if (endTime[i] > 1) {
//            add(1, endTime[i] - 1, i, 1, q, 1);
//        }
//    }
//    for (int e = 1; e <= edgeCnt; e++) {
//        maxEdge[n + e] = e;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    edgeCnt = m;
//    for (int i = 1; i <= m; i++) {
//        cin >> ex[i] >> ey[i] >> ew[i];
//    }
//    for (int i = 1; i <= q; i++) {
//        int e, w;
//        cin >> e >> w;
//        changeEdge[i] = e;
//        ex[++edgeCnt] = ex[e];
//        ey[edgeCnt] = ey[e];
//        ew[edgeCnt] = w;
//    }
//    prepare();
//    dfs(1, q, 1);
//    for (int i = 1; i <= q; i++) {
//        cout << ans[i] << "\n";
//    }
//    return 0;
//}