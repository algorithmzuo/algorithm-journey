package class203;

// 变化的道路，C++版
// 一共n个点，n-1条无向道路，所有点组成一棵树，这些道路始终存在
// 然后给定m条变化的道路，每条变化的道路，只在第l天到第r天存在
// 道路给定权值代表费用，同一天内，一条道路在第一次经过时收费，之后免费
// 每天从1号点出发并游览所有点，要求游览结束后剩余钱数 > 0
// 打印每天出发时至少需要多少钱，也就是最少总费用+1
// 1 <= n <= 5 * 10^4
// 1 <= m <= 10^5
// 天数固定为32766，输出也固定为32766行
// 测试链接 : https://www.luogu.com.cn/problem/P4319
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 200001;
//const int MAXD = 32767;
//const int MAXT = 4000001;
//const int DAY = 32766;
//int n, m;
//
//int ex[MAXN];
//int ey[MAXN];
//int ew[MAXN];
//int edgeCnt;
//
//int head[MAXD << 2];
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
//ll mstSum = 1;
//ll ans[MAXD];
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
//    up(n + e);
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i < n; i++) {
//        cin >> ex[++edgeCnt];
//        cin >> ey[edgeCnt];
//        cin >> ew[edgeCnt];
//        add(1, DAY, edgeCnt, 1, DAY, 1);
//    }
//    cin >> m;
//    for (int i = 1, l, r; i <= m; i++) {
//        cin >> ex[++edgeCnt];
//        cin >> ey[edgeCnt];
//        cin >> ew[edgeCnt];
//        cin >> l;
//        cin >> r;
//        add(l, r, edgeCnt, 1, DAY, 1);
//    }
//    dfs(1, DAY, 1);
//    for (int i = 1; i <= DAY; i++) {
//        cout << ans[i] << "\n";
//    }
//    return 0;
//}