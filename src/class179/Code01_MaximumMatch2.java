package class179;

// 区间最大匹配，C++版
// 给定长度为n的数组a、长度为m的数组b、一个正数z
// 数组a中数字x、数组b中数字y，如果x + y <= z，那么构成一个匹配
// 已经匹配的数字，不可以重复使用，一共有q条查询，格式如下
// 查询 l r : 数组b[l..r]范围上的数字，随意选择数组a中的数字，打印最多匹配数
// 1 <= n <= 152501
// 1 <= m、q <= 52501
// 1 <= a[i]、b[i]、z <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4477
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, id;
//};
//
//const int MAXN = 152502;
//const int MAXM = 52502;
//const int MAXQ = 52502;
//int n, m, z, q;
//int a[MAXN];
//int b[MAXM];
//Query querys[MAXQ];
//int bi[MAXM];
//
//int match[MAXN << 2];
//int resta[MAXN << 2];
//int overb[MAXN << 2];
//
//int ans[MAXQ];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if (bi[a.l] & 1) {
//        return a.r < b.r;
//    } else {
//        return a.r > b.r;
//    }
//}
//
//void up(int i) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    int newMatch = min(resta[l], overb[r]);
//    resta[i] = resta[l] + resta[r] - newMatch;
//    overb[i] = overb[l] + overb[r] - newMatch;
//    match[i] = match[l] + match[r] + newMatch;
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//    	match[i] = 0;
//        resta[i] = 1;
//        overb[i] = 0;
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void add(int jobv, int l, int r, int i) {
//    if (l == r) {
//        if (resta[i] == 1) {
//        	match[i] = 1;
//            resta[i] = 0;
//        } else {
//            overb[i]++;
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobv + a[mid + 1] <= z) {
//            add(jobv, mid + 1, r, i << 1 | 1);
//        } else if (jobv + a[l] <= z) {
//            add(jobv, l, mid, i << 1);
//        }
//        up(i);
//    }
//}
//
//void del(int jobv, int l, int r, int i) {
//    if (l == r) {
//        if (overb[i] == 0) {
//        	match[i] = 0;
//            resta[i] = 1;
//        } else {
//            overb[i]--;
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobv + a[mid + 1] <= z) {
//            del(jobv, mid + 1, r, i << 1 | 1);
//        } else if (jobv + a[l] <= z) {
//            del(jobv, l, mid, i << 1);
//        }
//        up(i);
//    }
//}
//
//void prepare() {
//    sort(a + 1, a + n + 1);
//    int blen = (int)sqrt(m);
//    for (int i = 1; i <= m; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(querys + 1, querys + q + 1, QueryCmp);
//    build(1, n, 1);
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= q; i++) {
//        int jobl = querys[i].l;
//        int jobr = querys[i].r;
//        int id   = querys[i].id;
//        while (winl > jobl) {
//            add(b[--winl], 1, n, 1);
//        }
//        while (winr < jobr) {
//            add(b[++winr], 1, n, 1);
//        }
//        while (winl < jobl) {
//            del(b[winl++], 1, n, 1);
//        }
//        while (winr > jobr) {
//            del(b[winr--], 1, n, 1);
//        }
//        ans[id] = match[1];
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> z;
//    for (int i = 1; i <= n; i++) {
//        cin >> a[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> b[i];
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> querys[i].l >> querys[i].r;
//        querys[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= q; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}