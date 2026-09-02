package class205;

// 天使玩偶，C++版
// 本题就是讲解170，题目6，讲了CDQ分治的解法，这里用kdt的解法
// 规定(x1, y1)和(x2, y2)之间的距离 = | x1 - x2 | + | y1 - y2 |
// 一开始先给定n个点的位置，接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 1 x y : 在(x, y)位置添加一个点
// 操作 2 x y : 打印已经添加的所有点中，距离(x, y)最近的点有多远
// 1 <= n、m <= 3 * 10^5
// 0 <= x、y <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4169
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//const int INF = 1 << 30;
//int n, m;
//
//int x[MAXN];
//int y[MAXN];
//
//int cntkdt;
//int root;
//int ls[MAXN];
//int rs[MAXN];
//int siz[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//double ALPHA = 0.7;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//int arr[MAXN];
//int treeSiz;
//
//int init(int qx, int qy) {
//    cntkdt++;
//    x[cntkdt] = qx;
//    y[cntkdt] = qy;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    xmin[cntkdt] = xmax[cntkdt] = qx;
//    ymin[cntkdt] = ymax[cntkdt] = qy;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    xmin[i] = min(x[i], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(x[i], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(y[i], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(y[i], max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//int compareNode(int i, int j, int dimension) {
//    int a = dimension == 0 ? x[i] : y[i];
//    int b = dimension == 0 ? x[j] : y[j];
//    return a != b ? (a - b) : (i - j);
//}
//
//struct Cmp {
//    int dimension;
//
//    bool operator()(int a, int b) const {
//        return compareNode(a, b, dimension) < 0;
//    }
//};
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    nth_element(arr + l, arr + mid, arr + r + 1, Cmp{dimension});
//    int rt = arr[mid];
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//bool balance(int i) {
//    return ALPHA * siz[i] >= max(siz[ls[i]], siz[rs[i]]);
//}
//
//void dfs(int i) {
//    if (i != 0) {
//        arr[++treeSiz] = i;
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void rebuild() {
//    if (top != 0) {
//        treeSiz = 0;
//        dfs(top);
//        int newRoot = build(1, treeSiz, topDimension);
//        if (topFather == 0) {
//            root = newRoot;
//        } else if (topSide == 1) {
//            ls[topFather] = newRoot;
//        } else {
//            rs[topFather] = newRoot;
//        }
//    }
//}
//
//int add(int insertNode, int u, int fa, int side, int dimension) {
//    if (u == 0) {
//        return insertNode;
//    }
//    if (compareNode(insertNode, u, dimension) < 0) {
//        ls[u] = add(insertNode, ls[u], u, 1, dimension ^ 1);
//    } else {
//        rs[u] = add(insertNode, rs[u], u, 2, dimension ^ 1);
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//        topFather = fa;
//        topSide = side;
//        topDimension = dimension;
//    }
//    return u;
//}
//
//void add(int qx, int qy) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(qx, qy);
//    root = add(insertNode, root, 0, 0, 0);
//    rebuild();
//}
//
//int guess(int qx, int qy, int i) {
//    if (i == 0) {
//        return INF;
//    }
//    int ans = 0;
//    if (qx < xmin[i]) {
//        ans += xmin[i] - qx;
//    } else if (qx > xmax[i]) {
//        ans += qx - xmax[i];
//    }
//    if (qy < ymin[i]) {
//        ans += ymin[i] - qy;
//    } else if (qy > ymax[i]) {
//        ans += qy - ymax[i];
//    }
//    return ans;
//}
//
//int queryAns;
//
//void updateAns(int qx, int qy, int i) {
//    if (i == 0) {
//        return;
//    }
//    queryAns = min(queryAns, abs(qx - x[i]) + abs(qy - y[i]));
//    int gl = guess(qx, qy, ls[i]);
//    int gr = guess(qx, qy, rs[i]);
//    if (gl < gr) {
//        if (gl < queryAns) {
//            updateAns(qx, qy, ls[i]);
//        }
//        if (gr < queryAns) {
//            updateAns(qx, qy, rs[i]);
//        }
//    } else {
//        if (gr < queryAns) {
//            updateAns(qx, qy, rs[i]);
//        }
//        if (gl < queryAns) {
//            updateAns(qx, qy, ls[i]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    for (int i = 1, qx, qy; i <= n; i++) {
//        cin >> qx >> qy;
//        add(qx, qy);
//    }
//    for (int i = 1, op, qx, qy; i <= m; i++) {
//        cin >> op >> qx >> qy;
//        if (op == 1) {
//            add(qx, qy);
//        } else {
//            queryAns = INF;
//            updateAns(qx, qy, root);
//            cout << queryAns << "\n";
//        }
//    }
//    return 0;
//}