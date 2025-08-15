package class176;

// 小B的询问，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2709
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
//const int MAXN = 50001;
//int n, m, k;
//int arr[MAXN];
//Query query[MAXN];
//
//int bi[MAXN];
//int cnt[MAXN];
//long long sum = 0;
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//void del(int idx) {
//    sum -= 2 * cnt[arr[idx]] - 1;
//    cnt[arr[idx]]--;
//}
//
//void add(int idx) {
//    sum += 2 * cnt[arr[idx]] + 1;
//    cnt[arr[idx]]++;
//}
//
//void prepare() {
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
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        while (winl > jobl) {
//            add(--winl);
//        }
//        while (winr < jobr) {
//            add(++winr);
//        }
//        while (winl < jobl) {
//            del(winl++);
//        }
//        while (winr > jobr) {
//            del(winr--);
//        }
//        ans[query[i].id] = sum;
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