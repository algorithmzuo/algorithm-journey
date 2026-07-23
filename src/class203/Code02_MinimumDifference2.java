package class203;

// 最小差值生成树，C++版
// 一共n个点、m条无向边，给定每条边的两个端点和边权
// 题目保证图连通，图中可能存在自环，选择一棵生成树
// 希望树中最大边权减去最小边权的值尽量小，打印该值
// 1 <= n <= 5 * 10^4
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4234
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
//const int MAXN = 300001;
//int n, m;
//Edge arr[MAXN];
//
//bool removed[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int minEdge[MAXN];
//
//void up(int x) {
//    minEdge[x] = x <= n ? 0 : x - n;
//    int le = minEdge[ls[x]];
//    int re = minEdge[rs[x]];
//    if (le != 0 && (minEdge[x] == 0 || le < minEdge[x])) {
//        minEdge[x] = le;
//    }
//    if (re != 0 && (minEdge[x] == 0 || re < minEdge[x])) {
//        minEdge[x] = re;
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
//    if (n <= 1) {
//        return 0;
//    }
//    sort(arr + 1, arr + m + 1, EdgeCmp);
//    int edgeCnt = 0;
//    int first = 1;
//    int ans = INT_MAX;
//    for (int i = 1; i <= m; i++) {
//        int x = arr[i].x;
//        int y = arr[i].y;
//        if (x == y) {
//            removed[i] = true;
//        } else {
//            makeroot(x);
//            if (findroot(y) != x) {
//                link(x, n + i);
//                link(y, n + i);
//                edgeCnt++;
//            } else {
//                split(x, y);
//                int pre = minEdge[y];
//                cut(arr[pre].x, n + pre);
//                cut(arr[pre].y, n + pre);
//                link(x, n + i);
//                link(y, n + i);
//                removed[pre] = true;
//            }
//        }
//        while (removed[first]) {
//            first++;
//        }
//        if (edgeCnt == n - 1) {
//            ans = min(ans, arr[i].w - arr[first].w);
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].w;
//    }
//    cout << compute() << "\n";
//    return 0;
//}