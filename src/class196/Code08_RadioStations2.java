package class196;

// 电台，C++版
// 给定数值f，范围[1, f]上，你要选一个数字ansx作为固定频率
// 一共p个电台，每个电台给定频率范围[l, r]，频率范围包含ansx的电台才能签约
// 签约电台还要满足n个要求和m个限制，具体格式如下
// 要求 u v : 必须签下电台u或者电台v
// 限制 u v : 电台u和电台v不能同时签下
// 如果存在签约方案，找到任何一种即可，依次打印，签约电台的数量、ansx、所有签约电台的编号
// 如果不存在签约方案，打印-1
// 2 <= n、p、f、m <= 4 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1215F
// 测试链接 : https://codeforces.com/problemset/problem/1215/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 400001;
//const int MAXT = 2000001;
//const int MAXM = 4000001;
//int n, p, f, m, cntt;
//
//int l[MAXN];
//int r[MAXN];
//
//int head[MAXT];
//int nxt[MAXM];
//int to[MAXM];
//int cntg;
//
//int dfn[MAXT];
//int low[MAXT];
//int cntd;
//
//int sta[MAXT];
//int top;
//
//int belong[MAXT];
//int sccCnt;
//
//int pre[MAXN];
//int suf[MAXN];
//
//int ansx;
//int pick[MAXN];
//int siz;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void link() {
//    pre[1] = ++cntt;
//    for (int i = 2; i <= f; i++) {
//        pre[i] = ++cntt;
//        addEdge(pre[i - 1], pre[i]);
//    }
//    suf[f] = ++cntt;
//    for (int i = f - 1; i >= 1; i--) {
//        suf[i] = ++cntt;
//        addEdge(suf[i + 1], suf[i]);
//    }
//    for (int i = 1; i <= p; i++) {
//        addEdge(i, pre[r[i]]);
//        if (r[i] + 1 <= f) {
//            addEdge(suf[r[i] + 1], i + p);
//        }
//        addEdge(i, suf[l[i]]);
//        if (l[i] - 1 >= 1) {
//            addEdge(pre[l[i] - 1], i + p);
//        }
//    }
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (belong[v] == 0) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//        } while (pop != u);
//    }
//}
//
//bool compute() {
//    bool check = true;
//    for (int i = 1; i <= p; i++) {
//        if (belong[i] == belong[i + p]) {
//            check = false;
//            break;
//        } else if (belong[i] < belong[i + p]) {
//            ansx = max(ansx, l[i]);
//            pick[++siz] = i;
//        }
//    }
//    return check;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> p >> f >> m;
//    cntt = p << 1;
//    for (int i = 1, u, v; i <= n; i++) {
//        cin >> u >> v;
//        addEdge(u + p, v);
//        addEdge(v + p, u);
//    }
//    for (int i = 1; i <= p; i++) {
//        cin >> l[i] >> r[i];
//    }
//    link();
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge(u, v + p);
//        addEdge(v, u + p);
//    }
//    for (int i = 1; i <= cntt; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i);
//        }
//    }
//    bool check = compute();
//    if (check) {
//        cout << siz << " " << ansx << "\n";
//        for (int i = 1; i <= siz; i++) {
//            cout << pick[i] << " ";
//        }
//    } else {
//        cout << -1 << "\n";
//    }
//    return 0;
//}