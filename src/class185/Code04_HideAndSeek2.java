package class185;

// 捉迷藏，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Set {
//    priority_queue<int> addHeap;
//    priority_queue<int> delHeap;
//
//    void clean() {
//        while (!delHeap.empty() && !addHeap.empty() && delHeap.top() == addHeap.top()) {
//            addHeap.pop();
//            delHeap.pop();
//        }
//    }
//
//    int size() {
//        return (int)addHeap.size() - (int)delHeap.size();
//    }
//
//    void push(int v) {
//        addHeap.push(v);
//    }
//
//    void del(int v) {
//        delHeap.push(v);
//    }
//
//    int pop() {
//        clean();
//        int v = addHeap.top();
//        addHeap.pop();
//        return v;
//    }
//
//    int top() {
//        clean();
//        return addHeap.top();
//    }
//
//    int second() {
//        int a = pop();
//        int b = top();
//        push(a);
//        return b;
//    }
//};
//
//const int MAXN = 100001;
//int n, m;
//bool status[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//bool vis[MAXN];
//int centfa[MAXN];
//
//Set distSet[MAXN];
//Set maxdSet[MAXN];
//Set maxdTop2;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int ei = head[u], v; ei; ei = nxt[ei]) {
//        v = to[ei];
//        if (v != f) {
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] <= dep[top[b]]) {
//            b = fa[top[b]];
//        } else {
//            a = fa[top[a]];
//        }
//    }
//    return dep[a] <= dep[b] ? a : b;
//}
//
//int getDist(int x, int y) {
//    return dep[x] + dep[y] - (dep[getLca(x, y)] << 1);
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void centroidTree(int u, int fa) {
//    centfa[u] = fa;
//    vis[u] = true;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            centroidTree(getCentroid(v, u), u);
//        }
//    }
//}
//
//void add(int x) {
//    if (maxdSet[x].size() >= 2) {
//        maxdTop2.push(maxdSet[x].top() + maxdSet[x].second());
//    }
//}
//
//void del(int x) {
//    if (maxdSet[x].size() >= 2) {
//        maxdTop2.del(maxdSet[x].top() + maxdSet[x].second());
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        int u = i;
//        while (u > 0) {
//            int fa = centfa[u];
//            if (fa > 0) {
//                distSet[u].push(getDist(i, fa));
//            }
//            u = fa;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        maxdSet[i].push(0);
//        int fa = centfa[i];
//        if (fa > 0) {
//            maxdSet[fa].push(distSet[i].top());
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        add(i);
//    }
//}
//
//void on(int x) {
//    del(x);
//    maxdSet[x].del(0);
//    add(x);
//    for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
//        del(fa);
//        maxdSet[fa].del(distSet[u].top());
//        distSet[u].del(getDist(x, fa));
//        if (distSet[u].size() > 0) {
//            maxdSet[fa].push(distSet[u].top());
//        }
//        add(fa);
//    }
//}
//
//void off(int x) {
//    del(x);
//    maxdSet[x].push(0);
//    add(x);
//    for (int u = x, fa = centfa[u]; fa > 0; u = fa, fa = centfa[u]) {
//        del(fa);
//        if (distSet[u].size() > 0) {
//            maxdSet[fa].del(distSet[u].top());
//        }
//        distSet[u].push(getDist(x, fa));
//        maxdSet[fa].push(distSet[u].top());
//        add(fa);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    centroidTree(getCentroid(1, 0), 0);
//    prepare();
//    int offCnt = n;
//    cin >> m;
//    char op;
//    int x;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == 'G') {
//            if (offCnt <= 1) {
//                cout << offCnt - 1 << '\n';
//            } else {
//                cout << maxdTop2.top() << '\n';
//            }
//        } else {
//            cin >> x;
//            if (status[x]) {
//                off(x);
//                offCnt++;
//            } else {
//                on(x);
//                offCnt--;
//            }
//            status[x] = !status[x];
//        }
//    }
//    return 0;
//}