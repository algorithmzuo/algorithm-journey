package class188;

// 词链，C++版
// 给定n个由小写字母组成的单词，想把所有单词串成一个词链
// 同时要求前一个单词的结尾字符 == 后一个单词的开头字符
// 比如词链，aloha.arachnid.dog.gopher.rat.tiger
// 需要使用每个输入的单词恰好一次，同一单词出现k次就要用k次
// 返回字典序最小的结果，注意 . 的字典序 < 小写字母的字典序
// 如果不存在合法词链，输出***
// 1 <= n <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P1127
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 27;
//const int MAXM = 1002;
//int n = 26, m;
//
//int a[MAXM];
//int b[MAXM];
//string w[MAXM];
//int eidArr[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int edgeid[MAXM];
//int cntg;
//
//int cur[MAXN];
//int outDeg[MAXN];
//int inDeg[MAXN];
//
//int path[MAXM];
//int cntp;
//
//bool EdgeCmp(int i, int j) {
//    if (a[i] != a[j]) {
//        return a[i] < a[j];
//    }
//    return w[i] < w[j];
//}
//
//void addEdge(int u, int v, int eid) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    edgeid[cntg] = eid;
//    head[u] = cntg;
//}
//
//int startNode(const string &str) {
//    return (int)(str[0] - 'a' + 1);
//}
//
//int endNode(const string &str) {
//    return (int)(str[(int)str.size() - 1] - 'a' + 1);
//}
//
//void connect() {
//    sort(eidArr + 1, eidArr + m + 1, EdgeCmp);
//    for (int l = 1, r = 1; l <= m; l = ++r) {
//        while (r + 1 <= m && a[eidArr[l]] == a[eidArr[r + 1]]) {
//            r++;
//        }
//        for (int i = r, u, v; i >= l; i--) {
//            u = a[eidArr[i]];
//            v = b[eidArr[i]];
//            outDeg[u]++;
//            inDeg[v]++;
//            addEdge(u, v, eidArr[i]);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        cur[i] = head[i];
//    }
//}
//
//int directedStart() {
//    int start = -1, end = -1;
//    for (int i = 1; i <= n; i++) {
//        int v = outDeg[i] - inDeg[i];
//        if (v < -1 || v > 1 || (v == 1 && start != -1) || (v == -1 && end != -1)) {
//            return -1;
//        }
//        if (v == 1) {
//            start = i;
//        }
//        if (v == -1) {
//            end = i;
//        }
//    }
//    if ((start == -1) ^ (end == -1)) {
//        return -1;
//    }
//    if (start != -1) {
//        return start;
//    }
//    for (int i = 1; i <= n; i++) {
//        if (outDeg[i] > 0) {
//            return i;
//        }
//    }
//    return -1;
//}
//
//void euler(int u, int eid) {
//    for (int e = cur[u]; e > 0; e = cur[u]) {
//        cur[u] = nxt[e];
//        euler(to[e], edgeid[e]);
//    }
//    path[++cntp] = eid;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> m;
//    string str;
//    for (int i = 1; i <= m; i++) {
//        cin >> str;
//        a[i] = startNode(str);
//        b[i] = endNode(str);
//        w[i] = str;
//        eidArr[i] = i;
//    }
//    connect();
//    int start = directedStart();
//    if (start == -1) {
//        cout << "***\n";
//    } else {
//        euler(start, 0);
//        if (cntp != m + 1) {
//            cout << "***\n";
//        } else {
//            cout << w[path[cntp - 1]];
//            for (int i = cntp - 2; i >= 1; i--) {
//                cout << "." << w[path[i]];
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}