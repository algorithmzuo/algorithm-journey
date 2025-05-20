package class169;

// 带修改的区间第k小，C++版
// 给定一个长度为n的数组arr，接下来是m条操作，每种操作是如下两种类型的一种
// 操作 Q x y v : 查询arr[x..y]范围上第v小的值
// 操作 C x y   : 把x位置的值修改成y
// 1 <= n、m <= 10^5
// 1 <= 数组中的值 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2617
// 本题是讲解160，树套树模版题，现在作为带修改的整体二分模版题
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXE = MAXN << 2;
//const int INF = 1000000001;
//int n, m;
//
//int arr[MAXN];
//int eid[MAXE];
//int op[MAXE];
//int x[MAXE];
//int y[MAXE];
//int v[MAXE];
//int q[MAXE];
//int cnte = 0;
//int cntq = 0;
//
//int tree[MAXN];
//
//int lset[MAXE];
//int rset[MAXE];
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
//            if (op[id] == 1) {
//                ans[q[id]] = vl;
//            }
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        int lsiz = 0, rsiz = 0;
//        for (int i = el; i <= er; i++) {
//            int id = eid[i];
//            if (op[id] == 1) {
//                int satisfy = query(x[id], y[id]);
//                if (v[id] <= satisfy) {
//                    lset[++lsiz] = id;
//                } else {
//                    v[id] -= satisfy;
//                    rset[++rsiz] = id;
//                }
//            } else {
//                if (y[id] <= mid) {
//                    add(x[id], v[id]);
//                    lset[++lsiz] = id;
//                } else {
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
//            if (op[id] == 2 && y[id] <= mid) {
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
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//        op[++cnte] = 2;
//        x[cnte] = i;
//        y[cnte] = arr[i];
//        v[cnte] = 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        char type;
//        cin >> type;
//        if (type == 'Q') {
//            op[++cnte] = 1;
//            cin >> x[cnte] >> y[cnte] >> v[cnte];
//            q[cnte] = ++cntq;
//        } else {
//            int a, b;
//            cin >> a >> b;
//            op[++cnte] = 2;
//            x[cnte] = a;
//            y[cnte] = arr[a];
//            v[cnte] = -1;
//            op[++cnte] = 2;
//            x[cnte] = a;
//            y[cnte] = b;
//            v[cnte] = 1;
//            arr[a] = b;
//        }
//    }
//    for (int i = 1; i <= cnte; i++) {
//        eid[i] = i;
//    }
//    compute(1, cnte, 0, INF);
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}