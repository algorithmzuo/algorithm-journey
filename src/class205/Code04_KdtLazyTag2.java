package class205;

// kd树结合懒更新，C++版
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
//int k, m, cntn;
//
//ll qpos[MAXK];
//ll ql[MAXK];
//ll qr[MAXK];
//ll qv;
//
//ll pos[MAXN][MAXK];
//ll val[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int siz[MAXN];
//ll sum[MAXN];
//ll tag[MAXN];
//
//ll minv[MAXN][MAXK];
//ll maxv[MAXN][MAXK];
//
//int root[MAXP];
//
//void swap(int i, int j) {
//    swap(pos[i], pos[j]);
//    swap(val[i], val[j]);
//}
//
//int first, last;
//
//void partition(int l, int r, ll pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        if (pos[i][dimension] == pivot) {
//            i++;
//        } else if (pos[i][dimension] < pivot) {
//            swap(first++, i++);
//        } else {
//            swap(i, last--);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        ll pivot = pos[l + rand() % (r - l + 1)][dimension];
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
//    siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
//    sum[i] = val[i] + sum[ls[i]] + sum[rs[i]];
//    for (int d = 0; d < k; d++) {
//        minv[i][d] = min(pos[i][d], min(minv[ls[i]][d], minv[rs[i]][d]));
//        maxv[i][d] = max(pos[i][d], max(maxv[ls[i]][d], maxv[rs[i]][d]));
//    }
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
//        ls[mid] = build(l, mid - 1, (dimension + 1) % k);
//        rs[mid] = build(mid + 1, r, (dimension + 1) % k);
//    }
//    maintain(mid);
//    return mid;
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
//void dfs(int i) {
//    if (i != 0) {
//        down(i);
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void insert() {
//    cntn++;
//    for (int d = 0; d < k; d++) {
//        pos[cntn][d] = qpos[d];
//    }
//    val[cntn] = qv;
//    int p = 0;
//    while (root[p] != 0) {
//        dfs(root[p]);
//        root[p++] = 0;
//    }
//    root[p] = build(cntn - (1 << p) + 1, cntn, 0);
//}
//
//bool outside(int i) {
//    for (int d = 0; d < k; d++) {
//        if (maxv[i][d] < ql[d] || qr[d] < minv[i][d]) {
//            return true;
//        }
//    }
//    return false;
//}
//
//bool covered(int i) {
//    for (int d = 0; d < k; d++) {
//        if (ql[d] > minv[i][d] || qr[d] < maxv[i][d]) {
//            return false;
//        }
//    }
//    return true;
//}
//
//bool pointIn(int i) {
//    for (int d = 0; d < k; d++) {
//        if (ql[d] > pos[i][d] || qr[d] < pos[i][d]) {
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
//        ans += val[i];
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
//    srand((unsigned)time(nullptr));
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
//                cin >> qpos[d];
//                qpos[d] ^= lastAns;
//            }
//            cin >> qv;
//            qv ^= lastAns;
//            insert();
//        } else {
//            for (int d = 0; d < k; d++) {
//                cin >> ql[d];
//                ql[d] ^= lastAns;
//            }
//            for (int d = 0; d < k; d++) {
//                cin >> qr[d];
//                qr[d] ^= lastAns;
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