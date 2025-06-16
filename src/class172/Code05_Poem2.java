package class172;

// 作诗，C++版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上，有多少个数出现正偶数次
// 1 <= 所有数值 <= 10^5
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P4135
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 401;
//int n, c, m;
//int arr[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int freq[MAXB][MAXN];
//int even[MAXB][MAXB];
//int numCnt[MAXN];
//
//int getCnt(int l, int r, int v) {
//    return freq[r][v] - freq[l - 1][v];
//}
//
//int delta(int pre) {
//    return pre ? ((pre & 1) ? 1 : -1) : 0;
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
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = bl[i]; j <= br[i]; j++) {
//            freq[i][arr[j]]++;
//        }
//        for (int j = 1; j <= c; j++) {
//            freq[i][j] += freq[i - 1][j];
//        }
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = i; j <= bnum; j++) {
//            even[i][j] = even[i][j - 1];
//            for (int k = bl[j]; k <= br[j]; k++) {
//                even[i][j] += delta(numCnt[arr[k]]);
//                numCnt[arr[k]]++;
//            }
//        }
//        for (int j = 1; j <= c; j++) {
//            numCnt[j] = 0;
//        }
//    }
//}
//
//int query(int l, int r) {
//    int ans = 0;
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            ans += delta(numCnt[arr[i]]);
//            numCnt[arr[i]]++;
//        }
//        for (int i = l; i <= r; i++) {
//            numCnt[arr[i]] = 0;
//        }
//    } else {
//        ans = even[bi[l] + 1][bi[r] - 1];
//        for (int i = l; i <= br[bi[l]]; i++) {
//            ans += delta(getCnt(bi[l] + 1, bi[r] - 1, arr[i]) + numCnt[arr[i]]);
//            numCnt[arr[i]]++;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            ans += delta(getCnt(bi[l] + 1, bi[r] - 1, arr[i]) + numCnt[arr[i]]);
//            numCnt[arr[i]]++;
//        }
//        for (int i = l; i <= br[bi[l]]; i++) {
//            numCnt[arr[i]] = 0;
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            numCnt[arr[i]] = 0;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> c >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    prepare();
//    for (int i = 1, a, b, l, r, lastAns = 0; i <= m; i++) {
//        cin >> a >> b;
//        a = (a + lastAns) % n + 1;
//        b = (b + lastAns) % n + 1;
//        l = min(a, b);
//        r = max(a, b);
//        lastAns = query(l, r);
//        cout << lastAns << '\n';
//    }
//    return 0;
//}