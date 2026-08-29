package class205;

// kd树结合懒更新，二进制分组，C++版
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
//const int MAXP = 19;
//const int MAXK = 3;
//const ll INF = 1LL << 60;
//int k, m;
//
//ll qx[MAXK];
//ll qy[MAXK];
//ll qv;
//
//struct Node {
//    ll pos[MAXK];
//    ll val;
//};
//
//struct Cmp {
//    int dimension;
//
//    bool operator()(const Node &a, const Node &b) const {
//        return a.pos[dimension] < b.pos[dimension];
//    }
//};
//
//int cntkdt;
//int root[MAXP];
//Node arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int siz[MAXN];
//
//ll sum[MAXN];
//ll tag[MAXN];
//ll minv[MAXN][MAXK];
//ll maxv[MAXN][MAXK];
//
//void maintain(int i) {
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    sum[i] = arr[i].val + sum[ls[i]] + sum[rs[i]];
//    for (int d = 0; d < k; d++) {
//        minv[i][d] = min(arr[i].pos[d], min(minv[ls[i]][d], minv[rs[i]][d]));
//        maxv[i][d] = max(arr[i].pos[d], max(maxv[ls[i]][d], maxv[rs[i]][d]));
//    }
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    nth_element(arr + l, arr + mid, arr + r + 1, Cmp{dimension});
//    ls[mid] = build(l, mid - 1, (dimension + 1) % k);
//    rs[mid] = build(mid + 1, r, (dimension + 1) % k);
//    maintain(mid);
//    return mid;
//}
//
//void lazy(int i, ll v) {
//    if (i != 0) {
//        arr[i].val += v;
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
//void dfs(int i) {
//    if (i != 0) {
//        down(i);
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void insert() {
//    cntkdt++;
//    for (int d = 0; d < k; d++) {
//        arr[cntkdt].pos[d] = qx[d];
//    }
//    arr[cntkdt].val = qv;
//    int p = 0;
//    while (root[p] != 0) {
//        dfs(root[p]);
//        root[p++] = 0;
//    }
//    root[p] = build(cntkdt - (1 << p) + 1, cntkdt, 0);
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
//        if (qx[d] > arr[i].pos[d] || qy[d] < arr[i].pos[d]) {
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
//        arr[i].val += qv;
//    }
//    down(i);
//    addValue(ls[i]);
//    addValue(rs[i]);
//    maintain(i);
//}
//
//void addValue() {
//    for (int p = 0; p < MAXP; p++) {
//        addValue(root[p]);
//    }
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
//        ans += arr[i].val;
//    }
//    down(i);
//    ans += querySum(ls[i]);
//    ans += querySum(rs[i]);
//    return ans;
//}
//
//ll querySum() {
//    ll ans = 0;
//    for (int p = 0; p < MAXP; p++) {
//        ans += querySum(root[p]);
//    }
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
//                addValue();
//            } else {
//                lastAns = querySum();
//                cout << lastAns << "\n";
//            }
//        }
//    }
//    return 0;
//}