package class159;

// 最大异或和，C++版
// 非负序列arr的初始长度为n，一共有m条操作，每条操作是如下两种类型中的一种
// A x     : arr的末尾增加数字x，arr的长度n也增加1
// Q l r x : l~r这些位置中，选一个位置p，现在希望
//           arr[p] ^ arr[p+1] ^ .. ^ arr[n] ^ x 这个值最大
//           打印这个最大值
// 1 <= n、m <= 3 * 10^5
// 0 <= arr[i]、x <= 10^7
// 因为练的就是可持久化前缀树，所以就用在线算法，不要使用离线算法
// 测试链接 : https://www.luogu.com.cn/problem/P4735
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 600001;
//const int MAXT = MAXN * 22;
//const int BIT = 25;
//int n, m, eor;
//int root[MAXN];
//int tree[MAXT][2];
//int pass[MAXT];
//int cnt = 0;
//
//int insert(int num, int i) {
//    int rt = ++cnt;
//    tree[rt][0] = tree[i][0];
//    tree[rt][1] = tree[i][1];
//    pass[rt] = pass[i] + 1;
//    for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
//        path = (num >> b) & 1;
//        i = tree[i][path];
//        cur = ++cnt;
//        tree[cur][0] = tree[i][0];
//        tree[cur][1] = tree[i][1];
//        pass[cur] = pass[i] + 1;
//        tree[pre][path] = cur;
//    }
//    return rt;
//}
//
//int query(int num, int u, int v) {
//    int ans = 0;
//    for (int b = BIT, path, best; b >= 0; b--) {
//        path = (num >> b) & 1;
//        best = path ^ 1;
//        if (pass[tree[v][best]] > pass[tree[u][best]]) {
//            ans += 1 << b;
//            u = tree[u][best];
//            v = tree[v][best];
//        } else {
//            u = tree[u][path];
//            v = tree[v][path];
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(0);
//    cin >> n >> m;
//    eor = 0;
//    root[0] = insert(eor, 0);
//    for (int i = 1, num; i <= n; i++) {
//        cin >> num;
//        eor ^= num;
//        root[i] = insert(eor, root[i - 1]);
//    }
//    string op;
//    int x, y, z;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == "A") {
//            cin >> x;
//            eor ^= x;
//            n++;
//            root[n] = insert(eor, root[n - 1]);
//        } else {
//            cin >> x >> y >> z;
//            if (x == 1) {
//                cout << query(eor ^ z, 0, root[y - 1]) << "\n";
//            } else {
//                cout << query(eor ^ z, root[x - 2], root[y - 1]) << "\n";
//            }
//        }
//    }
//    return 0;
//}