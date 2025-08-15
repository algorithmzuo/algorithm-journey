package class176;

// 大爷的字符串题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3709
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
//const int MAXN = 200001;
//int n, m;
//int arr[MAXN];
//int sorted[MAXN];
//Query query[MAXN];
//
//int bi[MAXN];
//int cnt1[MAXN];
//int cnt2[MAXN];
//int maxCnt = 0;
//int ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//int kth(int len, int num) {
//    int left = 1, right = len, mid, ret = 0;
//    while (left <= right) {
//        mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//        	ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//void del(int num) {
//    if (cnt1[num] == maxCnt && cnt2[cnt1[num]] == 1) {
//        maxCnt--;
//    }
//    cnt2[cnt1[num]]--;
//    cnt2[--cnt1[num]]++;
//}
//
//void add(int num) {
//    cnt2[cnt1[num]]--;
//    cnt2[++cnt1[num]]++;
//    if (cnt1[num] > maxCnt) {
//        maxCnt = cnt1[num];
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    int len = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[len] != sorted[i]) {
//            sorted[++len] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(len, arr[i]);
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
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        while (winl > jobl) {
//            add(arr[--winl]);
//        }
//        while (winr < jobr) {
//            add(arr[++winr]);
//        }
//        while (winl < jobl) {
//            del(arr[winl++]);
//        }
//        while (winr > jobr) {
//            del(arr[winr--]);
//        }
//        ans[query[i].id] = maxCnt;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
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
//        cout << -ans[i] << '\n';
//    }
//    return 0;
//}