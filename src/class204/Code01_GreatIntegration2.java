package class204;

// 大融合，C++版
// 本题就是讲解166，题目5，讲了线段树分治的解法，这里用lct的解法
// 一共有n个点，一共有q条操作，每条操作是如下两种类型中的一种
// 操作 A x y : 点x和点y之间连一条边，保证之前x和y是不联通的
// 操作 Q x y : 打印点x和点y之间这条边的负载，保证x和y之间有一条边
// 边负载定义为，这条边两侧端点各自连通区大小的乘积
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4219
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, q;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int vir[MAXN];
//int sum[MAXN];
//
//void up(int x) {
//    sum[x] = sum[ls[x]] + sum[rs[x]] + vir[x] + 1;
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
//        vir[x] += sum[rs[x]];
//        vir[x] -= sum[y];
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
//        access(y);
//        splay(y);
//        fa[x] = y;
//        vir[y] += sum[x];
//        up(y);
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
//long query(int x, int y) {
//    cut(x, y);
//    makeroot(x);
//    int sizx = sum[x];
//    makeroot(y);
//    int sizy = sum[y];
//    link(x, y);
//    return 1LL * sizx * sizy;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    for (int i = 1; i <= n; i++) {
//        sum[i] = 1;
//    }
//    for (int i = 1; i <= q; i++) {
//        char op;
//        int x, y;
//        cin >> op >> x >> y;
//        if (op == 'A') {
//            link(x, y);
//        } else {
//            cout << query(x, y) << "\n";
//        }
//    }
//    return 0;
//}