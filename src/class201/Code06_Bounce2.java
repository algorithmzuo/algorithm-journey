package class201;

// 弹飞绵羊，C++版
// 一共n个装置，编号0~n-1，从左到右摆成一条直线
// 每个装置给定弹力f，比如3号装置弹力为5，则从3号装置可以向右跳到8号装置
// 如果跳到的位置 >= n，也就是不存在这个装置，则认为被弹飞
// 一共m条操作，操作类型如下
// 操作 1 x   : 查询从x号装置出发，跳几次会弹飞
// 操作 2 x y : x号装置的弹力修改成y
// 1 <= n <= 2 * 10^5
// 1 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3203
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200002;
//int n, m;
//int force[MAXN];
//int target[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sta[MAXN];
//bool rev[MAXN];
//
//int siz[MAXN];
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int x = 1; x <= n + 1; x++) {
//        siz[x] = 1;
//    }
//    for (int x = 1; x <= n; x++) {
//        cin >> force[x];
//        target[x] = min(x + force[x], n + 1);
//        link(x, target[x]);
//    }
//    cin >> m;
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op;
//        cin >> x;
//        x++;
//        if (op == 1) {
//            split(x, n + 1);
//            cout << siz[n + 1] - 1 << "\n";
//        } else {
//            cin >> y;
//            cut(x, target[x]);
//            force[x] = y;
//            target[x] = min(x + force[x], n + 1);
//            link(x, target[x]);
//        }
//    }
//    return 0;
//}