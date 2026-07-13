package class202;

// 染色，C++版
// 本题就是讲解161，题目5，讲了树链剖分的解法，这里用lct的解法
// 一共有n个节点，给定n-1条边，节点连成一棵树，每个节点给定初始颜色值
// 连续相同颜色被认为是一段，变化了就认为是另一段
// 比如，112221，有三个颜色段，分别为 11、222、1
// 一共有m条操作，每种操作是如下2种类型中的一种
// 操作 C x y z : x到y的路径上，每个节点的颜色都改为z
// 操作 Q x y   : x到y的路径上，打印有几个颜色段
// 1 <= n、m <= 10^5
// 1 <= 任何时候的颜色值 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2486
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sta[MAXN];
//bool rev[MAXN];
//
//int arr[MAXN];
//int colorAns[MAXN];
//int lcolor[MAXN];
//int rcolor[MAXN];
//
//int colorTag[MAXN];
//
//void up(int x) {
//    lcolor[x] = ls[x] == 0 ? arr[x] : lcolor[ls[x]];
//    rcolor[x] = rs[x] == 0 ? arr[x] : rcolor[rs[x]];
//    colorAns[x] = colorAns[ls[x]] + colorAns[rs[x]] + 1;
//    if (ls[x] != 0 && rcolor[ls[x]] == arr[x]) {
//        colorAns[x]--;
//    }
//    if (rs[x] != 0 && arr[x] == lcolor[rs[x]]) {
//        colorAns[x]--;
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
//        swap(lcolor[x], rcolor[x]);
//        rev[x] = !rev[x];
//    }
//}
//
//void effect(int x, int c) {
//    if (x != 0) {
//        arr[x] = c;
//        lcolor[x] = c;
//        rcolor[x] = c;
//        colorAns[x] = 1;
//        colorTag[x] = c;
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
//    if (colorTag[x] != 0) {
//        effect(ls[x], colorTag[x]);
//        effect(rs[x], colorTag[x]);
//        colorTag[x] = 0;
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//        lcolor[i] = arr[i];
//        rcolor[i] = arr[i];
//        colorAns[i] = 1;
//    }
//    for (int i = 1, x, y; i < n; i++) {
//        cin >> x >> y;
//        link(x, y);
//    }
//    for (int i = 1, x, y, c; i <= m; i++) {
//        string op;
//        cin >> op;
//        if (op == "C") {
//            cin >> x >> y >> c;
//            split(x, y);
//            effect(y, c);
//        } else {
//            cin >> x >> y;
//            split(x, y);
//            cout << colorAns[y] << "\n";
//        }
//    }
//    return 0;
//}