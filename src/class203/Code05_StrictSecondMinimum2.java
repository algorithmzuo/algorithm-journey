package class203;

// 严格次小生成树，C++版
// 一共n个点、m条无向边，每条边有边权，图中可能存在自环
// 严格次小生成树的边权和，必须严格大于最小生成树的边权和
// 题目保证严格次小生成树一定存在，打印其边权和
// 1 <= n <= 10^5
// 1 <= m <= 3 * 10^5
// 0 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4180
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Edge {
//    int x, y, w;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    return a.w < b.w;
//}
//
//const int MAXN = 400001;
//const int MAXM = 300001;
//const int INF = 1000000001;
//int n, m;
//
//Edge arr[MAXM];
//
//int father[MAXN];
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool rev[MAXN];
//int sta[MAXN];
//
//int max1[MAXN];
//int max2[MAXN];
//
//int find(int x) {
//    if (x != father[x]) {
//        father[x] = find(father[x]);
//    }
//    return father[x];
//}
//
//void up(int x) {
//    int l = ls[x], lmax1 = max1[l], lmax2 = max2[l];
//    int r = rs[x], rmax1 = max1[r], rmax2 = max2[r];
//    if (lmax1 > rmax1) {
//        max1[x] = lmax1;
//        max2[x] = max(rmax1, lmax2);
//    } else if (rmax1 > lmax1) {
//        max1[x] = rmax1;
//        max2[x] = max(lmax1, rmax2);
//    } else {
//        max1[x] = lmax1;
//        max2[x] = max(lmax2, rmax2);
//    }
//    int v = x <= n ? -INF : arr[x - n].w;
//    if (v > max1[x]) {
//        max2[x] = max1[x];
//        max1[x] = v;
//    } else if (v < max1[x] && v > max2[x]) {
//        max2[x] = v;
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
//ll compute() {
//    sort(arr + 1, arr + m + 1, EdgeCmp);
//    ll sum = 0;
//    int minAdd = INF;
//    for (int i = 1; i <= m; i++) {
//        int x = arr[i].x;
//        int y = arr[i].y;
//        int w = arr[i].w;
//        if (x != y) {
//            int fx = find(x);
//            int fy = find(y);
//            if (fx != fy) {
//                father[fx] = fy;
//                int e = n + i;
//                max1[e] = w;
//                max2[e] = -INF;
//                link(x, e);
//                link(y, e);
//                sum += w;
//            } else {
//                split(x, y);
//                int v1 = max1[y];
//                int v2 = max2[y];
//                if (w > v1) {
//                    minAdd = min(minAdd, w - v1);
//                } else if (w == v1 && v2 != -INF) {
//                    minAdd = min(minAdd, w - v2);
//                }
//            }
//        }
//    }
//    return sum + minAdd;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].w;
//    }
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    for (int i = 0; i <= n; i++) {
//        max1[i] = max2[i] = -INF;
//    }
//    cout << compute() << "\n";
//    return 0;
//}