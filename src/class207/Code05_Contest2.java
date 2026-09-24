package class207;

// 确定能力，C++版
// 一共n头奶牛，编号为1~n，每头奶牛的能力互不相同
// 给定m条已知关系，格式 a b，表示a的能力强于b
// 能力关系具有传递性，如果a强于b，b强于c，那么a强于c
// 题目保证没有矛盾，根据已知关系，希望确定奶牛的排名
// 计算有多少头奶牛的具体名次已经能够确定，打印这个数量
// 1 <= n <= 100
// 1 <= m <= 4500
// 测试链接 : https://www.luogu.com.cn/problem/P2419
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 101;
//int n, m;
//
//bitset<MAXN> dp[MAXN];
//int cnt[MAXN];
//
//void floyd() {
//    for (int bridge = 1; bridge <= n; bridge++) {
//        for (int i = 1; i <= n; i++) {
//            if (dp[i][bridge]) {
//                dp[i] |= dp[bridge];
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, a, b; i <= m; i++) {
//        cin >> a >> b;
//        dp[a][b] = true;
//    }
//    floyd();
//    for (int i = 1; i <= n; i++) {
//        for (int j = i + 1; j <= n; j++) {
//            if (dp[i][j] || dp[j][i]) {
//                cnt[i]++;
//                cnt[j]++;
//            }
//        }
//    }
//    int ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (cnt[i] == n - 1) {
//            ans++;
//        }
//    }
//    cout << ans << '\n';
//    return 0;
//}