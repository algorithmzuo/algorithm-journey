package class157;

// 区间内的不同数字，C++版
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5919
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//static const int MAXN = 200002;
//static const int MAXM = MAXN * 37;
//int cases, n, q;
//int arr[MAXN];
//int pos[MAXN];
//int root[MAXN];
//int ls[MAXM];
//int rs[MAXM];
//int diff[MAXM];
//int cnt;
//
//int build(int l, int r) {
//    int rt = ++cnt;
//    if (l == r) {
//        return rt;
//    }
//    int mid = (l + r) / 2;
//    ls[rt] = build(l, mid);
//    rs[rt] = build(mid + 1, r);
//    return rt;
//}
//
//int update(int jobi, int jobv, int l, int r, int i) {
//    int rt = ++cnt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    diff[rt] = diff[i] + jobv;
//    if (l == r) {
//        return rt;
//    }
//    int mid = (l + r) / 2;
//    if (jobi <= mid) {
//        ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
//    } else {
//        rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
//    }
//    return rt;
//}
//
//int queryDiff(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return diff[i];
//    }
//    int mid = (l + r) / 2;
//    int ans = 0;
//    if (jobl <= mid) {
//        ans += queryDiff(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += queryDiff(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//int queryKth(int jobk, int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) / 2;
//    int leftDiff = diff[ls[i]];
//    if (leftDiff >= jobk) {
//        return queryKth(jobk, l, mid, ls[i]);
//    } else {
//        return queryKth(jobk - leftDiff, mid + 1, r, rs[i]);
//    }
//}
//
//void prepare() {
//    cnt = 0;
//    memset(pos, 0, sizeof(pos));
//    root[n + 1] = build(1, n);
//    for (int i = n; i >= 1; i--) {
//        if (pos[arr[i]] == 0) {
//            root[i] = update(i, 1, 1, n, root[i + 1]);
//        } else {
//            root[i] = update(pos[arr[i]], -1, 1, n, root[i + 1]);
//            root[i] = update(i, 1, 1, n, root[i]);
//        }
//        pos[arr[i]] = i;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> cases;
//    for (int t = 1; t <= cases; t++) {
//        cin >> n >> q;
//        for (int i = 1; i <= n; i++) {
//            cin >> arr[i];
//        }
//        prepare();
//        cout << "Case #" << t << ":";
//        for (int i = 1, l, r, k, lastAns = 0; i <= q; i++) {
//            cin >> l >> r;
//            l = (l + lastAns) % n + 1;
//            r = (r + lastAns) % n + 1;
//            if (l > r) {
//                swap(l, r);
//            }
//            k = (queryDiff(l, r, 1, n, root[l]) + 1) >> 1;
//            lastAns = queryKth(k, 1, n, root[l]);
//            cout << " " << lastAns;
//        }
//        cout << "\n";
//    }
//    return 0;
//}