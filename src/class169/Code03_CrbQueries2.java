package class169;

// 带修改的区间第k小，C++版
// 给定一个长度为n的数组arr，接下来是m条操作，每种操作是如下两种类型的一种
// 操作 1 x y   : 把x位置的值修改成y
// 操作 2 x y v : 查询arr[x..y]范围上第v小的值
// 1 <= n、m <= 10^5
// 1 <= 数组中的值 <= 10^9
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5412
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int INF = 1000000001;
//int n, m;
//
//int arr[MAXN];
//
//int eid[MAXN << 2];
//int op[MAXN << 2];
//int x[MAXN << 2];
//int y[MAXN << 2];
//int v[MAXN << 2];
//int q[MAXN << 2];
//
//int tree[MAXN];
//
//int lset[MAXN << 2];
//int rset[MAXN << 2];
//
//int ans[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//int query(int l, int r) {
//    return sum(r) - sum(l - 1);
//}
//
//void compute(int el, int er, int vl, int vr) {
//    if (el > er) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = el; i <= er; i++) {
//            int id = eid[i];
//            if (op[id] == 2) {
//                ans[q[id]] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        int lsiz = 0, rsiz = 0;
//        for (int i = el; i <= er; i++) {
//            int id = eid[i];
//            if (op[id] == 1) {
//                if (y[id] <= mid) {
//                    add(x[id], v[id]);
//                    lset[++lsiz] = id;
//                } else {
//                    rset[++rsiz] = id;
//                }
//            } else {
//                int satisfy = query(x[id], y[id]);
//                if (v[id] <= satisfy) {
//                    lset[++lsiz] = id;
//                } else {
//                    v[id] -= satisfy;
//                    rset[++rsiz] = id;
//                }
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            eid[el + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            eid[el + lsiz + i - 1] = rset[i];
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            int id = lset[i];
//            if (op[id] == 1 && y[id] <= mid) {
//                add(x[id], -v[id]);
//            }
//        }
//        compute(el, el + lsiz - 1, vl, mid);
//        compute(el + lsiz, er, mid + 1, vr);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    while (cin >> n) {
//        fill(tree + 1, tree + n + 1, 0);
//        int eventCnt = 0;
//        for (int i = 1; i <= n; i++) {
//            cin >> arr[i];
//            op[++eventCnt] = 1;
//            x[eventCnt] = i;
//            y[eventCnt] = arr[i];
//            v[eventCnt] = 1;
//        }
//        cin >> m;
//        int queryCnt = 0;
//        for (int i = 1; i <= m; i++) {
//            cin >> op[++eventCnt];
//            if (op[eventCnt] == 1) {
//                int a, b;
//                cin >> a >> b;
//                x[eventCnt] = a;
//                y[eventCnt] = arr[a];
//                v[eventCnt] = -1;
//                op[++eventCnt] = 1;
//                x[eventCnt] = a;
//                y[eventCnt] = b;
//                v[eventCnt] = 1;
//                arr[a] = b;
//            } else {
//                cin >> x[eventCnt] >> y[eventCnt] >> v[eventCnt];
//                q[eventCnt] = ++queryCnt;
//            }
//        }
//        for (int i = 1; i <= eventCnt; i++) {
//            eid[i] = i;
//        }
//        compute(1, eventCnt, 1, INF);
//        for (int i = 1; i <= queryCnt; i++) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}