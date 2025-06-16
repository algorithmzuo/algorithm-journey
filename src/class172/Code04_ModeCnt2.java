package class172;

// 空间少求众数的次数，C++版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上，众数到底出现了几次
// 1 <= 所有数值 <= 5 * 10^5
// 内存空间只有64MB，题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P5048
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int v, i;
//};
//
//bool NodeCmp(Node a, Node b) {
//    if (a.v != b.v) {
//        return a.v < b.v;
//    }
//    return a.i < b.i;
//}
//
//const int MAXN = 500001;
//const int MAXB = 801;
//int n, m;
//int arr[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//Node sortList[MAXN];
//int listIdx[MAXN];
//
//int modeCnt[MAXB][MAXB];
//int numCnt[MAXN];
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
//        sortList[i].v = arr[i];
//        sortList[i].i = i;
//    }
//    sort(sortList + 1, sortList + n + 1, NodeCmp);
//    for (int i = 1; i <= n; i++) {
//        listIdx[sortList[i].i] = i;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = i; j <= bnum; j++) {
//            int cnt = modeCnt[i][j - 1];
//            for (int k = bl[j]; k <= br[j]; k++) {
//                cnt = max(cnt, ++numCnt[arr[k]]);
//            }
//            modeCnt[i][j] = cnt;
//        }
//        for (int j = 1; j <= n; j++) {
//            numCnt[j] = 0;
//        }
//    }
//}
//
//int query(int l, int r) {
//    int ans = 0;
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            ans = max(ans, ++numCnt[arr[i]]);
//        }
//        for (int i = l; i <= r; i++) {
//            numCnt[arr[i]] = 0;
//        }
//    } else {
//        ans = modeCnt[bi[l] + 1][bi[r] - 1];
//        for (int i = l, idx; i <= br[bi[l]]; i++) {
//            idx = listIdx[i];
//            while (idx + ans <= n && sortList[idx + ans].v == arr[i] && sortList[idx + ans].i <= r) {
//                ans++;
//            }
//        }
//        for (int i = bl[bi[r]], idx; i <= r; i++) {
//            idx = listIdx[i];
//            while (idx - ans >= 1 && sortList[idx - ans].v == arr[i] && sortList[idx - ans].i >= l) {
//                ans++;
//            }
//        }
//    }
//    return ans;
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
//    for (int i = 1, l, r, lastAns = 0; i <= m; i++) {
//        cin >> l >> r;
//        l ^= lastAns;
//        r ^= lastAns;
//        lastAns = query(l, r);
//        cout << lastAns << '\n';
//    }
//    return 0;
//}