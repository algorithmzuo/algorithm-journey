package class205;

// 简单题，替罪羊树的方式，C++版
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
//const int MAXN = 200001;
//const int INF = 1 << 30;
//int n;
//
//int x[MAXN];
//int y[MAXN];
//int v[MAXN];
//
//int cntkdt;
//int root;
//int ls[MAXN];
//int rs[MAXN];
//
//int siz[MAXN];
//int sum[MAXN];
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
//
//int arr[MAXN];
//int treeSiz;
//
//int init(int qx, int qy, int qv) {
//    cntkdt++;
//    x[cntkdt] = qx;
//    y[cntkdt] = qy;
//    v[cntkdt] = qv;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    sum[cntkdt] = qv;
//    xmin[cntkdt] = xmax[cntkdt] = qx;
//    ymin[cntkdt] = ymax[cntkdt] = qy;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    sum[i] = v[i] + sum[ls[i]] + sum[rs[i]];
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
//void add(int qx, int qy, int qv) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(qx, qy, qv);
//    root = add(insertNode, root, 0, 0, 0);
//    rebuild();
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
//    if (x1 <= x[i] && x[i] <= x2 && y1 <= y[i] && y[i] <= y2) {
//        ans += v[i];
//    }
//    ans += query(x1, y1, x2, y2, ls[i]);
//    ans += query(x1, y1, x2, y2, rs[i]);
//    return ans;
//}
//
//int query(int x1, int y1, int x2, int y2) {
//    return query(x1, y1, x2, y2, root);
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