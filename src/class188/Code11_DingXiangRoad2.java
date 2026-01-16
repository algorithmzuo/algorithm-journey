package class188;

// 丁香之路，C++版
// 一共有n个点，点i和点j的距离为|i - j|，所以这是完全图，边是无向边
// 给定m个点对，每个点对(a, b)，保证 a != b，表示a和b之间的无向边做了标记
// 给定起点s，不管终点是什么，每条被标记的无向边，走过至少一次
// 打印起点s，终点i的情况下，走过路程的最小距离是多少
// 终点i可以是1~n中的任意一点，所以一共打印n个数值
// 1 <= n <= 2500
// m <= n个顶点的完全图的边数
// 测试链接 : https://www.luogu.com.cn/problem/P6628
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Edge {
//    int u, v, w;
//};
//
//bool EdgeCmp(Edge e1, Edge e2) {
//    if (e1.w != e2.w) {
//        return e1.w < e2.w;
//    }
//    if (e1.u != e2.u) {
//        return e1.u < e2.u;
//    }
//    return e1.v < e2.v;
//}
//
//const int MAXN = 3001;
//const int MAXM = 3000001;
//int n, m, s;
//int a[MAXM];
//int b[MAXM];
//
//bool fix[MAXN];
//set<int> nodeSet;
//int nodeArr[MAXN];
//int cnt;
//
//int fa1[MAXN];
//int deg1[MAXN];
//int fa2[MAXN];
//int deg2[MAXN];
//
//Edge edgeArr[MAXN];
//
//ll sum;
//
//int dist(int x, int y) {
//    return abs(x - y);
//}
//
//int find(int fa[], int x) {
//    if (x != fa[x]) {
//        fa[x] = find(fa, fa[x]);
//    }
//    return fa[x];
//}
//
//void Union(int fa[], int x, int y) {
//    int fx = find(fa, x);
//    int fy = find(fa, y);
//    if (fx != fy) {
//        fa[fx] = fy;
//    }
//}
//
//ll kruskal() {
//    ll cost = 0;
//    int edgeCnt = cnt - 1;
//    sort(edgeArr + 1, edgeArr + edgeCnt + 1, EdgeCmp);
//    for (int i = 1; i <= edgeCnt; i++) {
//        int u = edgeArr[i].u;
//        int v = edgeArr[i].v;
//        int w = edgeArr[i].w;
//        if (find(fa2, u) != find(fa2, v)) {
//            cost += 1LL * w * 2;
//            Union(fa2, u, v);
//        }
//    }
//    return cost;
//}
//
//ll compute(int start, int end) {
//    cnt = 0;
//    for (int x : nodeSet) {
//        nodeArr[++cnt] = x;
//    }
//    for (int i = 1; i <= n; i++) {
//        fa2[i] = find(fa1, i);
//        deg2[i] = deg1[i];
//    }
//    ll ans = sum;
//    deg2[start]++;
//    deg2[end]++;
//    Union(fa2, start, end);
//    for (int i = 1, p = 0; i <= cnt; i++) {
//        if ((deg2[nodeArr[i]] & 1) == 1) {
//            if (p == 0) {
//                p = i;
//            } else {
//                ans += dist(nodeArr[p], nodeArr[i]);
//                while (p < i) {
//                    Union(fa2, nodeArr[p++], nodeArr[i]);
//                }
//                p = 0;
//            }
//        }
//    }
//    for (int i = 1; i <= cnt - 1; i++) {
//        edgeArr[i].u = nodeArr[i];
//        edgeArr[i].v = nodeArr[i + 1];
//        edgeArr[i].w = nodeArr[i + 1] - nodeArr[i];
//    }
//    ans += kruskal();
//    return ans;
//}
//
//void prepare() {
//    fix[s] = true;
//    nodeSet.insert(s);
//    sum = 0;
//    for (int i = 1; i <= n; i++) {
//        fa1[i] = i;
//    }
//    for (int i = 1, u, v; i <= m; i++) {
//        u = a[i];
//        v = b[i];
//        deg1[u]++;
//        deg1[v]++;
//        fix[u] = true;
//        fix[v] = true;
//        nodeSet.insert(u);
//        nodeSet.insert(v);
//        Union(fa1, u, v);
//        sum += dist(u, v);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> s;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//    }
//    prepare();
//    for (int i = 1; i <= n; i++) {
//        if (!fix[i]) {
//            nodeSet.insert(i);
//        }
//        cout << compute(s, i) << " ";
//        if (!fix[i]) {
//            nodeSet.erase(i);
//        }
//    }
//    cout << "\n";
//    return 0;
//}