package class175;

// 直到倒下，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF786C
// 测试链接 : https://codeforces.com/problemset/problem/786/C
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, blen = 1000;
//int arr[MAXN];
//int num[MAXN];
//int ans[MAXN];
//
//int query(int x) {
//    memset(num, 0, sizeof(num));
//    int cnt = 0, lst = 1, tot = 0;
//    for (int i = 1; i <= n; i++) {
//        if (num[arr[i]] == 0) {
//            cnt++;
//            num[arr[i]] = 1;
//        }
//        if (cnt > x) {
//            tot++;
//            for (int j = lst; j <= i; j++) {
//                num[arr[j]] = 0;
//            }
//            lst = i;
//            num[arr[i]] = 1;
//            cnt = 1;
//        }
//    }
//    if (cnt > 0) {
//        tot++;
//    }
//    return tot;
//}
//
//void compute() {
//    fill(ans + 1, ans + n + 1, -1);
//    for (int i = 1; i <= blen; i++) {
//        ans[i] = query(i);
//    }
//    for (int i = blen + 1; i <= n;) {
//        ans[i] = query(i);
//        int l = i, r = n, find = i;
//        while (l <= r) {
//            int mid = (l + r) >> 1;
//            if (query(mid) >= ans[i]) {
//                find = mid;
//                l = mid + 1;
//            } else {
//                r = mid - 1;
//            }
//        }
//        i = find + 1;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
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