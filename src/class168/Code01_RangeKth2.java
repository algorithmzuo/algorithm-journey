package class168;

// 区间内第k小，C++版
// 本题是讲解157，可持久化线段树模版题，现在作为整体二分的模版题
// 给定一个长度为n的数组，接下来有m条查询，格式如下
// 查询 l r k : 打印[l..r]范围内第k小的值
// 1 <= n、m <= 2 * 10^5
// 1 <= 数组中的数字 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3834
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Number {
//    int i, v;
//};
//
//bool NumberCmp(Number x, Number y) {
//    return x.v < y.v;
//}
//
//const int MAXN = 200001;
//const int INF = 1000000001;
//int n, m;
//
//Number arr[MAXN];
//
//int qid[MAXN];
//int l[MAXN];
//int r[MAXN];
//int k[MAXN];
//
//int tree[MAXN];
//int used = 0;
//
//int lset[MAXN];
//int rset[MAXN];
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
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            ans[qid[i]] = vl;
//        }
//    } else {
//        int mid = (vl + vr) / 2;
//        while (used + 1 <= n && arr[used + 1].v <= mid) {
//            used++;
//            add(arr[used].i, 1);
//        }
//        while (used >= 1 && arr[used].v > mid) {
//            add(arr[used].i, -1);
//            used--;
//        }
//        int lsiz = 0, rsiz = 0;
//        for (int i = ql; i <= qr; i++) {
//            int id = qid[i];
//            int check = query(l[id], r[id]);
//            if (check >= k[id]) {
//                lset[++lsiz] = id;
//            } else {
//                rset[++rsiz] = id;
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            qid[ql + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            qid[ql + lsiz + i - 1] = rset[i];
//        }
//        compute(ql, ql + lsiz - 1, vl, mid);
//        compute(ql + lsiz, qr, mid + 1, vr);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        arr[i].i = i;
//        cin >> arr[i].v;
//    }
//    for (int i = 1; i <= m; i++) {
//        qid[i] = i;
//        cin >> l[i] >> r[i] >> k[i];
//    }
//    sort(arr + 1, arr + n + 1, NumberCmp);
//    compute(1, m, 0, INF);
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}