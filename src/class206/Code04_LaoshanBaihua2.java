package class206;

// 崂山白花蛇草水，C++版
// 网格空间n * n，一共有q条操作，类型如下
// 操作 1 a b v     : 坐标(a, b)，增加一个点，点权为v
// 操作 2 a b c d k : 查询(a, b)为左下角，(c, d)为右上角的矩形中，第k大的点权
// 如果点不够k个，打印 NAIVE!ORZzyz.
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 5 * 10^5
// 1 <= q <= 10^5
// 1 <= v <= 10^9
// 本题推荐loj的测试，洛谷的测试让该题变成了卡常竞赛，实在没必要
// 测试链接 : https://loj.ac/p/6016
// 测试链接 : https://www.luogu.com.cn/problem/P4848
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = 3000001;
//const int MAXV = 1000000000;
//const int INF = 1 << 30;
//int n, q;
//
//int cntseg;
//int cntkdt;
//
//int rootseg;
//int lseg[MAXT];
//int rseg[MAXT];
//int rootkdt[MAXT];
//
//int x[MAXT];
//int y[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int xmin[MAXT];
//int xmax[MAXT];
//int ymin[MAXT];
//int ymax[MAXT];
//
//double ALPHA = 0.7;
//int top;
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
//    int v1 = dimension == 0 ? x[i] : y[i];
//    int v2 = dimension == 0 ? x[j] : y[j];
//    return v1 != v2 ? (v1 - v2) : (i - j);
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
//int rebuild(int i, int dimension) {
//    if (i == top) {
//        treeSiz = 0;
//        dfs(i);
//        return build(1, treeSiz, dimension);
//    }
//    if (compareNode(top, i, dimension) < 0) {
//        ls[i] = rebuild(ls[i], dimension ^ 1);
//    } else {
//        rs[i] = rebuild(rs[i], dimension ^ 1);
//    }
//    maintain(i);
//    return i;
//}
//
//void rebuild(int version) {
//    if (top != 0) {
//        rootkdt[version] = rebuild(rootkdt[version], 0);
//    }
//}
//
//int insert(int insertNode, int u, int dimension) {
//    if (u == 0) {
//        return insertNode;
//    }
//    if (compareNode(insertNode, u, dimension) < 0) {
//        ls[u] = insert(insertNode, ls[u], dimension ^ 1);
//    } else {
//        rs[u] = insert(insertNode, rs[u], dimension ^ 1);
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//    }
//    return u;
//}
//
//void insertKdt(int version, int qx, int qy) {
//    top = 0;
//    int insertNode = init(qx, qy);
//    rootkdt[version] = insert(insertNode, rootkdt[version], 0);
//    rebuild(version);
//}
//
//int add(int qx, int qy, int qv, int l, int r, int i) {
//    if (i == 0) {
//        i = ++cntseg;
//    }
//    insertKdt(i, qx, qy);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (qv <= mid) {
//            lseg[i] = add(qx, qy, qv, l, mid, lseg[i]);
//        } else {
//            rseg[i] = add(qx, qy, qv, mid + 1, r, rseg[i]);
//        }
//    }
//    return i;
//}
//
//int queryCount(int a, int b, int c, int d, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (xmax[i] < a || c < xmin[i] || ymax[i] < b || d < ymin[i]) {
//        return 0;
//    }
//    if (a <= xmin[i] && xmax[i] <= c && b <= ymin[i] && ymax[i] <= d) {
//        return siz[i];
//    }
//    int ans = 0;
//    if (a <= x[i] && x[i] <= c && b <= y[i] && y[i] <= d) {
//        ans = 1;
//    }
//    ans += queryCount(a, b, c, d, ls[i]);
//    ans += queryCount(a, b, c, d, rs[i]);
//    return ans;
//}
//
//int query(int a, int b, int c, int d, int k, int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    int cnt = queryCount(a, b, c, d, rootkdt[rseg[i]]);
//    if (cnt >= k) {
//        return query(a, b, c, d, k, mid + 1, r, rseg[i]);
//    } else {
//        return query(a, b, c, d, k - cnt, l, mid, lseg[i]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    int op, a, b, c, d, v, k, lastAns = 0;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> a >> b >> v;
//            a ^= lastAns;
//            b ^= lastAns;
//            v ^= lastAns;
//            rootseg = add(a, b, v, 1, MAXV, rootseg);
//        } else {
//            cin >> a >> b >> c >> d >> k;
//            a ^= lastAns;
//            b ^= lastAns;
//            c ^= lastAns;
//            d ^= lastAns;
//            k ^= lastAns;
//            if (queryCount(a, b, c, d, rootkdt[rootseg]) >= k) {
//                lastAns = query(a, b, c, d, k, 1, MAXV, rootseg);
//            } else {
//                lastAns = 0;
//            }
//            if (lastAns == 0) {
//                cout << "NAIVE!ORZzyz.\n";
//            } else {
//                cout << lastAns << "\n";
//            }
//        }
//    }
//    return 0;
//}