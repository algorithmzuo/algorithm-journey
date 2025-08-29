package class177;

// 相同数的最远距离，C++版
// 给定一个长度为n的数组arr，一共有m条查询，格式如下
// 查询 l r : 打印arr[l..r]范围上，相同的数的最远间隔距离
//            序列中两个元素的间隔距离指的是两个元素下标差的绝对值
// 1 <= n、m <= 2 * 10^5
// 1 <= arr[i] <= 2 * 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5906
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
//const int MAXB = 501;
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
//int forceEd[MAXN];
//int st[MAXN];
//int ed[MAXN];
//
//int curAns = 0;
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
//        if (forceEd[arr[i]] == 0) {
//            forceEd[arr[i]] = i;
//        } else {
//            ret = max(ret, i - forceEd[arr[i]]);
//        }
//    }
//    for (int i = l; i <= r; i++) {
//        forceEd[arr[i]] = 0;
//    }
//    return ret;
//}
//
//void addRight(int idx) {
//    int num = arr[idx];
//    ed[num] = idx;
//    if (st[num] == 0) {
//        st[num] = idx;
//    }
//    curAns = max(curAns, idx - st[num]);
//}
//
//void addLeft(int idx) {
//    int num = arr[idx];
//    if (ed[num] != 0) {
//        curAns = max(curAns, ed[num] - idx);
//    } else {
//        ed[num] = idx;
//    }
//}
//
//void delLeft(int idx) {
//    int num = arr[idx];
//    if (ed[num] == idx) {
//        ed[num] = 0;
//    }
//}
//
//void compute() {
//    for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
//        curAns = 0;
//        fill(st + 1, st + cntv + 1, 0);
//        fill(ed + 1, ed + cntv + 1, 0);
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
//                int backup = curAns;
//                while (winl > jobl) {
//                    addLeft(--winl);
//                }
//                ans[id] = curAns;
//                curAns = backup;
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
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    cin >> m;
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