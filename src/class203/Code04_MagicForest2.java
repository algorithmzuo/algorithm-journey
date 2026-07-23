package class203;

// 魔法森林，C++版
// 无向图有n个点、m条边，每条边有a、b两种权值，可能存在重边和自环
// 定义路径的代价为，路径上最大的a + 路径上最大的b
// 选择1号点到n号点的路径，打印路径代价的最小值，无法到达打印-1
// 2 <= n <= 5 * 10^4
// 0 <= m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2387
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int x, y, a, b;
//};
//
//bool EdgeCmp(Edge e1, Edge e2) {
//    return e1.a < e2.a;
//}
//
//const int MAXN = 200001;
//const int INF = 1000000001;
//int n, m;
//Edge arr[MAXN];
//
//int father[MAXN];
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int maxbEdge[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    maxbEdge[x] = x <= n ? 0 : x - n;
//    if (arr[maxbEdge[ls[x]]].b > arr[maxbEdge[x]].b) {
//        maxbEdge[x] = maxbEdge[ls[x]];
//    }
//    if (arr[maxbEdge[rs[x]]].b > arr[maxbEdge[x]].b) {
//        maxbEdge[x] = maxbEdge[rs[x]];
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
//int compute() {
//    sort(arr + 1, arr + m + 1, EdgeCmp);
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    int ans = INF;
//    for (int i = 1; i <= m; i++) {
//        int x = arr[i].x;
//        int y = arr[i].y;
//        if (x != y) {
//            up(n + i);
//            if (find(x) != find(y)) {
//                father[find(x)] = find(y);
//                link(x, n + i);
//                link(y, n + i);
//            } else {
//                split(x, y);
//                int pre = maxbEdge[y];
//                if (arr[i].b < arr[pre].b) {
//                    cut(arr[pre].x, n + pre);
//                    cut(arr[pre].y, n + pre);
//                    link(x, n + i);
//                    link(y, n + i);
//                }
//            }
//        }
//        if (find(1) == find(n)) {
//            split(1, n);
//            ans = min(ans, arr[i].a + arr[maxbEdge[n]].b);
//        }
//    }
//    return ans == INF ? -1 : ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].a >> arr[i].b;
//    }
//    int ans = compute();
//    cout << ans << "\n";
//    return 0;
//}