package class157;

// 去月球，C++版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=4348
// 测试链接 : https://www.spoj.com/problems/TTM/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//static const int MAXN = 100001;
//static const int MAXM = MAXN * 25;
//int n, q, t = 0;
//long long arr[MAXN];
//int root[MAXN];
//int ls[MAXM];
//int rs[MAXM];
//long long sum[MAXM];
//long long addTag[MAXM];
//int cnt = 0;
//
//int build(int l, int r) {
//    int rt = ++cnt;
//    addTag[rt] = 0;
//    if (l == r) {
//        sum[rt] = arr[l];
//    } else {
//        int mid = (l + r) / 2;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//        sum[rt] = sum[ls[rt]] + sum[rs[rt]];
//    }
//    return rt;
//}
//
//int add(int jobl, int jobr, long long jobv, int l, int r, int i) {
//    int rt = ++cnt, a = max(jobl, l), b = min(jobr, r);
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    sum[rt] = sum[i] + jobv * (b - a + 1);
//    addTag[rt] = addTag[i];
//    if (jobl <= l && r <= jobr) {
//        addTag[rt] += jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobl <= mid) {
//            ls[rt] = add(jobl, jobr, jobv, l, mid, ls[rt]);
//        }
//        if (jobr > mid) {
//            rs[rt] = add(jobl, jobr, jobv, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//long long query(int jobl, int jobr, long long historyAdd, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return sum[i] + historyAdd * (r - l + 1);
//    }
//    int mid = (l + r) / 2;
//    long long ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, historyAdd + addTag[i], l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, historyAdd + addTag[i], mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    root[0] = build(1, n);
//    for (int i = 1; i <= q; i++) {
//        char op;
//        cin >> op;
//        if (op == 'C') {
//            int x, y;
//            long long z;
//            cin >> x >> y >> z;
//            root[t + 1] = add(x, y, z, 1, n, root[t]);
//            t++;
//        } else if (op == 'Q') {
//            int x, y;
//            cin >> x >> y;
//            cout << query(x, y, 0, 1, n, root[t]) << "\n";
//        } else if (op == 'H') {
//            int x, y, z;
//            cin >> x >> y >> z;
//            cout << query(x, y, 0, 1, n, root[z]) << "\n";
//        } else {
//            cin >> t;
//        }
//    }
//    return 0;
//}