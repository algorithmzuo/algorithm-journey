package class179;

// 掉进兔子洞，C++版
// 三个区间同时出现的数，一个一个删掉，直到无法再删，剩下数字的个数叫做 剩余个数
// A = [1 2 2 3 3 3]    B = [1 2 2 3 3 3]    C = [1 1 2 3 3]
// 删除的过程为，一起删掉一个1、一起删掉一个2、一起删掉2个3，然后状况为
// A = [2 3]    B = [2 3]    C = [1]    剩余个数为5
// 给定一个长度为n的数组arr，下来有m条查询，格式如下
// 查询 l1 r1 l2 r2 l3 r3 : 给定了三个区间，打印剩余个数
// 1 <= n、m <= 10^5
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4688
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
//const int MAXT = 30001;
//int n, m;
//int arr[MAXN];
//
//int sorted[MAXN];
//int bi[MAXN];
//
//int cnt[MAXN];
//bitset<MAXN> curSet;
//bool hasSet[MAXT];
//bitset<MAXN> bitSet[MAXT];
//
//Query query[MAXT * 3];
//
//int ans[MAXT];
//
//int kth(int num) {
//    int left = 1, right = n, ret = -1;
//    while (left <= right) {
//        int mid = (left + right) >> 1;
//        if (sorted[mid] >= num) {
//            ret = mid;
//            right = mid - 1;
//        } else {
//            left = mid + 1;
//        }
//    }
//    return ret;
//}
//
//bool QueryCmp(const Query &a, const Query &b) {
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
//void add(int x) {
//    cnt[x]++;
//    curSet[x + cnt[x] - 1] = 1;
//}
//
//void del(int x) {
//    cnt[x]--;
//    curSet[x + cnt[x]] = 0;
//}
//
//void compute(int q) {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= q; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
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
//        if (!hasSet[id]) {
//            hasSet[id] = true;
//            bitSet[id] = curSet;
//        } else {
//            bitSet[id] &= curSet;
//        }
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//    }
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
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
//    prepare();
//    for (int t = MAXT - 1; m > 0; m -= t) {
//        int k = min(m, t);
//        memset(cnt, 0, sizeof(int) * (n + 2));
//        memset(hasSet, 0, sizeof(bool) * (k + 2));
//        memset(ans, 0, sizeof(int) * (k + 2));
//        curSet.reset();
//        int cntq = 0, l, r;
//        for (int i = 1; i <= k; i++) {
//            for (int j = 1; j <= 3; j++) {
//                cin >> l >> r;
//                query[++cntq] = { l, r, i };
//                ans[i] += r - l + 1;
//            }
//        }
//        sort(query + 1, query + cntq + 1, QueryCmp);
//        compute(cntq);
//        for (int i = 1; i <= k; i++) {
//            ans[i] -= bitSet[i].count() * 3;
//        }
//        for (int i = 1; i <= k; i++) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}