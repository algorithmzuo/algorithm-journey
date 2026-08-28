package class205;

// 简单题，二进制分组的方式，C++版
// 有一个n * n的平面区域，初始时没有点，有若干条操作，类型如下
// 操作 1 a b c   : 平面里增加一个点，坐标(a, b)，点权为c
// 操作 2 a b c d : 查询(a, b)为左下角、(c, d)为右上角的区域中，所有点的点权和
// 操作 3         : 终止，以后没有操作了
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4148
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int x;
//    int y;
//    int v;
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
//const int MAXN = 200001;
//const int MAXP = 19;
//const int INF = 1 << 30;
//int n, cntn;
//
//int root[MAXP];
//Node arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sum[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//void maintain(int i) {
//    sum[i] = arr[i].v + sum[ls[i]] + sum[rs[i]];
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
//void add(int x, int y, int v) {
//    cntn++;
//    arr[cntn].x = x;
//    arr[cntn].y = y;
//    arr[cntn].v = v;
//    int p = 0;
//    while (root[p] != 0) {
//        root[p++] = 0;
//    }
//    root[p] = build(cntn - (1 << p) + 1, cntn, 0);
//}
//
//int query(int x1, int y1, int x2, int y2, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (xmax[i] < x1 || x2 < xmin[i] || ymax[i] < y1 || y2 < ymin[i]) {
//        return 0;
//    }
//    if (x1 <= xmin[i] && xmax[i] <= x2 && y1 <= ymin[i] && ymax[i] <= y2) {
//        return sum[i];
//    }
//    int ans = 0;
//    if (x1 <= arr[i].x && arr[i].x <= x2 && y1 <= arr[i].y && arr[i].y <= y2) {
//        ans += arr[i].v;
//    }
//    ans += query(x1, y1, x2, y2, ls[i]);
//    ans += query(x1, y1, x2, y2, rs[i]);
//    return ans;
//}
//
//int query(int x1, int y1, int x2, int y2) {
//    int ans = 0;
//    for (int p = 0; p < MAXP; p++) {
//        ans += query(x1, y1, x2, y2, root[p]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    int op, a, b, c, d, lastAns;
//    cin >> op;
//    lastAns = 0;
//    while (op != 3) {
//        cin >> a >> b >> c;
//        a ^= lastAns;
//        b ^= lastAns;
//        c ^= lastAns;
//        if (op == 1) {
//            add(a, b, c);
//        } else {
//            cin >> d;
//            d ^= lastAns;
//            lastAns = query(a, b, c, d);
//            cout << lastAns << "\n";
//        }
//        cin >> op;
//    }
//    return 0;
//}