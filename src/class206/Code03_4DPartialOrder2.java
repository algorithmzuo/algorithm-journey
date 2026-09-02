package class206;

// 四维偏序最长链，C++版
// 一共n个点，每个点有四维坐标(a, b, c, d)
// 可以任意选择点的排列顺序，每个点最多使用一次
// 点x的后面可以放置点y的条件为，y的每个坐标 >= x对应的坐标
// 希望选择的点尽量多，打印最多能选择几个点
// 1 <= n <= 5 * 10^4
// -10^9 <= 坐标值 <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3769
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct ABCD {
//    int a, b, c, d;
//};
//
//bool ABCDCmp(ABCD x, ABCD y) {
//    if (x.a != y.a) return x.a < y.a;
//    if (x.b != y.b) return x.b < y.b;
//    if (x.c != y.c) return x.c < y.c;
//    return x.d < y.d;
//}
//
//struct BI {
//    int b, i;
//};
//
//bool BICmp(BI x, BI y) {
//    if (x.b != y.b) return x.b < y.b;
//    return x.i < y.i;
//}
//
//const int MAXN = 50001;
//const int MAXT = 500001;
//const int INF = 1 << 30;
//int n;
//
//ABCD abcd[MAXN];
//
//BI bi[MAXN];
//
//int ranking[MAXN];
//
//int c[MAXT];
//int d[MAXT];
//int cntkdt;
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int cmin[MAXT];
//int cmax[MAXT];
//int dmin[MAXT];
//int dmax[MAXT];
//
//double ALPHA = 0.7;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//int arr[MAXN];
//int treeSiz;
//
//int dp[MAXT];
//int maxdp[MAXT];
//
//int init(int qc, int qd, int qv) {
//    cntkdt++;
//    c[cntkdt] = qc;
//    d[cntkdt] = qd;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    cmin[cntkdt] = cmax[cntkdt] = qc;
//    dmin[cntkdt] = dmax[cntkdt] = qd;
//    dp[cntkdt] = maxdp[cntkdt] = qv;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    cmin[i] = min(c[i], min(cmin[ls[i]], cmin[rs[i]]));
//    cmax[i] = max(c[i], max(cmax[ls[i]], cmax[rs[i]]));
//    dmin[i] = min(d[i], min(dmin[ls[i]], dmin[rs[i]]));
//    dmax[i] = max(d[i], max(dmax[ls[i]], dmax[rs[i]]));
//    maxdp[i] = max(dp[i], max(maxdp[ls[i]], maxdp[rs[i]]));
//}
//
//int compareNode(int i, int j, int dimension) {
//    int v1 = dimension == 0 ? c[i] : d[i];
//    int v2 = dimension == 0 ? c[j] : d[j];
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
//void rebuild(int version) {
//    if (top != 0) {
//        treeSiz = 0;
//        dfs(top);
//        int newRoot = build(1, treeSiz, topDimension);
//        if (topFather == 0) {
//            root[version] = newRoot;
//        } else if (topSide == 1) {
//            ls[topFather] = newRoot;
//        } else {
//            rs[topFather] = newRoot;
//        }
//    }
//}
//
//int addKdt(int insertNode, int u, int fa, int side, int dimension) {
//    if (u == 0) {
//        return insertNode;
//    }
//    if (compareNode(insertNode, u, dimension) < 0) {
//        ls[u] = addKdt(insertNode, ls[u], u, 1, dimension ^ 1);
//    } else {
//        rs[u] = addKdt(insertNode, rs[u], u, 2, dimension ^ 1);
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
//void addKdt(int version, int qx, int qy, int qv) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(qx, qy, qv);
//    root[version] = addKdt(insertNode, root[version], 0, 0, 0);
//    rebuild(version);
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int rank, int qc, int qd, int qv) {
//    for (int i = rank; i <= n; i += lowbit(i)) {
//        addKdt(i, qc, qd, qv);
//    }
//}
//
//int queryAns;
//
//void updateAns(int qc, int qd, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (cmin[i] > qc || dmin[i] > qd) {
//        return;
//    }
//    if (maxdp[i] <= queryAns) {
//        return;
//    }
//    if (cmax[i] <= qc && dmax[i] <= qd) {
//        queryAns = max(queryAns, maxdp[i]);
//        return;
//    }
//    if (c[i] <= qc && d[i] <= qd) {
//        queryAns = max(queryAns, dp[i]);
//    }
//    updateAns(qc, qd, ls[i]);
//    updateAns(qc, qd, rs[i]);
//}
//
//int query(int rank, int qc, int qd) {
//    queryAns = 0;
//    for (int i = rank; i > 0; i -= lowbit(i)) {
//        updateAns(qc, qd, root[i]);
//    }
//    return queryAns;
//}
//
//void prepare() {
//    stable_sort(abcd + 1, abcd + n + 1, ABCDCmp);
//    for (int i = 1; i <= n; i++) {
//        bi[i].b = abcd[i].b;
//        bi[i].i = i;
//    }
//    sort(bi + 1, bi + n + 1, BICmp);
//    for (int i = 1; i <= n; i++) {
//        ranking[bi[i].i] = i;
//    }
//    cmin[0] = dmin[0] = INF;
//    cmax[0] = dmax[0] = -INF;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> abcd[i].a >> abcd[i].b >> abcd[i].c >> abcd[i].d;
//    }
//    prepare();
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        int cur = query(ranking[i], abcd[i].c, abcd[i].d) + 1;
//        ans = max(ans, cur);
//        add(ranking[i], abcd[i].c, abcd[i].d, cur);
//    }
//    cout << ans << "\n";
//    return 0;
//}