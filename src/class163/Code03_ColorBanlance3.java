package class163;

// 颜色平衡的子树，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P9233
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//int n;
//int arr[MAXN];
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cnt = 0;
//int siz[MAXN];
//int son[MAXN];
//int colorCnt[MAXN];
//int cntCnt[MAXN];
//int ans = 0;
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
//}
//
//void effect(int u) {
//    colorCnt[arr[u]]++;
//    cntCnt[colorCnt[arr[u]] - 1]--;
//    cntCnt[colorCnt[arr[u]]]++;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        effect(to[e]);
//    }
//}
//
//void cancle(int u) {
//    colorCnt[arr[u]]--;
//    cntCnt[colorCnt[arr[u]] + 1]--;
//    cntCnt[colorCnt[arr[u]]]++;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        cancle(to[e]);
//    }
//}
//
//void dfs1(int u) {
//    siz[u] = 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        dfs1(to[e]);
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        siz[u] += siz[v];
//        if (son[u] == 0 || siz[son[u]] < siz[v]) {
//            son[u] = v;
//        }
//    }
//}
//
//void dfs2(int u, int keep) {
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != son[u]) {
//            dfs2(v, 0);
//        }
//    }
//    if (son[u] != 0) {
//        dfs2(son[u], 1);
//    }
//    colorCnt[arr[u]]++;
//    cntCnt[colorCnt[arr[u]] - 1]--;
//    cntCnt[colorCnt[arr[u]]]++;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != son[u]) {
//            effect(v);
//        }
//    }
//    if (colorCnt[arr[u]] * cntCnt[colorCnt[arr[u]]] == siz[u]) {
//        ans++;
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
//    for (int i = 1, color, father; i <= n; i++) {
//        cin >> color >> father;
//        arr[i] = color;
//        if (i != 1) {
//            addEdge(father, i);
//        }
//    }
//    dfs1(1);
//    dfs2(1, 1);
//    cout << ans << "\n";
//    return 0;
//}