package class202;

// 航线规划，C++版
// 一共有n个点，m条边，任何时刻图连通，没有重边
// 一共有q条操作，操作有两种类型，具体格式如下
// 操作 0 x y : 删除点x和点y之间的直接边
// 操作 1 x y : 查询点x和点y之间有多少条必经边
// 1 <= n <= 3 * 10^4
// 1 <= m <= 10^5
// 1 <= q <= 4 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P2542
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 30001;
//const int MAXM = 100001;
//const int MAXQ = 40001;
//int n, m, q;
//
//int ex[MAXM];
//int ey[MAXM];
//
//int qop[MAXQ];
//int qx[MAXQ];
//int qy[MAXQ];
//
//int queryAns[MAXQ];
//int queryCnt;
//
//bool deleted[MAXM];
//map<pair<int, int>, int> edgeMap;
//
//int father[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int ebccSiz[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    ebccSiz[x] = ebccSiz[ls[x]] + ebccSiz[rs[x]] + 1;
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
//    x = find(x);
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        up(x);
//        fa[x] = find(fa[x]);
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
//void condense(int x, int root) {
//    if (x != 0) {
//        father[x] = root;
//        condense(ls[x], root);
//        condense(rs[x], root);
//    }
//}
//
//void link(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (x == y) {
//        return;
//    }
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//    } else {
//        condense(rs[x], x);
//        rs[x] = 0;
//        up(x);
//    }
//}
//
//pair<int, int> key(int x, int y) {
//    int a = min(x, y);
//    int b = max(x, y);
//    return {a, b};
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        ebccSiz[i] = 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        edgeMap[key(ex[i], ey[i])] = i;
//    }
//    for (int i = 1; i <= q; i++) {
//        if (qop[i] == 0) {
//            deleted[edgeMap[key(qx[i], qy[i])]] = true;
//        } else {
//            queryCnt++;
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        if (!deleted[i]) {
//            link(ex[i], ey[i]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> ex[i] >> ey[i];
//    }
//    int op;
//    cin >> op;
//    while (op != -1) {
//        qop[++q] = op;
//        cin >> qx[q] >> qy[q];
//        cin >> op;
//    }
//    prepare();
//    for (int i = q, j = queryCnt; i >= 1; i--) {
//        int x = find(qx[i]);
//        int y = find(qy[i]);
//        if (qop[i] == 0) {
//            link(x, y);
//        } else {
//            if (x == y) {
//                queryAns[j--] = 0;
//            } else {
//                split(x, y);
//                queryAns[j--] = ebccSiz[y] - 1;
//            }
//        }
//    }
//    for (int i = 1; i <= queryCnt; i++) {
//        cout << queryAns[i] << "\n";
//    }
//    return 0;
//}