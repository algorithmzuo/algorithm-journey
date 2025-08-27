package class177;

// 达到阈值的最小众数，C++版
// 测试链接 : https://leetcode.cn/problems/threshold-majority-queries/
// 提交以下全部代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, k, id;
//};
//
//const int MAXN = 10001;
//const int MAXM = 50001;
//const int MAXB = 301;
//
//int n, m;
//int arr[MAXN];
//Query query[MAXM];
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
//int maxCnt;
//int numAns;
//int ans[MAXM];
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
//int force(int l, int r, int k) {
//    int mx = 0, who = 0;
//    for (int i = l; i <= r; i++) {
//        forceCnt[arr[i]]++;
//    }
//    for (int i = l; i <= r; i++) {
//        int num = arr[i];
//        if (forceCnt[num] > mx || (forceCnt[num] == mx && num < who)) {
//            mx = forceCnt[num];
//            who = num;
//        }
//    }
//    for (int i = l; i <= r; i++) {
//        forceCnt[arr[i]]--;
//    }
//    return mx >= k ? sorted[who] : -1;
//}
//
//void add(int num) {
//    cnt[num]++;
//    if (cnt[num] > maxCnt || (cnt[num] == maxCnt && num < numAns)) {
//        maxCnt = cnt[num];
//        numAns = num;
//    }
//}
//
//void undo(int num) {
//    cnt[num]--;
//}
//
//void compute() {
//    for (int block = 1, qi = 1; block <= bnum && qi <= m; block++) {
//        maxCnt = 0;
//        numAns = 0;
//        fill(cnt + 1, cnt + cntv + 1, 0);
//        int winl = br[block] + 1, winr = br[block];
//        for (; qi <= m && bi[query[qi].l] == block; qi++) {
//            int jobl = query[qi].l;
//            int jobr = query[qi].r;
//            int jobk = query[qi].k;
//            int id = query[qi].id;
//            if (jobr <= br[block]) {
//                ans[id] = force(jobl, jobr, jobk);
//            } else {
//                while (winr < jobr) {
//                    add(arr[++winr]);
//                }
//                int backupCnt = maxCnt;
//                int backupNum = numAns;
//                while (winl > jobl) {
//                    add(arr[--winl]);
//                }
//                if (maxCnt >= jobk) {
//                    ans[id] = sorted[numAns];
//                } else {
//                	ans[id] = -1;
//                }
//                maxCnt = backupCnt;
//                numAns = backupNum;
//                while (winl <= br[block]) {
//                    undo(arr[winl++]);
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
//class Solution {
//public:
//    vector<int> subarrayMajority(vector<int>& nums, vector<vector<int>>& queries) {
//        n = (int)nums.size();
//        m = (int)queries.size();
//        for (int i = 1; i <= n; i++) {
//            arr[i] = nums[i - 1];
//        }
//        for (int i = 1; i <= m; i++) {
//            query[i].l = queries[i - 1][0] + 1;
//            query[i].r = queries[i - 1][1] + 1;
//            query[i].k = queries[i - 1][2];
//            query[i].id = i;
//        }
//        prepare();
//        compute();
//        vector<int> ret(m);
//        for (int i = 1; i <= m; i++) {
//            ret[i - 1] = ans[i];
//        }
//        return ret;
//    }
//};