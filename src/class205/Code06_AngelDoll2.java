package class205;

// 天使玩偶，C++版
// 本题就是讲解170，题目6，讲了CDQ分治的解法，这里用kdt的解法
// 规定(x1, y1)和(x2, y2)之间的距离 = | x1 - x2 | + | y1 - y2 |
// 一开始先给定n个点的位置，接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 1 x y : 在(x, y)位置添加一个点
// 操作 2 x y : 打印已经添加的所有点中，到(x, y)位置最短距离的点是多远
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
//const int MAXN = 600001;
//const int MAXP = 20;
//const int INF = 1 << 30;
//int n, m, cntn;
//
//Node arr[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//int root[MAXP];
//
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = dimension == 0 ? arr[i].x : arr[i].y;
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(arr[first++], arr[i++]);
//        } else {
//            swap(arr[i], arr[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = l + rand() % (r - l + 1);
//        int pivot = dimension == 0 ? arr[idx].x : arr[idx].y;
//        partition(l, r, pivot, dimension);
//        if (i < first) {
//            r = first - 1;
//        } else if (i > last) {
//            l = last + 1;
//        } else {
//            break;
//        }
//    }
//}
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
//    if (l == r) {
//        ls[mid] = 0;
//        rs[mid] = 0;
//    } else {
//        randSelect(l, r, mid, dimension);
//        ls[mid] = build(l, mid - 1, dimension ^ 1);
//        rs[mid] = build(mid + 1, r, dimension ^ 1);
//    }
//    maintain(mid);
//    return mid;
//}
//
//void add(int x, int y) {
//    cntn++;
//    arr[cntn].x = x;
//    arr[cntn].y = y;
//    int p = 0;
//    while (root[p] != 0) {
//        root[p++] = 0;
//    }
//    root[p] = build(cntn - (1 << p) + 1, cntn, 0);
//}
//
//int guess(int x, int y, int i) {
//    if (i == 0) {
//        return INF;
//    }
//    int ans = 0;
//    if (x < xmin[i]) {
//        ans += xmin[i] - x;
//    } else if (x > xmax[i]) {
//        ans += x - xmax[i];
//    }
//    if (y < ymin[i]) {
//        ans += ymin[i] - y;
//    } else if (y > ymax[i]) {
//        ans += y - ymax[i];
//    }
//    return ans;
//}
//
//int queryAns;
//
//void updateAns(int x, int y, int i) {
//    if (i == 0) {
//        return;
//    }
//    queryAns = min(queryAns, abs(x - arr[i].x) + abs(y - arr[i].y));
//    int gl = guess(x, y, ls[i]);
//    int gr = guess(x, y, rs[i]);
//    if (gl < gr) {
//        if (gl < queryAns) {
//            updateAns(x, y, ls[i]);
//        }
//        if (gr < queryAns) {
//            updateAns(x, y, rs[i]);
//        }
//    } else {
//        if (gr < queryAns) {
//            updateAns(x, y, rs[i]);
//        }
//        if (gl < queryAns) {
//            updateAns(x, y, ls[i]);
//        }
//    }
//}
//
//int query(int x, int y) {
//    queryAns = INF;
//    for (int p = 0; p < MAXP; p++) {
//        if (root[p] != 0) {
//            updateAns(x, y, root[p]);
//        }
//    }
//    return queryAns;
//}
//
//void prepare() {
//    for (int p = 0, siz = 1 << p, rest = n; p < MAXP; p++, siz <<= 1) {
//        if ((n & (1 << p)) != 0) {
//            root[p] = build(rest - siz + 1, rest, 0);
//            rest -= siz;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n >> m;
//    cntn = n;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//    }
//    prepare();
//    for (int i = 1, op, x, y; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            add(x, y);
//        } else {
//            cout << query(x, y) << "\n";
//        }
//    }
//    return 0;
//}