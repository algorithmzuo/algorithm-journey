package class176;

// 异或序列，C++版
// 给定一个长度为n的数组arr，给定一个数字k，一共有m条查询，格式如下
// 查询 l r : arr[l..r]范围上，有多少子数组的异或和为k，打印其数量
// 0 <= n、m、k、arr[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4462
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
//const int MAXN = 100001;
//const int MAXS = 1 << 20;
//int n, m, k;
//int arr[MAXN];
//Query query[MAXN];
//int bi[MAXN];
//
//int pre[MAXN];
//long long cnt[MAXS];
//long long num;
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//void del(int x) {
//    if (k != 0) {
//        num -= cnt[x] * cnt[x ^ k];
//    } else {
//        num -= (cnt[x] * (cnt[x] - 1)) >> 1;
//    }
//    cnt[x]--;
//    if (k != 0) {
//        num += cnt[x] * cnt[x ^ k];
//    } else {
//        num += (cnt[x] * (cnt[x] - 1)) >> 1;
//    }
//}
//
//void add(int x) {
//    if (k != 0) {
//        num -= cnt[x] * cnt[x ^ k];
//    } else {
//        num -= (cnt[x] * (cnt[x] - 1)) >> 1;
//    }
//    cnt[x]++;
//    if (k != 0) {
//        num += cnt[x] * cnt[x ^ k];
//    } else {
//        num += (cnt[x] * (cnt[x] - 1)) >> 1;
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        pre[i] = pre[i - 1] ^ arr[i];
//    }
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l - 1;
//        int jobr = query[i].r;
//        while (winl > jobl) {
//            add(pre[--winl]);
//        }
//        while (winr < jobr) {
//            add(pre[++winr]);
//        }
//        while (winl < jobl) {
//            del(pre[winl++]);
//        }
//        while (winr > jobr) {
//            del(pre[winr--]);
//        }
//        ans[query[i].id] = num;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query[i].l;
//        cin >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}