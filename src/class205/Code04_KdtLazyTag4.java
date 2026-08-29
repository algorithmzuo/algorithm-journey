package class205;

// kd树结合懒更新，替罪羊树的方式，C++版
// 点的坐标有k维，点还有点权，k维空间中的轴对齐区域，可以用两个对角点表示
// 一共有m条操作，类型如下
// 操作 1 qx qv    : 空间里增加一个点，qx是k个值表示点的坐标，qv表示点权
// 操作 2 qx qy qv : 区域的两个对角点qx和qy，各自有k个值的坐标，该区域所有点的点权增加qv
// 操作 3 qx qy    : 区域的两个对角点qx和qy，各自有k个值的坐标，打印该区域所有点的点权和
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 2 <= k <= 3
// 1 <= m <= 10^5
// 坐标、点权、答案都需要long类型
// 测试链接 : https://www.luogu.com.cn/problem/P14312
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 200001;
//const int MAXK = 3;
//const ll INF = 1LL << 60;
//int k, m;
//
//ll qx[MAXK];
//ll qy[MAXK];
//ll qv;
//
//int cntkdt;
//int root;
//ll pos[MAXN][MAXK];
//ll val[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//int siz[MAXN];
//ll sum[MAXN];
//ll tag[MAXN];
//
//ll minv[MAXN][MAXK];
//ll maxv[MAXN][MAXK];
//
//double ALPHA = 0.7;
//int collect[MAXN];
//int collectSiz;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//
//struct Cmp {
//    int dimension;
//
//    bool operator()(int a, int b) const {
//        return pos[a][dimension] < pos[b][dimension];
//    }
//};
//
//int init() {
//    cntkdt++;
//    for (int d = 0; d < k; d++) {
//        pos[cntkdt][d] = qx[d];
//        minv[cntkdt][d] = maxv[cntkdt][d] = qx[d];
//    }
//    val[cntkdt] = qv;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    siz[cntkdt] = 1;
//    sum[cntkdt] = qv;
//    tag[cntkdt] = 0;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    sum[i] = val[i] + sum[ls[i]] + sum[rs[i]];
//    for (int d = 0; d < k; d++) {
//        minv[i][d] = min(pos[i][d], min(minv[ls[i]][d], minv[rs[i]][d]));
//        maxv[i][d] = max(pos[i][d], max(maxv[ls[i]][d], maxv[rs[i]][d]));
//    }
//}
//
//void lazy(int i, ll v) {
//    if (i != 0) {
//        val[i] += v;
//        sum[i] += v * siz[i];
//        tag[i] += v;
//    }
//}
//
//void down(int i) {
//    if (tag[i] != 0) {
//        lazy(ls[i], tag[i]);
//        lazy(rs[i], tag[i]);
//        tag[i] = 0;
//    }
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    nth_element(collect + l, collect + mid, collect + r + 1, Cmp{dimension});
//    int rt = collect[mid];
//    ls[rt] = build(l, mid - 1, (dimension + 1) % k);
//    rs[rt] = build(mid + 1, r, (dimension + 1) % k);
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
//        down(i);
//        collect[++collectSiz] = i;
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
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
//        down(u);
//        if (pos[insertNode][dimension] <= pos[u][dimension]) {
//            add(insertNode, ls[u], u, 1, (dimension + 1) % k);
//        } else {
//            add(insertNode, rs[u], u, 2, (dimension + 1) % k);
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
//void insert() {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init();
//    add(insertNode, root, 0, 0, 0);
//    rebuild();
//}
//
//bool outside(int i) {
//    for (int d = 0; d < k; d++) {
//        if (maxv[i][d] < qx[d] || qy[d] < minv[i][d]) {
//            return true;
//        }
//    }
//    return false;
//}
//
//bool covered(int i) {
//    for (int d = 0; d < k; d++) {
//        if (qx[d] > minv[i][d] || qy[d] < maxv[i][d]) {
//            return false;
//        }
//    }
//    return true;
//}
//
//bool pointIn(int i) {
//    for (int d = 0; d < k; d++) {
//        if (qx[d] > pos[i][d] || qy[d] < pos[i][d]) {
//            return false;
//        }
//    }
//    return true;
//}
//
//void addValue(int i) {
//    if (i == 0) {
//        return;
//    }
//    if (outside(i)) {
//        return;
//    }
//    if (covered(i)) {
//        lazy(i, qv);
//        return;
//    }
//    if (pointIn(i)) {
//        val[i] += qv;
//    }
//    down(i);
//    addValue(ls[i]);
//    addValue(rs[i]);
//    maintain(i);
//}
//
//ll querySum(int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (outside(i)) {
//        return 0;
//    }
//    if (covered(i)) {
//        return sum[i];
//    }
//    ll ans = 0;
//    if (pointIn(i)) {
//        ans += val[i];
//    }
//    down(i);
//    ans += querySum(ls[i]);
//    ans += querySum(rs[i]);
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> k >> m;
//    for (int d = 0; d < k; d++) {
//        minv[0][d] = INF;
//        maxv[0][d] = -INF;
//    }
//    ll lastAns = 0;
//    for (int i = 1, op; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
//            for (int d = 0; d < k; d++) {
//                cin >> qx[d];
//                qx[d] ^= lastAns;
//            }
//            cin >> qv;
//            qv ^= lastAns;
//            insert();
//        } else {
//            for (int d = 0; d < k; d++) {
//                cin >> qx[d];
//                qx[d] ^= lastAns;
//            }
//            for (int d = 0; d < k; d++) {
//                cin >> qy[d];
//                qy[d] ^= lastAns;
//            }
//            if (op == 2) {
//                cin >> qv;
//                qv ^= lastAns;
//                addValue(root);
//            } else {
//                lastAns = querySum(root);
//                cout << lastAns << "\n";
//            }
//        }
//    }
//    return 0;
//}