package class202;

// 星球联盟，C++版
// 一共有n个点、m条无向边，接下来依次加入p条无向边
// 如果两个点属于同一个联盟，必须存在一条环形线路经过这两个点
// 等价地说，两个点之间存在两条没有公共边的路径，也就是在同一个边双连通分量中
// 每次加入一条新边(x,y)，加入之后判断点x和点y是否属于同一个联盟
// 如果不属于同一个联盟，打印"No"
// 如果属于同一个联盟，打印这个联盟里的节点数
// 1 <= n、m、p <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P10657
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//int n, m, p;
//
//int father[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int nodeCnt[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
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
//        int tmp = ls[x];
//        ls[x] = rs[x];
//        rs[x] = tmp;
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
//void condense(int x, int root) {
//    if (x != 0) {
//        father[x] = root;
//        nodeCnt[root] += nodeCnt[x];
//        condense(ls[x], root);
//        condense(rs[x], root);
//    }
//}
//
//int link(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (x == y) {
//        return nodeCnt[x];
//    }
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//        return -1;
//    } else {
//        condense(rs[x], x);
//        rs[x] = 0;
//        return nodeCnt[x];
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> p;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        nodeCnt[i] = 1;
//    }
//    for (int i = 1, x, y; i <= m; i++) {
//        cin >> x >> y;
//        link(x, y);
//    }
//    for (int i = 1, x, y; i <= p; i++) {
//        cin >> x >> y;
//        int ans = link(x, y);
//        if (ans == -1) {
//            cout << "No\n";
//        } else {
//            cout << ans << "\n";
//        }
//    }
//    return 0;
//}