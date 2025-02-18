package class160;

// k大数查询，C++版
// 初始时有n个空集合，编号1~n，实现如下两种类型的操作，操作一共发生m次
// 操作 1 l r v : 数字v放入编号范围[l,r]的每一个集合中
// 操作 2 l r k : 编号范围[l,r]的所有集合，如果生成不去重的并集，返回第k大的数字
// 1 <= n、m <= 5 * 10^4
// 1 <= v <= n
// 1 <= k < 2^63，题目保证第k大的数字一定存在
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
//
//int root[MAXN << 2];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//int lazy[MAXT];
//int cnt;
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
//        if (!ls[i]) ls[i] = ++cnt;
//        if (!rs[i]) rs[i] = ++cnt;
//        sum[ls[i]] += 1LL * lazy[i] * ln;
//        lazy[ls[i]] += lazy[i];
//        sum[rs[i]] += 1LL * lazy[i] * rn;
//        lazy[rs[i]] += lazy[i];
//        lazy[i] = 0;
//    }
//}
//
//int innerAdd(int jobl, int jobr, int l, int r, int i) {
//    if (!i) i = ++cnt;
//    if (jobl <= l && r <= jobr) {
//        sum[i] += (long long)(r - l + 1);
//        lazy[i]++;
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) ls[i] = innerAdd(jobl, jobr, l, mid, ls[i]);
//        if (jobr > mid) rs[i] = innerAdd(jobl, jobr, mid + 1, r, rs[i]);
//        up(i);
//    }
//    return i;
//}
//
//long long innerQuery(int jobl, int jobr, int l, int r, int i) {
//    if (!i) return 0;
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    down(i, mid - l + 1, r - mid);
//    long long ans = 0;
//    if (jobl <= mid) ans += innerQuery(jobl, jobr, l, mid, ls[i]);
//    if (jobr > mid) ans += innerQuery(jobl, jobr, mid + 1, r, rs[i]);
//    return ans;
//}
//
//void outerAdd(int jobl, int jobr, int jobk, int l, int r, int i) {
//    root[i] = innerAdd(jobl, jobr, 1, n, root[i]);
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (jobk <= mid) {
//            outerAdd(jobl, jobr, jobk, l, mid, i << 1);
//        } else {
//            outerAdd(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//int outerQuery(int jobl, int jobr, long long jobk, int l, int r, int i) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    long long rightsum = innerQuery(jobl, jobr, 1, n, root[i << 1 | 1]);
//    if (jobk > rightsum) {
//        return outerQuery(jobl, jobr, jobk - rightsum, l, mid, i << 1);
//    } else {
//        return outerQuery(jobl, jobr, jobk, mid + 1, r, i << 1 | 1);
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
//            outerAdd(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
//        } else {
//            int idx = outerQuery(ques[i][1], ques[i][2], ques[i][3], 1, s, 1);
//            cout << sorted[idx] << "\n";
//        }
//    }
//    return 0;
//}