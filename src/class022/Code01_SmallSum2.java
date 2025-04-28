package class022;

// 小和问题，C++版
// 测试链接 : https://www.nowcoder.com/practice/edfe05a1d45c4ea89101d936cac32469
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n;
//
//int arr[MAXN];
//int help[MAXN];
//
//long long merge(int l, int m, int r) {
//    long long ans = 0;
//    for (int j = m + 1, i = l, sum = 0; j <= r; j++) {
//        while (i <= m && arr[i] <= arr[j]) {
//            sum += arr[i++];
//        }
//        ans += sum;
//    }
//    int i = l, a = l, b = m + 1;
//    while (a <= m && b <= r) {
//        help[i++] = (arr[a] <= arr[b] ? arr[a++] : arr[b++]);
//    }
//    while (a <= m) {
//        help[i++] = arr[a++];
//    }
//    while (b <= r) {
//        help[i++] = arr[b++];
//    }
//    for (i = l; i <= r; i++) {
//        arr[i] = help[i];
//    }
//    return ans;
//}
//
//long long smallSum(int l, int r) {
//    if (l == r) {
//        return 0;
//    }
//    int m = (l + r) >> 1;
//    return smallSum(l, m) + smallSum(m + 1, r) + merge(l, m, r);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    while (cin >> n) {
//        for (int i = 0; i < n; i++) {
//            cin >> arr[i];
//        }
//        cout << smallSum(0, n - 1) << '\n';
//    }
//    return 0;
//}