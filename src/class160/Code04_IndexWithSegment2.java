package class160;

// 树状数组套线段树，C++版
// 给定一个长度为n的数组arr，下标1~n
// 每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 5 * 10^4
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 160;
//const int INF = INT_MAX;
//int n, m, s;
//int arr[MAXN];
//int ques[MAXN][4];
//int sorted[MAXN * 2];
//
//int root[MAXN];
//int sum[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int cntt;
//
//int pos[MAXN];
//int pre[MAXN];
//int cntpos, cntpre;
//
//int kth(int num) {
//    int l = 1, r = s;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        if (sorted[mid] == num) return mid;
//        if (sorted[mid] < num) l = mid + 1;
//        else r = mid - 1;
//    }
//    return -1;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//int innerAdd(int jobi, int jobv, int l, int r, int i) {
//    if (!i) i = ++cntt;
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) ls[i] = innerAdd(jobi, jobv, l, mid, ls[i]);
//        else rs[i] = innerAdd(jobi, jobv, mid + 1, r, rs[i]);
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int innerQuery(int jobk, int l, int r) {
//    if (l == r) return l;
//    int mid = (l + r) >> 1;
//    int leftsum = 0;
//    for (int i = 1; i <= cntpos; i++) leftsum += sum[ls[pos[i]]];
//    for (int i = 1; i <= cntpre; i++) leftsum -= sum[ls[pre[i]]];
//    if (jobk <= leftsum) {
//        for (int i = 1; i <= cntpos; i++) pos[i] = ls[pos[i]];
//        for (int i = 1; i <= cntpre; i++) pre[i] = ls[pre[i]];
//        return innerQuery(jobk, l, mid);
//    } else {
//        for (int i = 1; i <= cntpos; i++) pos[i] = rs[pos[i]];
//        for (int i = 1; i <= cntpre; i++) pre[i] = rs[pre[i]];
//        return innerQuery(jobk - leftsum, mid + 1, r);
//    }
//}
//
//int innerRank(int jobi, int l, int r) {
//    if (l == r) return 0;
//    int mid = (l + r) >> 1;
//    if (jobi <= mid) {
//        for (int i = 1; i <= cntpos; i++) pos[i] = ls[pos[i]];
//        for (int i = 1; i <= cntpre; i++) pre[i] = ls[pre[i]];
//        return innerRank(jobi, l, mid);
//    } else {
//        int leftsum = 0;
//        for (int i = 1; i <= cntpos; i++) {
//            leftsum += sum[ls[pos[i]]];
//            pos[i] = rs[pos[i]];
//        }
//        for (int i = 1; i <= cntpre; i++) {
//            leftsum -= sum[ls[pre[i]]];
//            pre[i] = rs[pre[i]];
//        }
//        return leftsum + innerRank(jobi, mid + 1, r);
//    }
//}
//
//void outerAdd(int i, int v) {
//    for (int j = i; j <= n; j += lowbit(j)) {
//        root[j] = innerAdd(arr[i], v, 1, s, root[j]);
//    }
//}
//
//int outerQuery(int l, int r, int k) {
//    cntpos = cntpre = 0;
//    for (int i = r; i > 0; i -= lowbit(i)) pos[++cntpos] = root[i];
//    for (int i = l - 1; i > 0; i -= lowbit(i)) pre[++cntpre] = root[i];
//    return sorted[innerQuery(k, 1, s)];
//}
//
//int outerRank(int l, int r, int k) {
//    cntpos = cntpre = 0;
//    for (int i = r; i > 0; i -= lowbit(i)) pos[++cntpos] = root[i];
//    for (int i = l - 1; i > 0; i -= lowbit(i)) pre[++cntpre] = root[i];
//    return innerRank(k, 1, s) + 1;
//}
//
//int outerPre(int l, int r, int k) {
//    int rk = outerRank(l, r, k);
//    return (rk == 1) ? -INF : outerQuery(l, r, rk - 1);
//}
//
//int outerPost(int l, int r, int k) {
//    if (k == s) return INF;
//    int rk = outerRank(l, r, k + 1);
//    return (rk == r - l + 2) ? INF : outerQuery(l, r, rk);
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= n; i++) sorted[++s] = arr[i];
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 3) sorted[++s] = ques[i][2];
//        else if (ques[i][0] != 2) sorted[++s] = ques[i][3];
//    }
//    sort(sorted + 1, sorted + s + 1);
//    int len = 1;
//    for (int i = 2; i <= s; i++)
//        if (sorted[len] != sorted[i]) sorted[++len] = sorted[i];
//    s = len;
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//        outerAdd(i, 1);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) cin >> arr[i];
//    for (int i = 1; i <= m; i++) {
//        cin >> ques[i][0] >> ques[i][1] >> ques[i][2];
//        if (ques[i][0] != 3) cin >> ques[i][3];
//    }
//    prepare();
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 1) {
//            cout << outerRank(ques[i][1], ques[i][2], kth(ques[i][3])) << "\n";
//        } else if (ques[i][0] == 2) {
//            cout << outerQuery(ques[i][1], ques[i][2], ques[i][3]) << "\n";
//        } else if (ques[i][0] == 3) {
//            outerAdd(ques[i][1], -1);
//            arr[ques[i][1]] = kth(ques[i][2]);
//            outerAdd(ques[i][1], 1);
//        } else if (ques[i][0] == 4) {
//            cout << outerPre(ques[i][1], ques[i][2], kth(ques[i][3])) << "\n";
//        } else {
//            cout << outerPost(ques[i][1], ques[i][2], kth(ques[i][3])) << "\n";
//        }
//    }
//    return 0;
//}