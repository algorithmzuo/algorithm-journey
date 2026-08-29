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
// 本题推荐loj的测试，洛谷本题的新增用例让该题变成了卡常竞赛，实在没必要
// 测试链接 : https://loj.ac/p/6016
// 测试链接 : https://www.luogu.com.cn/problem/P4848
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
//const int MAXN = 100001;
//const int MAXT = 3000001;
//const int MAXV = 1000000000;
//const int INF = 1 << 30;
//int n, q;
//int a, b, c, d, v, k;
//
//int cntseg;
//int cntkdt;
//
//int rootseg;
//int lseg[MAXT];
//int rseg[MAXT];
//int rootkdt[MAXT];
//
//Node arr[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int xmin[MAXT];
//int xmax[MAXT];
//int ymin[MAXT];
//int ymax[MAXT];
//
//double ALPHA = 0.7;
//int collect[MAXN];
//int collectSiz;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//
//bool XCmp(int a, int b) {
//    return arr[a].x < arr[b].x;
//}
//
//bool YCmp(int a, int b) {
//    return arr[a].y < arr[b].y;
//}
//
//int init() {
//    cntkdt++;
//    arr[cntkdt].x = a;
//    arr[cntkdt].y = b;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    xmin[cntkdt] = xmax[cntkdt] = a;
//    ymin[cntkdt] = ymax[cntkdt] = b;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
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
//        nth_element(collect + l, collect + mid, collect + r + 1, XCmp);
//    } else {
//        nth_element(collect + l, collect + mid, collect + r + 1, YCmp);
//    }
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
//void add(int insertNode, int version, int u, int fa, int side, int dimension) {
//    if (u == 0) {
//        if (fa == 0) {
//            rootkdt[version] = insertNode;
//        } else if (side == 1) {
//            ls[fa] = insertNode;
//        } else {
//            rs[fa] = insertNode;
//        }
//    } else {
//        int insertd = dimension == 0 ? arr[insertNode].x : arr[insertNode].y;
//        int ud = dimension == 0 ? arr[u].x : arr[u].y;
//        if (insertd <= ud) {
//            add(insertNode, version, ls[u], u, 1, dimension ^ 1);
//        } else {
//            add(insertNode, version, rs[u], u, 2, dimension ^ 1);
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
//void dfs(int i) {
//    if (i != 0) {
//        collect[++collectSiz] = i;
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void rebuild(int version) {
//    if (top != 0) {
//        collectSiz = 0;
//        dfs(top);
//        int rt = build(1, collectSiz, topDimension);
//        if (topFather == 0) {
//            rootkdt[version] = rt;
//        } else if (topSide == 1) {
//            ls[topFather] = rt;
//        } else {
//            rs[topFather] = rt;
//        }
//    }
//}
//
//void insertKdt(int version) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init();
//    add(insertNode, version, rootkdt[version], 0, 0, 0);
//    rebuild(version);
//}
//
//int add(int l, int r, int i) {
//    if (i == 0) {
//        i = ++cntseg;
//    }
//    insertKdt(i);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (v <= mid) {
//            lseg[i] = add(l, mid, lseg[i]);
//        } else {
//            rseg[i] = add(mid + 1, r, rseg[i]);
//        }
//    }
//    return i;
//}
//
//bool outside(int i) {
//    return xmax[i] < a || c < xmin[i] || ymax[i] < b || d < ymin[i];
//}
//
//bool covered(int i) {
//    return a <= xmin[i] && xmax[i] <= c && b <= ymin[i] && ymax[i] <= d;
//}
//
//bool pointIn(int i) {
//    return a <= arr[i].x && arr[i].x <= c && b <= arr[i].y && arr[i].y <= d;
//}
//
//int queryCount(int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (outside(i)) {
//        return 0;
//    }
//    if (covered(i)) {
//        return siz[i];
//    }
//    int ans = pointIn(i) ? 1 : 0;
//    ans += queryCount(ls[i]);
//    ans += queryCount(rs[i]);
//    return ans;
//}
//
//int query(int jobk, int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    int cnt = queryCount(rootkdt[rseg[i]]);
//    if (cnt >= jobk) {
//        return query(jobk, mid + 1, r, rseg[i]);
//    } else {
//        return query(jobk - cnt, l, mid, lseg[i]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    for (int i = 1, op, lastAns = 0; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> a >> b >> v;
//            a ^= lastAns;
//            b ^= lastAns;
//            v ^= lastAns;
//            rootseg = add(1, MAXV, rootseg);
//        } else {
//            cin >> a >> b >> c >> d >> k;
//            a ^= lastAns;
//            b ^= lastAns;
//            c ^= lastAns;
//            d ^= lastAns;
//            k ^= lastAns;
//            if (queryCount(rootkdt[rootseg]) >= k) {
//                lastAns = query(k, 1, MAXV, rootseg);
//            } else {
//                lastAns = 0;
//            }
//            if (lastAns == 0) {
//                cout << "NAIVE!ORZzyz." << "\n";
//            } else {
//                cout << lastAns << "\n";
//            }
//        }
//    }
//    return 0;
//}