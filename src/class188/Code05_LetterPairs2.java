package class188;

// 无序字母对，C++版
// 给定n个字母对，每个字母对有两个字母，字母区分大小写
// 字母对中的两个字母可以位置颠倒，也就是字母对中的字母是不区分顺序的
// 不区分顺序的情况下，题目保证不会给定重复的字母对
// 构造一个字符串，让每个字母对都在字符串中出现
// 因为字母对内部的字母不区分顺序，所以作为相邻的两个字符出现即可
// 如果没有满足要求的字符串，打印"No Solution"
// 如果有多种方案，请输出字典序最小的方案
// 1 <= n <= 1326
// 测试链接 : https://www.luogu.com.cn/problem/P1341
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 53;
//const int MAXM = 2001;
//int n = 52, m;
//char arr[MAXM][2];
//
//int graph[MAXN][MAXN];
//int deg[MAXN];
//int cur[MAXN];
//
//int path[MAXM];
//int cntp;
//
//int getInt(char c) {
//    return c <= 'Z' ? (c - 'A' + 1) : (c - 'a' + 27);
//}
//
//char getChar(int v) {
//    return (char)(v <= 26 ? ('A' + v - 1) : ('a' + v - 27));
//}
//
//void connect() {
//    for (int i = 1, u, v; i <= m; i++) {
//        u = getInt(arr[i][0]);
//        v = getInt(arr[i][1]);
//        graph[u][v]++;
//        graph[v][u]++;
//        deg[u]++;
//        deg[v]++;
//    }
//    for (int i = 1; i <= n; i++) {
//        cur[i] = 1;
//    }
//}
//
//int undirectedStart() {
//    int odd = 0;
//    for (int i = 1; i <= n; i++) {
//        if ((deg[i] & 1) == 1) {
//            odd++;
//        }
//    }
//    if (odd != 0 && odd != 2) {
//        return -1;
//    }
//    for (int i = 1; i <= n; i++) {
//        if (odd == 0 && deg[i] > 0) {
//            return i;
//        }
//        if (odd == 2 && (deg[i] & 1) == 1) {
//            return i;
//        }
//    }
//    return -1;
//}
//
//void euler(int u) {
//    for (int v = cur[u]; v <= n; v = cur[u]) {
//        cur[u]++;
//        if (graph[u][v] > 0) {
//            graph[u][v]--;
//            graph[v][u]--;
//            euler(v);
//        }
//    }
//    path[++cntp] = u;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i][0] >> arr[i][1];
//    }
//    connect();
//    int start = undirectedStart();
//    if (start == -1) {
//        cout << "No Solution\n";
//    } else {
//        euler(start);
//        if (cntp != m + 1) {
//            cout << "No Solution\n";
//        } else {
//            for (int i = cntp; i >= 1; i--) {
//                cout << getChar(path[i]);
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}