package class204;

// LCT与动态图，C++版
// 初始有n个孤立节点，接下来有q条操作，操作类型如下
// 操作 1 x y : 点x和点y之间增加一条无向边，两点之前可能连通
// 操作 2 x y : 打印点x到点y的路径中，必经边的数量，不连通打印-1
// 操作 3 x y : 打印点x到点y的路径中，必经点的数量，不连通打印-1
// 必经点包括节点x和节点y本身
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
//const int MAXT = 400001;
//int n, q;
//
//int father[MAXN];
//
//int fa[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//bool rev[MAXT];
//int sta[MAXT];
//
//int cntev;
//
//int val[MAXT];
//int sum[MAXT];
//bool zeroTag[MAXT];
//
//int road[MAXT];
//int roadLen;
//
//int find(int x) {
//    if (father[x] != x) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    sum[x] = sum[ls[x]] + sum[rs[x]] + val[x];
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
//void setZero(int x) {
//    if (x != 0) {
//        val[x] = 0;
//        sum[x] = 0;
//        zeroTag[x] = true;
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//    if (zeroTag[x]) {
//        setZero(ls[x]);
//        setZero(rs[x]);
//        zeroTag[x] = false;
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
//    int size = 0;
//    sta[++size] = x;
//    for (int y = x; !isroot(y); y = fa[y]) {
//        sta[++size] = fa[y];
//    }
//    while (size != 0) {
//        down(sta[size--]);
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
//    up(x);
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
//void split(int x, int y) {
//    makeroot(x);
//    access(y);
//    splay(y);
//}
//
//void link(int x, int y) {
//    makeroot(x);
//    fa[x] = y;
//}
//
//void cut(int x, int y) {
//    split(x, y);
//    fa[x] = 0;
//    ls[y] = 0;
//    up(y);
//}
//
//void inOrder(int x) {
//    if (x != 0) {
//        down(x);
//        inOrder(ls[x]);
//        road[++roadLen] = x;
//        inOrder(rs[x]);
//    }
//}
//
//void addEdge(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (fx != fy) {
//        father[fy] = fx;
//        int edge = ++cntev;
//        val[edge] = 1;
//        sum[edge] = 1;
//        link(x, edge);
//        link(edge, y);
//        link(x + n, y + n);
//    } else {
//        split(x, y);
//        setZero(y);
//        x = x + n;
//        y = y + n;
//        split(x, y);
//        if (sum[y] > 2) {
//            roadLen = 0;
//            inOrder(y);
//            for (int i = 2; i <= roadLen; i++) {
//                cut(road[i - 1], road[i]);
//            }
//            int square = ++cntev;
//            for (int i = 1; i <= roadLen; i++) {
//                link(road[i], square);
//            }
//        }
//    }
//}
//
//int queryCute(int x, int y) {
//    if (find(x) != find(y)) {
//        return -1;
//    }
//    split(x, y);
//    return sum[y];
//}
//
//int queryCutv(int x, int y) {
//    if (find(x) != find(y)) {
//        return -1;
//    }
//    x = x + n;
//    y = y + n;
//    split(x, y);
//    return sum[y];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    cntev = n << 1;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        val[i + n] = 1;
//        sum[i + n] = 1;
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