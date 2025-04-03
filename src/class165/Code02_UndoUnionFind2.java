package class165;

// 可撤销并查集模版题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc302_h
// 测试链接 : https://atcoder.jp/contests/abc302/tasks/abc302_h
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//int a[MAXN];
//int b[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cnt;
//
//int father[MAXN];
//int ncnt[MAXN];
//int ecnt[MAXN];
//
//int opstack[MAXN][2];
//int stacksiz = 0;
//
//int ans[MAXN];
//int ball = 0;
//
//void addEdge(int u, int v) {
//	nxt[++cnt] = head[u];
//	to[cnt] = v;
//    head[u] = cnt;
//}
//
//int find(int i) {
//	while(i != father[i]) {
//		i = father[i];
//	}
//	return i;
//}
//
//void merge(int h1, int h2) {
//    int big, small;
//    if (ncnt[h1] >= ncnt[h2]) {
//        big = h1;
//        small = h2;
//    } else {
//        big = h2;
//        small = h1;
//    }
//    father[small] = big;
//    ncnt[big] += ncnt[small];
//    ecnt[big] += ecnt[small] + 1;
//    stacksiz++;
//    opstack[stacksiz][0] = big;
//    opstack[stacksiz][1] = small;
//}
//
//void undo() {
//    int big = opstack[stacksiz][0];
//    int small = opstack[stacksiz][1];
//    stacksiz--;
//    father[small] = small;
//    ncnt[big] -= ncnt[small];
//    ecnt[big] -= ecnt[small] + 1;
//}
//
//void dfs(int u, int fa) {
//    int h1 = find(a[u]), h2 = find(b[u]);
//    bool merged = false;
//    int add = 0;
//    if (h1 == h2) {
//        ecnt[h1]++;
//        if (ncnt[h1] == ecnt[h1]) {
//            ball++;
//            add = 1;
//        }
//    } else {
//        if (ecnt[h1] < ncnt[h1] || ecnt[h2] < ncnt[h2]) {
//            ball++;
//            add = 1;
//        }
//        merge(h1, h2);
//        merged = true;
//    }
//    if (u != 1) {
//        ans[u] = ball;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if (to[e] != fa) {
//            dfs(to[e], u);
//        }
//    }
//    if (merged) {
//        undo();
//    } else {
//        ecnt[h1]--;
//    }
//    ball -= add;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n;
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> a[i] >> b[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        ncnt[i] = 1;
//        ecnt[i] = 0;
//    }
//    dfs(1, 0);
//    for (int i = 2; i < n; i++) {
//        cout << ans[i] << " ";
//    }
//    cout << ans[n] << "\n";
//    return 0;
//}