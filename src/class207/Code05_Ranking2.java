package class207;

// 需要关系，C++版
// 一共n头奶牛，编号为1~n，每头奶牛的能力互不相同
// 给定m条已知关系，格式 a b，表示a的能力强于b
// 能力关系具有传递性，如果a强于b，b强于c，那么a强于c
// 希望确定所有奶牛能力从强到弱的完整排名，所以m条关系可能不够
// 你可以询问任意两只奶牛的强弱，所有询问必须提前准备好，不可以动态调整
// 求至少需要询问多少次，才能保证确定完整排名，打印这个数量
// 1 <= n <= 1000
// 1 <= m <= 10000
// 测试链接 : https://www.luogu.com.cn/problem/P2881
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1001;
//int n, m;
//
//bitset<MAXN> dp[MAXN];
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
//    int need = 0;
//    for (int i = 1; i <= n; i++) {
//        for (int j = i + 1; j <= n; j++) {
//            if (!dp[i][j] && !dp[j][i]) {
//                need++;
//            }
//        }
//    }
//    cout << need << '\n';
//    return 0;
//}