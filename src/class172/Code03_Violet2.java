package class172;

// 蒲公英，C++版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上的众数，如果有多个众数，打印值最小的
// 1 <= n <= 4 * 10^4
// 1 <= m <= 5 * 10^4
// 1 <= 数组中的值 <= 10^9
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P4168
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 40001;
//const int MAXB = 201;
//int n, m, s;
//int arr[MAXN];
//int sortv[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int freq[MAXB][MAXN];
//int mode[MAXB][MAXB];
//int numCnt[MAXN];
//
//int lower(int num) {
//    int l = 1, r = s, m, ans = 0;
//    while (l <= r) {
//        m = (l + r) >> 1;
//        if (sortv[m] >= num) {
//            ans = m;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return ans;
//}
//
//int getCnt(int l, int r, int v) {
//    return freq[r][v] - freq[l - 1][v];
//}
//
//void prepare() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//    }
//    for (int i = 1; i <= n; i++) {
//        sortv[i] = arr[i];
//    }
//    sort(sortv + 1, sortv + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sortv[s] != sortv[i]) {
//            sortv[++s] = sortv[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = lower(arr[i]);
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = bl[i]; j <= br[i]; j++) {
//            freq[i][arr[j]]++;
//        }
//        for (int j = 1; j <= s; j++) {
//            freq[i][j] += freq[i - 1][j];
//        }
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = i; j <= bnum; j++) {
//            int most = mode[i][j - 1];
//            int mostCnt = getCnt(i, j, most);
//            for (int k = bl[j]; k <= br[j]; k++) {
//                int cur = arr[k];
//                int curCnt = getCnt(i, j, cur);
//                if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
//                    most = cur;
//                    mostCnt = curCnt;
//                }
//            }
//            mode[i][j] = most;
//        }
//    }
//}
//
//int query(int l, int r) {
//    int most = 0;
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            numCnt[arr[i]]++;
//        }
//        for (int i = l; i <= r; i++) {
//            if (numCnt[arr[i]] > numCnt[most] || (numCnt[arr[i]] == numCnt[most] && arr[i] < most)) {
//                most = arr[i];
//            }
//        }
//        for (int i = l; i <= r; i++) {
//            numCnt[arr[i]] = 0;
//        }
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//            numCnt[arr[i]]++;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            numCnt[arr[i]]++;
//        }
//        most = mode[bi[l] + 1][bi[r] - 1];
//        int mostCnt = getCnt(bi[l] + 1, bi[r] - 1, most) + numCnt[most];
//        for (int i = l; i <= br[bi[l]]; i++) {
//            int cur = arr[i];
//            int curCnt = getCnt(bi[l] + 1, bi[r] - 1, cur) + numCnt[cur];
//            if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
//                most = cur;
//                mostCnt = curCnt;
//            }
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            int cur = arr[i];
//            int curCnt = getCnt(bi[l] + 1, bi[r] - 1, cur) + numCnt[cur];
//            if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
//                most = cur;
//                mostCnt = curCnt;
//            }
//        }
//        for (int i = l; i <= br[bi[l]]; i++) {
//            numCnt[arr[i]] = 0;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            numCnt[arr[i]] = 0;
//        }
//    }
//    return sortv[most];
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
//    int lastAns = 0, a, b, l, r;
//    for (int i = 1; i <= m; i++) {
//        cin >> a >> b;
//        a = (a + lastAns - 1) % n + 1;
//        b = (b + lastAns - 1) % n + 1;
//        l = min(a, b);
//        r = max(a, b);
//        lastAns = query(l, r);
//        cout << lastAns << '\n';
//    }
//    return 0;
//}