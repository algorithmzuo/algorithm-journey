package class177;

// 累加和为0的最长子数组，C++版
// 给定一个长度为n的数组arr，其中只有1和-1两种值
// 一共有m条查询，格式 l r : 打印arr[l..r]范围上，累加和为0的最长子数组长度
// 1 <= n、m <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/SP20644
// 测试链接 : https://www.spoj.com/problems/ZQUERY/
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
//const int MAXN = 50002;
//const int MAXB = 301;
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
//int first[MAXN];
//int mostRight[MAXN];
//int maxDist;
// 
//int ans[MAXN];
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
//int force(int l, int r) {
//    int ret = 0;
//    for (int i = l; i <= r; i++) {
//        if (first[arr[i]] == 0) {
//            first[arr[i]] = i;
//        } else {
//            ret = max(ret, i - first[arr[i]]);
//        }
//    }
//    for (int i = l; i <= r; i++) {
//        first[arr[i]] = 0;
//    }
//    return ret;
//}
//
//void addRight(int idx) {
//    int num = arr[idx];
//    mostRight[num] = idx;
//    if (first[num] == 0) {
//        first[num] = idx;
//    }
//    maxDist = max(maxDist, idx - first[num]);
//}
//
//void addLeft(int idx) {
//    int num = arr[idx];
//    if (mostRight[num] != 0) {
//        maxDist = max(maxDist, mostRight[num] - idx);
//    } else {
//        mostRight[num] = idx;
//    }
//}
//
//void delLeft(int idx) {
//    int num = arr[idx];
//    if (mostRight[num] == idx) {
//        mostRight[num] = 0;
//    }
//}
//
//void compute() {
//    for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
//        maxDist = 0;
//        fill(first + 1, first + cntv + 1, 0);
//        fill(mostRight + 1, mostRight + cntv + 1, 0);
//        int winl = br[block] + 1, winr = br[block];
//        for (; qi <= m && bi[query[qi].l] == block; qi++) {
//            int jobl = query[qi].l;
//            int jobr = query[qi].r;
//            int id = query[qi].id;
//            if (jobr <= br[block]) {
//                ans[id] = force(jobl, jobr);
//            } else {
//                while (winr < jobr) {
//                    addRight(++winr);
//                }
//                int backup = maxDist;
//                while (winl > jobl) {
//                    addLeft(--winl);
//                }
//                ans[id] = maxDist;
//                maxDist = backup;
//                while (winl <= br[block]) {
//                    delLeft(winl++);
//                }
//            }
//        }
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        arr[i] += arr[i - 1];
//    }
//    for (int i = n; i >= 0; i--) {
//        arr[i + 1] = arr[i];
//    }
//    n++;
//    for (int i = 1; i <= m; i++) {
//        query[i].r++;
//    }
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    cntv = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[cntv] != sorted[i]) {
//            sorted[++cntv] = sorted[i];
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