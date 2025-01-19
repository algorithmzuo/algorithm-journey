package class159;

// 最大异或和，C++版
// 因为练的就是可持久化前缀树，所以就用在线算法
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
//int nxt[MAXT][2];
//int pass[MAXT];
//int cnt = 0;
//
//int insert(int num, int bit, int i) {
//    int rt = ++cnt;
//    nxt[rt][0] = nxt[i][0];
//    nxt[rt][1] = nxt[i][1];
//    pass[rt] = pass[i] + 1;
//    if (bit >= 0) {
//        int cur = (num >> bit) & 1;
//        nxt[rt][cur] = insert(num, bit - 1, nxt[rt][cur]);
//    }
//    return rt;
//}
//
//int query(int num, int bit, int u, int v) {
//    if (bit < 0) {
//        return 0;
//    }
//    int cur = (num >> bit) & 1;
//    int opp = cur ^ 1;
//    if (pass[nxt[v][opp]] > pass[nxt[u][opp]]) {
//        return (1 << bit) + query(num, bit - 1, nxt[u][opp], nxt[v][opp]);
//    } else {
//        return query(num, bit - 1, nxt[u][cur], nxt[v][cur]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(0);
//    cin >> n >> m;
//    eor = 0;
//    root[0] = insert(eor, BIT, 0);
//    for (int i = 1, num; i <= n; i++) {
//        cin >> num;
//        eor ^= num;
//        root[i] = insert(eor, BIT, root[i - 1]);
//    }
//    string op;
//    int x, y, z;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == "A") {
//            cin >> x;
//            eor ^= x;
//            n++;
//            root[n] = insert(eor, BIT, root[n - 1]);
//        } else {
//            cin >> x >> y >> z;
//            if (x == 1) {
//                cout << query(eor ^ z, BIT, 0, root[y - 1]) << "\n";
//            } else {
//                cout << query(eor ^ z, BIT, root[x - 2], root[y - 1]) << "\n";
//            }
//        }
//    }
//    return 0;
//}