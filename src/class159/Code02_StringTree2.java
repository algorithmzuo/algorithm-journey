package class159;

// 字符串树，C++版
// 一共有n个节点，n-1条边，组成一棵树，每条边的边权为字符串
// 一共有m条查询，每条查询的格式为
// u v s : 查询节点u到节点v的路径中，有多少边的字符串以字符串s作为前缀
// 1 <= n、m <= 10^5
// 所有字符串长度不超过10，并且都由字符a~z组成
// 测试链接 : https://www.luogu.com.cn/problem/P6088
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//static const int MAXN = 100001;
//static const int MAXT = 1000001;
//static const int MAXH = 20;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//string weight[MAXN << 1];
//int cntg = 0;
//
//int root[MAXN];
//int tree[MAXT][27];
//int pass[MAXT];
//int cntt = 0;
//
//int deep[MAXN];
//int stjump[MAXN][MAXH];
//
//void addEdge(int u, int v, const string &w) {
//	nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//int num(char c) {
//    return c - 'a' + 1;
//}
//
//int clone(int i) {
//    int rt = ++cntt;
//    for (int c = 1; c <= 26; c++) {
//        tree[rt][c] = tree[i][c];
//    }
//    pass[rt] = pass[i];
//    return rt;
//}
//
//int insert(const string &str, int i) {
//    int rt = clone(i);
//    pass[rt]++;
//    int pre = rt;
//    for (int j = 0; j < (int)str.size(); j++) {
//        int path = num(str[j]);
//        i = tree[i][path];
//        int cur = clone(i);
//        pass[cur]++;
//        tree[pre][path] = cur;
//        pre = cur;
//    }
//    return rt;
//}
//
//int query(const string &str, int i) {
//    for (int j = 0; j < (int)str.size(); j++) {
//        int path = num(str[j]);
//        i = tree[i][path];
//        if (!i) return 0;
//    }
//    return pass[i];
//}
//
//void dfs(int u, int fa, const string &path) {
//    root[u] = insert(path, root[fa]);
//    deep[u] = deep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        if (to[e] != fa) {
//            dfs(to[e], u, weight[e]);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (deep[a] < deep[b]) swap(a, b);
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (deep[stjump[a][p]] >= deep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) return a;
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int compute(int u, int v, const string &s) {
//    return query(s, root[u]) + query(s, root[v]) - 2 * query(s, root[lca(u, v)]);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i < n; i++) {
//        int u, v;
//        string s;
//        cin >> u >> v >> s;
//        addEdge(u, v, s);
//        addEdge(v, u, s);
//    }
//    dfs(1, 0, "");
//    cin >> m;
//    while (m--) {
//        int u, v;
//        string s;
//        cin >> u >> v >> s;
//        cout << compute(u, v, s) << "\n";
//    }
//    return 0;
//}