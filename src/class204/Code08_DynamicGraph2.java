package class204;

// LCT与动态图，C++版
// 初始有n个孤立节点，接下来有q条操作，操作类型如下
// 操作 1 x y : 在节点x和节点y之间增加一条无向边
// 操作 2 x y : 打印节点x和节点y之间的割边数量，不连通打印-1
// 操作 3 x y : 打印节点x和节点y之间的割点数量，不连通打印-1
// 割点数量包括节点x和节点y本身
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 10^5
// 1 <= q <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5489
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXV = 400001;
//int n, q;
//
//int father[MAXN];
//
//int fa[2][MAXV];
//int ls[2][MAXV];
//int rs[2][MAXV];
//bool rev[2][MAXV];
//int sta[MAXV];
//
//int val[2][MAXV];
//int sum[2][MAXV];
//
//bool zeroTag[MAXV];
//int road[MAXV];
//int cntr;
//
//int cnte;
//int cntv;
//
//int find(int x) {
//    if (father[x] != x) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int c, int x) {
//    sum[c][x] = sum[c][ls[c][x]] + sum[c][rs[c][x]] + val[c][x];
//}
//
//bool isroot(int c, int x) {
//    return ls[c][fa[c][x]] != x && rs[c][fa[c][x]] != x;
//}
//
//int lr(int c, int x) {
//    return ls[c][fa[c][x]] == x ? 0 : 1;
//}
//
//void reverse(int c, int x) {
//    if (x != 0) {
//        swap(ls[c][x], rs[c][x]);
//        rev[c][x] = !rev[c][x];
//    }
//}
//
//void setZero(int x) {
//    if (x != 0) {
//        val[0][x] = 0;
//        sum[0][x] = 0;
//        zeroTag[x] = true;
//    }
//}
//
//void down(int c, int x) {
//    if (rev[c][x]) {
//        reverse(c, ls[c][x]);
//        reverse(c, rs[c][x]);
//        rev[c][x] = false;
//    }
//    if (c == 0 && zeroTag[x]) {
//        setZero(ls[c][x]);
//        setZero(rs[c][x]);
//        zeroTag[x] = false;
//    }
//}
//
//void rotate(int c, int x) {
//    int f = fa[c][x], g = fa[c][f];
//    if (lr(c, x) == 0) {
//        ls[c][f] = rs[c][x];
//        if (ls[c][f] != 0) {
//            fa[c][ls[c][f]] = f;
//        }
//        rs[c][x] = f;
//    } else {
//        rs[c][f] = ls[c][x];
//        if (rs[c][f] != 0) {
//            fa[c][rs[c][f]] = f;
//        }
//        ls[c][x] = f;
//    }
//    if (!isroot(c, f)) {
//        if (lr(c, f) == 0) {
//            ls[c][g] = x;
//        } else {
//            rs[c][g] = x;
//        }
//    }
//    fa[c][f] = x;
//    fa[c][x] = g;
//    up(c, f);
//    up(c, x);
//}
//
//void splay(int c, int x) {
//    int size = 0;
//    sta[++size] = x;
//    for (int y = x; !isroot(c, y); y = fa[c][y]) {
//        sta[++size] = fa[c][y];
//    }
//    while (size != 0) {
//        down(c, sta[size--]);
//    }
//    while (!isroot(c, x)) {
//        int f = fa[c][x];
//        if (!isroot(c, f)) {
//            if (lr(c, x) == lr(c, f)) {
//                rotate(c, f);
//            } else {
//                rotate(c, x);
//            }
//        }
//        rotate(c, x);
//    }
//    up(c, x);
//}
//
//void access(int c, int x) {
//    for (int y = 0; x != 0; y = x, x = fa[c][x]) {
//        splay(c, x);
//        rs[c][x] = y;
//        up(c, x);
//    }
//}
//
//void makeroot(int c, int x) {
//    access(c, x);
//    splay(c, x);
//    reverse(c, x);
//}
//
//void split(int c, int x, int y) {
//    makeroot(c, x);
//    access(c, y);
//    splay(c, y);
//}
//
//void link(int c, int x, int y) {
//    makeroot(c, x);
//    fa[c][x] = y;
//}
//
//void cut(int c, int x, int y) {
//    split(c, x, y);
//    fa[c][x] = 0;
//    ls[c][y] = 0;
//    up(c, y);
//}
//
//void dfsRoad(int x) {
//    if (x != 0) {
//        down(1, x);
//        dfsRoad(ls[1][x]);
//        road[++cntr] = x;
//        dfsRoad(rs[1][x]);
//    }
//}
//
//void addEdge(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (fx != fy) {
//        father[fy] = fx;
//        int edge = ++cnte;
//        val[0][edge] = 1;
//        sum[0][edge] = 1;
//        link(0, x, edge);
//        link(0, edge, y);
//        link(1, x, y);
//    } else {
//        split(0, x, y);
//        setZero(y);
//        split(1, x, y);
//        if (sum[1][y] > 2) {
//            cntr = 0;
//            dfsRoad(y);
//            for (int i = 2; i <= cntr; i++) {
//                cut(1, road[i - 1], road[i]);
//            }
//            int square = ++cntv;
//            for (int i = 1; i <= cntr; i++) {
//                link(1, road[i], square);
//            }
//        }
//    }
//}
//
//int queryCute(int x, int y) {
//    if (find(x) != find(y)) {
//        return -1;
//    }
//    split(0, x, y);
//    return sum[0][y];
//}
//
//int queryCutv(int x, int y) {
//    if (find(x) != find(y)) {
//        return -1;
//    }
//    split(1, x, y);
//    return sum[1][y];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    cnte = n;
//    cntv = n;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        val[1][i] = 1;
//        sum[1][i] = 1;
//    }
//    for (int i = 1, lastAns = 0, curAns, op, x, y; i <= q; i++) {
//        cin >> op;
//        cin >> x;
//        cin >> y;
//        x ^= lastAns;
//        y ^= lastAns;
//        if (op == 1) {
//            addEdge(x, y);
//        } else {
//            if (op == 2) {
//                curAns = queryCute(x, y);
//            } else {
//                curAns = queryCutv(x, y);
//            }
//            cout << curAns << "\n";
//            if (curAns != -1) {
//                lastAns = curAns;
//            }
//        }
//    }
//    return 0;
//}