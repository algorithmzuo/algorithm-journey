package class203;

// 水管局长，C++版
// 一共n个点、m条无向边，给定每条边的两个端点和边权
// 接下来有q条操作，每条操作是如下两种类型中的一种
// 操作 1 x y : 打印点x到点y的所有路径中，最大边权的最小值
// 操作 2 x y : 删除点x和点y之间的直接边，该边一定存在
// 题目保证任何时刻图都连通，并且无重边无自环
// 1 <= n <= 10^3
// 1 <= m、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4172
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int x, y, w;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    return a.w < b.w;
//}
//
//const int MAXN = 200001;
//int n, m, q;
//Edge arr[MAXN];
//
//int qop[MAXN];
//int qx[MAXN];
//int qy[MAXN];
//
//int father[MAXN];
//
//bool deleted[MAXN];
//map<pair<int, int>, int> edgeMap;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int maxEdge[MAXN];
//int ans[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//bool Union(int x, int y) {
//    x = find(x);
//    y = find(y);
//    if (x == y) {
//        return false;
//    }
//    father[x] = y;
//    return true;
//}
//
//void up(int x) {
//    maxEdge[x] = x <= n ? 0 : x - n;
//    if (arr[maxEdge[ls[x]]].w > arr[maxEdge[x]].w) {
//        maxEdge[x] = maxEdge[ls[x]];
//    }
//    if (arr[maxEdge[rs[x]]].w > arr[maxEdge[x]].w) {
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
//pair<int, int> key(int x, int y) {
//    int a = min(x, y);
//    int b = max(x, y);
//    return {a, b};
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    sort(arr + 1, arr + m + 1, EdgeCmp);
//    for (int i = 1; i <= m; i++) {
//        edgeMap[key(arr[i].x, arr[i].y)] = i;
//    }
//    for (int i = 1; i <= q; i++) {
//        if (qop[i] == 2) {
//            deleted[edgeMap[key(qx[i], qy[i])]] = true;
//        }
//    }
//    int edgeCnt = 0;
//    for (int i = 1; i <= m && edgeCnt != n - 1; i++) {
//        if (!deleted[i] && Union(arr[i].x, arr[i].y)) {
//            link(arr[i].x, n + i);
//            link(arr[i].y, n + i);
//            edgeCnt++;
//        }
//    }
//}
//
//void restore(int cur) {
//    int curx = arr[cur].x, cury = arr[cur].y;
//    split(curx, cury);
//    int pre = maxEdge[cury], prex = arr[pre].x, prey = arr[pre].y;
//    if (arr[cur].w < arr[pre].w) {
//        cut(prex, n + pre);
//        cut(prey, n + pre);
//        link(curx, n + cur);
//        link(cury, n + cur);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].w;
//    }
//    for (int i = 1; i <= q; i++) {
//        cin >> qop[i] >> qx[i] >> qy[i];
//    }
//    prepare();
//    for (int i = q; i >= 1; i--) {
//        if (qop[i] == 1) {
//            split(qx[i], qy[i]);
//            ans[i] = arr[maxEdge[qy[i]]].w;
//        } else {
//            restore(edgeMap[key(qx[i], qy[i])]);
//        }
//    }
//    for (int i = 1; i <= q; i++) {
//        if (qop[i] == 1) {
//            cout << ans[i] << "\n";
//        }
//    }
//    return 0;
//}