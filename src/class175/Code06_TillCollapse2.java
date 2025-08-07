package class175;

// 最少划分，C++版
// 给定一个长度为n的数组arr，考虑如下问题的解
// 数组arr划分成若干段子数组，保证每段不同数字的种类 <= k，返回至少划分成几段
// 打印k = 1, 2, 3..n时，所有的答案
// 1 <= arr[i] <= n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF786C
// 测试链接 : https://codeforces.com/problemset/problem/786/C
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, blen;
//int arr[MAXN];
//bool vis[MAXN];
//int ans[MAXN];
//
//int query(int limit) {
//    int kind = 0, cnt = 0, start = 1;
//    for (int i = 1; i <= n; i++) {
//        if (!vis[arr[i]]) {
//            kind++;
//            if (kind > limit) {
//                cnt++;
//                for (int j = start; j < i; j++) {
//                    vis[arr[j]] = false;
//                }
//                start = i;
//                kind = 1;
//            }
//            vis[arr[i]] = true;
//        }
//    }
//    if (kind > 0) {
//        cnt++;
//        for (int j = start; j <= n; j++) {
//            vis[arr[j]] = false;
//        }
//    }
//    return cnt;
//}
//
//int jump(int l, int r, int curAns) {
//    int find = l;
//    while (l <= r) {
//        int mid = (l + r) >> 1;
//        int check = query(mid);
//        if (check < curAns) {
//            r = mid - 1;
//        } else if (check > curAns) {
//            l = mid + 1;
//        } else {
//            find = mid;
//            l = mid + 1;
//        }
//    }
//    return find + 1;
//}
//
//void compute() {
//    for (int i = 1; i <= blen; i++) {
//        ans[i] = query(i);
//    }
//    for (int i = blen + 1; i <= n; i = jump(i, n, ans[i])) {
//        ans[i] = query(i);
//    }
//}
//
//void prepare() {
//    blen = max(1, (int)sqrt(n * log2(n)));
//    fill(ans + 1, ans + n + 1, -1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == -1) {
//            ans[i] = ans[i - 1];
//        }
//        cout << ans[i] << ' ';
//    }
//    cout << '\n';
//    return 0;
//}