package class203;

// 温暖会指引我们前行，C++版
// 一共n个地点，地点之间的道路是无向边，每条道路有温度和长度两种权值
// 初始时没有任何道路，之后出现的所有道路，温度互不相同
// 对于点x到点y的每一条路径，将路径上的道路按温度升序排列，形成该路径的温度序列
// 温度序列字典序最大的路径叫做最佳路径，因为道路温度互不相同，所以最佳路径唯一
// 接下来有m条操作，操作类型有三种，具体格式如下
// find e x y t l : 建立编号为e的边，端点x和y、温度t、长度l，编号保证唯一
// move x y       : 打印点x到点y最佳路径的长度总和，如果不连通打印-1
// change e l     : 编号为e的道路长度修改为l，该道路保证已经建立
// 1 <= n <= 10^5    1 <= m <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6664
// 测试链接 : https://uoj.ac/problem/274
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 400001;
//const int INF = 1000000001;
//int n, m;
//
//int ex[MAXN];
//int ey[MAXN];
//int et[MAXN];
//int el[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int sum[MAXN];
//
//int minEdge[MAXN];
//
//void up(int x) {
//    int e = x <= n ? 0 : x - n;
//    sum[x] = sum[ls[x]] + sum[rs[x]] + el[e];
//    minEdge[x] = e;
//    if (et[minEdge[ls[x]]] < et[minEdge[x]]) {
//        minEdge[x] = minEdge[ls[x]];
//    }
//    if (et[minEdge[rs[x]]] < et[minEdge[x]]) {
//        minEdge[x] = minEdge[rs[x]];
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
//void addEdge(int e) {
//    int x = ex[e];
//    int y = ey[e];
//    up(n + e);
//    makeroot(x);
//    if (findroot(y) != x) {
//        link(x, n + e);
//        link(y, n + e);
//    } else {
//        split(x, y);
//        int p = minEdge[y];
//        if (et[e] > et[p]) {
//            cut(ex[p], n + p);
//            cut(ey[p], n + p);
//            link(x, n + e);
//            link(y, n + e);
//        }
//    }
//}
//
//void change(int e, int l) {
//    splay(n + e);
//    el[e] = l;
//    up(n + e);
//}
//
//int query(int x, int y) {
//    makeroot(x);
//    if (findroot(y) != x) {
//        return -1;
//    }
//    split(x, y);
//    return sum[y];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    et[0] = INF;
//    string op;
//    int e, x, y, t, l;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == "find") {
//            cin >> e >> x >> y >> t >> l;
//            e++;
//            x++;
//            y++;
//            ex[e] = x;
//            ey[e] = y;
//            et[e] = t;
//            el[e] = l;
//            addEdge(e);
//        } else if (op == "move") {
//            cin >> x >> y;
//            x++;
//            y++;
//            cout << query(x, y) << "\n";
//        } else {
//            cin >> e >> l;
//            e++;
//            change(e, l);
//        }
//    }
//    return 0;
//}