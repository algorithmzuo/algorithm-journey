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
//int cnt;
//
//int cur[MAXN];
//int outDeg[MAXN];
//int inDeg[MAXN];
//
//int trail[MAXM];
//int top;
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
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
//int getStart() {
//    int s = 0;
//    for (int i = 1; i <= n; i++) {
//        if (abs(outDeg[i] - inDeg[i]) > 1) {
//            return -1;
//        }
//        if (outDeg[i] > inDeg[i]) {
//            if (s != 0) {
//                return -1;
//            }
//            s = i;
//        }
//    }
//    if (s > 0) {
//        return s;
//    }
//    for (int i = 1; i <= n; i++) {
//        if (outDeg[i] > 0) {
//            return i;
//        }
//    }
//    return 1;
//}
//
//void dfs(int u) {
//    for (int e = cur[u]; e > 0; e = cur[u]) {
//        cur[u] = nxt[e];
//        dfs(to[e]);
//    }
//    trail[++top] = u;
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
//    int start = getStart();
//    if (start == -1) {
//        cout << "No\n";
//    } else {
//        dfs(start);
//        if (top != m + 1) {
//            cout << "No\n";
//        } else {
//            for (int i = top; i >= 1; i--) {
//                cout << trail[i] << " ";
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}