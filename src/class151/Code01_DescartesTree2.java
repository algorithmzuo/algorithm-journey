package class151;

// 笛卡尔树模版(C++版)
// 给定一个长度为n的数组arr，下标从1开始
// 构建一棵二叉树，下标按照搜索二叉树组织，值按照小根堆组织
// 建树的过程要求时间复杂度O(n)
// 建树之后，为了验证
// 打印，i * (left[i] + 1)，所有信息异或起来的值
// 打印，i * (right[i] + 1)，所有信息异或起来的值
// 1 <= n <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P5854
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <vector>
//#include <stack>
//#include <cstdio>
//
//#define LL long long
//
//using namespace std;
//
//const int MAXN = 10000001;
//
//int arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sta[MAXN];
//int n;
//
//void build() {
//    int top = 0;
//    for (int i = 1; i <= n; i++) {
//        int pos = top;
//        while (pos > 0 && arr[sta[pos]] > arr[i]) {
//            pos--;
//        }
//        if (pos > 0) {
//            rs[sta[pos]] = i;
//        }
//        if (pos < top) {
//            ls[i] = sta[pos + 1];
//        }
//        sta[++pos] = i;
//        top = pos;
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
//    build();
//    long long ans1 = 0, ans2 = 0;
//    for (int i = 1; i <= n; i++) {
//        ans1 ^= 1LL * i * (ls[i] + 1);
//        ans2 ^= 1LL * i * (rs[i] + 1);
//    }
//    cout << ans1 << " " << ans2 << endl;
//    return 0;
//}