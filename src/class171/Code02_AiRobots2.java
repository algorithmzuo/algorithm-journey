package class171;

// 机器人聊天对，C++版
// 一共有n个机器人，给定一个整数k，每个机器人给定，位置x、视野y、智商q
// 第i个机器人可以看见的范围是[xi − yi, xi + yi]
// 如果两个机器人相互之间可以看见，并且智商差距不大于k，那么它们会开始聊天
// 打印有多少对机器人可以聊天
// 1 <= n <= 10^5
// 0 <= k <= 20
// 0 <= x、y、q <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF1045G
// 测试链接 : https://codeforces.com/problemset/problem/1045/G
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int x, y, q, l, r;
//};
//
//bool CmpY(Node a, Node b) {
//    return a.y > b.y;
//}
//
//bool CmpQ(Node a, Node b) {
//    return a.q < b.q;
//}
//
//const int MAXN = 100001;
//int n, k, s;
//
//Node arr[MAXN];
//int x[MAXN];
//int tree[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= s) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//int query(int l, int r) {
//    return sum(r) - sum(l - 1);
//}
//
//long long merge(int l, int m, int r) {
//    int winl = l, winr = l - 1;
//    long long ans = 0;
//    for (int i = m + 1; i <= r; i++) {
//        while (winl <= m && arr[winl].q < arr[i].q - k) {
//            add(arr[winl].x, -1);
//            winl++;
//        }
//        while (winr + 1 <= m && arr[winr + 1].q <= arr[i].q + k) {
//            winr++;
//            add(arr[winr].x, 1);
//        }
//        ans += query(arr[i].l, arr[i].r);
//    }
//    for (int i = winl; i <= winr; i++) {
//        add(arr[i].x, -1);
//    }
//    sort(arr + l, arr + r + 1, CmpQ);
//    return ans;
//}
//
//long long cdq(int l, int r) {
//    if (l == r) {
//        return 0;
//    }
//    int mid = (l + r) / 2;
//    return cdq(l, mid) + cdq(mid + 1, r) + merge(l, mid, r);
//}
//
//int lower(int num) {
//    int l = 1, r = s, m, ans = 1;
//    while (l <= r) {
//        m = (l + r) / 2;
//        if (x[m] >= num) {
//            ans = m;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return ans;
//}
//
//int upper(int num) {
//    int l = 1, r = s, m, ans = s + 1;
//    while (l <= r) {
//        m = (l + r) / 2;
//        if (x[m] > num) {
//            ans = m;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        x[i] = arr[i].x;
//    }
//    sort(x + 1, x + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (x[s] != x[i]) {
//            x[++s] = x[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i].l = lower(arr[i].x - arr[i].y);
//        arr[i].r = upper(arr[i].x + arr[i].y) - 1;
//        arr[i].x = lower(arr[i].x);
//    }
//    sort(arr + 1, arr + n + 1, CmpY);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> k;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].q;
//    }
//    prepare();
//    cout << cdq(1, n) << '\n';
//    return 0;
//}