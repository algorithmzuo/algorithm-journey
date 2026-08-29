package class206;

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
//struct Node {
//    int x;
//    int y;
//};
//
//bool XCmp(Node a, Node b) {
//    return a.x < b.x;
//}
//
//bool YCmp(Node a, Node b) {
//    return a.y < b.y;
//}
//
//const int MAXN = 1000001;
//const int MAXP = 20;
//const int INF = 1 << 30;
//int n, m;
//
//int cntkdt;
//int root[MAXP];
//Node arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//void maintain(int i) {
//    xmin[i] = min(arr[i].x, min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i].x, max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i].y, min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i].y, max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (dimension == 0) {
//        nth_element(arr + l, arr + mid, arr + r + 1, XCmp);
//    } else {
//        nth_element(arr + l, arr + mid, arr + r + 1, YCmp);
//    }
//    ls[mid] = build(l, mid - 1, dimension ^ 1);
//    rs[mid] = build(mid + 1, r, dimension ^ 1);
//    maintain(mid);
//    return mid;
//}
//
//void add(int qx, int qy) {
//    cntkdt++;
//    arr[cntkdt].x = qx;
//    arr[cntkdt].y = qy;
//    int p = 0;
//    while (root[p] != 0) {
//        root[p++] = 0;
//    }
//    root[p] = build(cntkdt - (1 << p) + 1, cntkdt, 0);
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
//    queryAns = min(queryAns, abs(qx - arr[i].x) + abs(qy - arr[i].y));
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
//int query(int qx, int qy) {
//    queryAns = INF;
//    for (int p = 0; p < MAXP; p++) {
//        if (root[p] != 0) {
//            updateAns(qx, qy, root[p]);
//        }
//    }
//    return queryAns;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntkdt = n;
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
//            cout << query(qx, qy) << "\n";
//        }
//    }
//    return 0;
//}