package class205;

// 简单题，替罪羊树的方式重构，C++版
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
//int n, cntn;
//
//int root;
//int arr[MAXN][3];
//int ls[MAXN];
//int rs[MAXN];
//int sum[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//double ALPHA = 0.7;
//int siz[MAXN];
//int collect[MAXN];
//int collectSiz;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//
//int init(int x, int y, int v) {
//    cntn++;
//    arr[cntn][0] = x;
//    arr[cntn][1] = y;
//    arr[cntn][2] = v;
//    ls[cntn] = rs[cntn] = 0;
//    siz[cntn] = 1;
//    sum[cntn] = v;
//    xmin[cntn] = xmax[cntn] = x;
//    ymin[cntn] = ymax[cntn] = y;
//    return cntn;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    sum[i] = arr[i][2] + sum[ls[i]] + sum[rs[i]];
//    xmin[i] = min(arr[i][0], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i][0], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i][1], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i][1], max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//void dfs(int i) {
//    if (i != 0) {
//        collect[++collectSiz] = i;
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = arr[collect[i]][dimension];
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(collect[first++], collect[i++]);
//        } else {
//            swap(collect[i], collect[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = collect[l + rand() % (r - l + 1)];
//        int pivot = arr[idx][dimension];
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
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    randSelect(l, r, mid, dimension);
//    int rt = collect[mid];
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
//void rebuild() {
//    if (top != 0) {
//        collectSiz = 0;
//        dfs(top);
//        int rt = build(1, collectSiz, topDimension);
//        if (topFather == 0) {
//            root = rt;
//        } else if (topSide == 1) {
//            ls[topFather] = rt;
//        } else {
//            rs[topFather] = rt;
//        }
//    }
//}
//
//void add(int insertNode, int u, int fa, int side, int dimension) {
//    if (u == 0) {
//        if (fa == 0) {
//            root = insertNode;
//        } else if (side == 1) {
//            ls[fa] = insertNode;
//        } else {
//            rs[fa] = insertNode;
//        }
//    } else {
//        if (arr[insertNode][dimension] <= arr[u][dimension]) {
//            add(insertNode, ls[u], u, 1, dimension ^ 1);
//        } else {
//            add(insertNode, rs[u], u, 2, dimension ^ 1);
//        }
//        maintain(u);
//        if (!balance(u)) {
//            top = u;
//            topFather = fa;
//            topSide = side;
//            topDimension = dimension;
//        }
//    }
//}
//
//void add(int x, int y, int v) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(x, y, v);
//    add(insertNode, root, 0, 0, 0);
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
//    if (x1 <= arr[i][0] && arr[i][0] <= x2 && y1 <= arr[i][1] && arr[i][1] <= y2) {
//        ans += arr[i][2];
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
//    srand((unsigned)time(nullptr));
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