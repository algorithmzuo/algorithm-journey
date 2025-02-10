package class160;

// K大数查询，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3332
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 230;
//int n, m, s;
//int ques[MAXN][4];
//int sorted[MAXN];
//int root[MAXN << 2];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//int lazy[MAXT];
//int cntt;
//
//int kth(int num) {
//    int l = 1, r = s;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        if (sorted[mid] == num) {
//            return mid;
//        } else if (sorted[mid] < num) {
//            l = mid + 1;
//        } else {
//            r = mid - 1;
//        }
//    }
//    return -1;
//}
//
//void up(int i) {
//    sum[i] = sum[ls[i]] + sum[rs[i]];
//}
//
//void down(int i, int ln, int rn) {
//    if (lazy[i]) {
//        if (!ls[i]) ls[i] = ++cntt;
//        if (!rs[i]) rs[i] = ++cntt;
//        sum[ls[i]] += 1LL * lazy[i] * ln;
//        lazy[ls[i]] += lazy[i];
//        sum[rs[i]] += 1LL * lazy[i] * rn;
//        lazy[rs[i]] += lazy[i];
//        lazy[i] = 0;
//    }
//}
//
//int update(int jobl, int jobr, int l, int r, int i) {
//    if (!i) i = ++cntt;
//    if (jobl <= l && r <= jobr) {
//        sum[i] += (long long)(r - l + 1);
//        lazy[i]++;
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) ls[i] = update(jobl, jobr, l, mid, ls[i]);
//        if (jobr > mid) rs[i] = update(jobl, jobr, mid + 1, r, rs[i]);
//        up(i);
//    }
//    return i;
//}
//
//long long querySum(int jobl, int jobr, int l, int r, int i) {
//    if (!i) return 0;
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    down(i, mid - l + 1, r - mid);
//    long long ans = 0;
//    if (jobl <= mid) ans += querySum(jobl, jobr, l, mid, ls[i]);
//    if (jobr > mid) ans += querySum(jobl, jobr, mid + 1, r, rs[i]);
//    return ans;
//}
//
//void add(int jobl, int jobr, int jobk, int l, int r, int i) {
//    root[i] = update(jobl, jobr, 1, n, root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (jobk <= mid) {
//            add(jobl, jobr, jobk, l, mid, i << 1);
//        } else {
//            add(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//int query(int jobl, int jobr, long long jobk, int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    long long rightsum = querySum(jobl, jobr, 1, n, root[i << 1 | 1]);
//    if (jobk > rightsum) {
//        return query(jobl, jobr, jobk - rightsum, l, mid, i << 1);
//    } else {
//        return query(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
//    }
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 1) {
//            sorted[++s] = ques[i][3];
//        }
//    }
//    sort(sorted + 1, sorted + s + 1);
//    int len = 1;
//    for (int i = 2; i <= s; i++) {
//        if (sorted[len] != sorted[i]) {
//            sorted[++len] = sorted[i];
//        }
//    }
//    s = len;
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 1) {
//            ques[i][3] = kth(ques[i][3]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> ques[i][0] >> ques[i][1] >> ques[i][2] >> ques[i][3];
//    }
//    prepare();
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 1) {
//            add(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
//        } else {
//            int idx = query(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
//            cout << sorted[idx] << "\n";
//        }
//    }
//    return 0;
//}