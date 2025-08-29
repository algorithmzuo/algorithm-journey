package class177;

// 只增回滚莫队入门题，C++版
// 给定一个长度为n的数组arr，下面定义重要度的概念
// 如果一段范围上，数字x出现c次，那么这个数字的重要度为x * c
// 范围上的最大重要度，就是该范围上，每种数字的重要度，取最大值
// 一共有m条查询，格式 l r : 打印arr[l..r]范围上的最大重要度
// 1 <= n、m <= 10^5
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/AT_joisc2014_c
// 测试链接 : https://atcoder.jp/contests/joisc2014/tasks/joisc2014_c
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
//const int MAXB = 401;
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//int sorted[MAXN];
//int cntv;
//
//int blen, bnum;
//int bi[MAXN];
//int br[MAXB];
//
//int forceCnt[MAXN];
//int cnt[MAXN];
//
//long long curAns = 0;
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//int kth(int num) {
//    int left = 1, right = cntv, ret = 0;
//    while (left <= right) {
//        int mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//            ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//long long force(int l, int r) {
//    long long ret = 0;
//    for (int i = l; i <= r; i++) {
//        forceCnt[arr[i]]++;
//    }
//    for (int i = l; i <= r; i++) {
//        ret = max(ret, 1LL * forceCnt[arr[i]] * sorted[arr[i]]);
//    }
//    for (int i = l; i <= r; i++) {
//        forceCnt[arr[i]]--;
//    }
//    return ret;
//}
//
//void add(int num) {
//    cnt[num]++;
//    curAns = max(curAns, 1LL * cnt[num] * sorted[num]);
//}
//
//void del(int num) {
//    cnt[num]--;
//}
//
//void compute() {
//    for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
//        curAns = 0;
//        fill(cnt + 1, cnt + cntv + 1, 0);
//        int winl = br[block] + 1, winr = br[block];
//        for (; qi <= m && bi[query[qi].l] == block; qi++) {
//            int jobl = query[qi].l;
//            int jobr = query[qi].r;
//            int id = query[qi].id;
//            if (jobr <= br[block]) {
//                ans[id] = force(jobl, jobr);
//            } else {
//                while (winr < jobr) {
//                    add(arr[++winr]);
//                }
//                long long backup = curAns;
//                while (winl > jobl) {
//                    add(arr[--winl]);
//                }
//                ans[id] = curAns;
//                curAns = backup;
//                while (winl <= br[block]) {
//                    del(arr[winl++]);
//                }
//            }
//        }
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//    	sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    cntv = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[cntv] != sorted[i]) {
//        	sorted[++cntv] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//    }
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        br[i] = min(i * blen, n);
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
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
//        cin >> query[i].l >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}