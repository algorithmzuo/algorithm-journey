package class191;

// 点双连通分量模版题2，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P8435
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXM = 300001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM << 1];
//int to[MAXM << 1];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//vector<vector<int>> vbccArr;
//
//bool VbccCmp(const vector<int> &o1, const vector<int> &o2) {
//    int size = (int)min(o1.size(), o2.size());
//    for (int i = 0; i < size; i++) {
//        if (o1[i] != o2[i]) {
//            return o1[i] < o2[i];
//        }
//    }
//    return o1.size() < o2.size();
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void tarjan(int u, bool root) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    if (root && head[u] == 0) {
//        vector<int> list;
//        list.push_back(u);
//        vbccArr.push_back(list);
//        return;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v, false);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                vector<int> list;
//                int pop;
//                do {
//                    pop = sta[top--];
//                    list.push_back(pop);
//                } while (pop != v);
//                list.push_back(u);
//                vbccArr.push_back(list);
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        if (u != v) {
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i, true);
//        }
//    }
//    int ansCnt = 0;
//    for (int i = 0; i < (int)vbccArr.size(); i++) {
//        if ((int)vbccArr[i].size() > 1) {
//            ansCnt++;
//            sort(vbccArr[i].begin(), vbccArr[i].end());
//        }
//    }
//    cout << ansCnt << "\n";
//    sort(vbccArr.begin(), vbccArr.end(), VbccCmp);
//    for (int i = 0; i < (int)vbccArr.size(); i++) {
//        if ((int)vbccArr[i].size() > 1) {
//            for (int node : vbccArr[i]) {
//                cout << node << " ";
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}