package class188;

// 有向图的欧拉路径，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P7771
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v;
//};
//
//bool EdgeCmp(Edge e1, Edge e2) {
//    if (e1.u != e2.u) {
//        return e1.u < e2.u;
//    }
//    return e1.v < e2.v;
//}
//
//const int MAXN = 100001;
//const int MAXM = 200002;
//int n, m;
//Edge edgeArr[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int cntg;
//
//int cur[MAXN];
//int outDeg[MAXN];
//int inDeg[MAXN];
//
//int path[MAXM];
//int cntp;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void connect() {
//    sort(edgeArr + 1, edgeArr + m + 1, EdgeCmp);
//    for (int l = 1, r = 1; l <= m; l = ++r) {
//        while (r + 1 <= m && edgeArr[l].u == edgeArr[r + 1].u) {
//            r++;
//        }
//        for (int i = r, u, v; i >= l; i--) {
//            u = edgeArr[i].u;
//            v = edgeArr[i].v;
//            outDeg[u]++;
//            inDeg[v]++;
//            addEdge(u, v);
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
//void euler(int u) {
//    for (int e = cur[u]; e > 0; e = cur[u]) {
//        cur[u] = nxt[e];
//        euler(to[e]);
//    }
//    path[++cntp] = u;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> edgeArr[i].u >> edgeArr[i].v;
//    }
//    connect();
//    int start = directedStart();
//    if (start == -1) {
//        cout << "No\n";
//    } else {
//        euler(start);
//        if (cntp != m + 1) {
//            cout << "No\n";
//        } else {
//            for (int i = cntp; i >= 1; i--) {
//                cout << path[i] << " ";
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}