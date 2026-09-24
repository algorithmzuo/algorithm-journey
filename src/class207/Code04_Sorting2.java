package class207;

// 排序，C++版
// 一共n个元素，用大写字母 A、B、C... 表示，元素的值互不相同
// 给定m条大小关系，格式为 A<B，表示A的值小于B，大小关系具有传递性
// 按输入顺序逐条考虑这些关系，判断以下情况
// 如果首次在前k条关系后，已经能唯一确定完整排名，输出k和升序序列，然后结束
// 输出 Sorted sequence determined after {k} relations: {升序序列}.
// 如果首次在前k条关系后，发现了矛盾，那么停止判断，不再考虑后续的关系
// 输出 Inconsistency found after {k} relations.
// 如果考察完所有m条关系，仍然没有矛盾，但无法唯一确定完整排名
// 输出 Sorted sequence cannot be determined.
// 2 <= n <= 26    1 <= m <= 600
// 测试链接 : https://www.luogu.com.cn/problem/P1347
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 27;
//const int MAXM = 601;
//int n, m;
//
//int a[MAXM];
//int b[MAXM];
//
//bitset<MAXN> dp[MAXN];
//
//int ans;
//int kth;
//char order[MAXN];
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
//int check() {
//    for (int i = 1; i <= n; i++) {
//        if (dp[i][i]) {
//            return 2;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        for (int j = i + 1; j <= n; j++) {
//            if (!dp[i][j] && !dp[j][i]) {
//                return 0;
//            }
//        }
//    }
//    return 1;
//}
//
//void compute() {
//    ans = 0;
//    for (int i = 1; i <= m; i++) {
//        dp[a[i]][b[i]] = true;
//        floyd();
//        ans = check();
//        if (ans == 1 || ans == 2) {
//            kth = i;
//            break;
//        }
//    }
//    if (ans == 1) {
//        for (int i = 1; i <= n; i++) {
//            order[n - dp[i].count()] = (char) ('A' + i - 1);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    char s1, s2, tmp;
//    for (int i = 1; i <= m; i++) {
//        cin >> s1 >> tmp >> s2;
//        a[i] = s1 - 'A' + 1;
//        b[i] = s2 - 'A' + 1;
//    }
//    compute();
//    if (ans == 0) {
//        cout << "Sorted sequence cannot be determined." << '\n';
//    } else if (ans == 1) {
//        cout << "Sorted sequence determined after " << kth << " relations: ";
//        for (int i = 1; i <= n; i++) {
//            cout << order[i];
//        }
//        cout << "." << '\n';
//    } else {
//        cout << "Inconsistency found after " << kth << " relations." << '\n';
//    }
//    return 0;
//}