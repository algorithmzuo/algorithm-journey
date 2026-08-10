package class204;

// 查询最远距离，C++版
// 初始没有节点，接下来有q条操作，操作类型如下
// 操作 B p : 新建一个节点，并与节点p连接，如果p为-1则新建一个独立节点
// 操作 Q x : 打印节点x到所在连通块中最远节点的距离
// 1 <= q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4271
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int q, cntn;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int siz[MAXN];
//
//int father[MAXN];
//
//int diameter[MAXN];
//int lnode[MAXN];
//int rnode[MAXN];
//
//int find(int x) {
//    if (father[x] != x) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
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
//int getDist(int x, int y) {
//    split(x, y);
//    return siz[y] - 1;
//}
//
//void build(int p) {
//    int x = ++cntn;
//    siz[x] = 1;
//    father[x] = x;
//    lnode[x] = x;
//    rnode[x] = x;
//    diameter[x] = 0;
//    if (p != -1) {
//        int root = find(p);
//        int a = lnode[root];
//        int b = rnode[root];
//        int best = diameter[root];
//        int bestl = a;
//        int bestr = b;
//        link(x, p);
//        int dista = getDist(x, a);
//        if (dista > best) {
//            best = dista;
//            bestl = x;
//            bestr = a;
//        }
//        int distb = getDist(x, b);
//        if (distb > best) {
//            best = distb;
//            bestl = x;
//            bestr = b;
//        }
//        father[x] = root;
//        lnode[root] = bestl;
//        rnode[root] = bestr;
//        diameter[root] = best;
//    }
//}
//
//int query(int x) {
//    int fx = find(x);
//    return max(getDist(x, lnode[fx]), getDist(x, rnode[fx]));
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> q;
//    string op;
//    int x;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        cin >> x;
//        if (op == "B") {
//            build(x);
//        } else {
//            cout << query(x) << "\n";
//        }
//    }
//    return 0;
//}