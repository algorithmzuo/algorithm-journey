package class175;

// 初始化，C++版
// 给定一个长度为n的数组arr，接下来有m条操作，操作格式如下
// 操作 1 x y z : 从arr[y]开始，下标每次+x，所有相应位置的数都+z，题目保证 y <= x
// 操作 2 x y   : 打印arr[x..y]的累加和，答案对1000000007取余
// 1 <= n、m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5309
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXB = 2001;
//const int MOD  = 1000000007;
//int n, m;
//
//long long arr[MAXN];
//long long sum[MAXN];
//long long pre[MAXB][MAXB];
//long long suf[MAXB][MAXB];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//void add(int x, int y, long long z) {
//    if (x <= blen) {
//        for (int i = y; i <= x; i++) {
//            pre[x][i] += z;
//        }
//        for (int i = 1; i <= y; i++) {
//            suf[x][i] += z;
//        }
//    } else {
//        for (int i = y; i <= n; i += x) {
//            arr[i] += z;
//            sum[bi[i]] += z;
//        }
//    }
//}
//
//long long querySum(int l, int r) {
//    int lb = bi[l], rb = bi[r];
//    long long ans = 0;
//    if (lb == rb) {
//        for (int i = l; i <= r; i++) {
//            ans += arr[i];
//        }
//    } else {
//        for (int i = l; i <= br[lb]; i++) {
//            ans += arr[i];
//        }
//        for (int i = bl[rb]; i <= r; i++) {
//            ans += arr[i];
//        }
//        for (int b = lb + 1; b <= rb - 1; b++) {
//            ans += sum[b];
//        }
//    }
//    return ans;
//}
//
//long long query(int l, int r) {
//    long long ans = querySum(l, r);
//    for (int x = 1, lb, rb, num; x <= blen; x++) {
//        lb = (l - 1) / x + 1;
//        rb = (r - 1) / x + 1;
//        num = rb - lb - 1;
//        if (lb == rb) {
//            ans = ans + pre[x][(r - 1) % x + 1] - pre[x][(l - 1) % x];
//        } else {
//            ans = ans + suf[x][(l - 1) % x + 1] + pre[x][x] * num + pre[x][(r - 1) % x + 1];
//        }
//    }
//    return ans % MOD;
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(n / log2(n)));
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int b = 1; b <= bnum; b++) {
//        bl[b] = (b - 1) * blen + 1;
//        br[b] = min(b * blen, n);
//        for (int i = bl[b]; i <= br[b]; i++) {
//            sum[b] += arr[i];
//        }
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
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            cin >> z;
//            add(x, y, z);
//        } else {
//            cout << query(x, y) << '\n';
//        }
//    }
//    return 0;
//}