package class163;

// 不同名字数量，C++版
// 一共有n个节点，编号1~n，给定每个节点的名字和父亲节点编号
// 名字是string类型，如果父亲节点编号为0，说明当前节点是某棵树的头节点
// 注意，n个节点组成的是森林结构，可能有若干棵树
// 一共有m条查询，每条查询 x k，含义如下
// 以x为头的子树上，到x距离为k的所有节点中，打印不同名字的数量
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF246E
// 测试链接 : https://codeforces.com/problemset/problem/246/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//bool root[MAXN];
//int arr[MAXN];
//
//int headg[MAXN];
//int nextg[MAXN];
//int tog[MAXN];
//int cntg;
//
//int headq[MAXN];
//int nextq[MAXN];
//int ansiq[MAXN];
//int kq[MAXN];
//int cntq;
//
//int fa[MAXN];
//int siz[MAXN];
//int dep[MAXN];
//int son[MAXN];
//unordered_map<string, int> nameToId;
//vector<unordered_set<int>> depSet;
//int ans[MAXN];
//
//int nameId(const string &name) {
//    if (nameToId.find(name) != nameToId.end()) {
//        return nameToId[name];
//    }
//    int newId = nameToId.size() + 1;
//    nameToId[name] = newId;
//    return newId;
//}
//
//void addId(int deep, int id) {
//    depSet[deep].insert(id);
//}
//
//void removeId(int deep, int id) {
//    depSet[deep].erase(id);
//}
//
//int sizeOfId(int deep) {
//    if (deep > n) {
//        return 0;
//    }
//    return (int)depSet[deep].size();
//}
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addQuestion(int u, int ansi, int k) {
//    nextq[++cntq] = headq[u];
//    ansiq[cntq] = ansi;
//    kq[cntq] = k;
//    headq[u] = cntq;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    siz[u] = 1;
//    dep[u] = dep[f] + 1;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        dfs1(tog[e], u);
//    }
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        siz[u] += siz[v];
//        if (son[u] == 0 || siz[son[u]] < siz[v]) {
//            son[u] = v;
//        }
//    }
//}
//
//void effect(int u) {
//    addId(dep[u], arr[u]);
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        effect(tog[e]);
//    }
//}
//
//void cancle(int u) {
//    removeId(dep[u], arr[u]);
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        cancle(tog[e]);
//    }
//}
//
//void dfs2(int u, int keep) {
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        if (v != son[u]) {
//            dfs2(v, 0);
//        }
//    }
//    if (son[u] != 0) {
//        dfs2(son[u], 1);
//    }
//    addId(dep[u], arr[u]);
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        if (v != son[u]) {
//            effect(v);
//        }
//    }
//    for (int i = headq[u]; i > 0; i = nextq[i]) {
//    	ans[ansiq[i]] = sizeOfId(dep[u] + kq[i]);
//    }
//    if (keep == 0) {
//        cancle(u);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    string name;
//    int father;
//    for (int i = 1; i <= n; i++) {
//        cin >> name >> father;
//        arr[i] = nameId(name);
//        if (father == 0) {
//        	root[i] = true;
//        } else {
//            addEdge(father, i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (root[i]) {
//            dfs1(i, 0);
//        }
//    }
//    depSet.resize(n + 1);
//    cin >> m;
//    for (int i = 1, node, k; i <= m; i++) {
//        cin >> node >> k;
//        addQuestion(node, i, k);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (root[i]) {
//            dfs2(i, 0);
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << "\n";
//    }
//    return 0;
//}