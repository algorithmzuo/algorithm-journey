package class188;

// 邮递员，C++版
// 一共n个点，m条有向边，不会有重边和自环
// 邮递员一定要从1号点开始，最后回到1号点，每条边都要走1次
// 邮递员依次走过的点的编号，就可以形成一个路径
// 给定t个序列，每个序列先给定长度k，然后是k个节点的编号
// 希望每个序列都是路径的连续子段，如果不存在这样的路径，打印"NIE"
// 如果存在这样的路径，先打印"TAK"，然后打印依次走过的节点编号
// 2 <= n <= 5 * 10^4    1 <= m <= 2 * 10^5
// 0 <= t <= 10^4        所有序列的节点总量 <= 2 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3443
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50005;
//const int MAXM = 200005;
//const int MAXC = 2000005;
//int n, m, t;
//int u[MAXM];
//int v[MAXM];
//int chain[MAXC];
//
//int headg[MAXN];
//int nextg[MAXM];
//int tog[MAXM];
//int cntg;
//int edgeCnt;
//
//map<pair<int, int>, int> pairEdge;
//map<pair<int, int>, int> chainHead;
//int lastChain[MAXM];
//
//int curv[MAXN];
//int inDeg[MAXN];
//int outDeg[MAXN];
//
//bool start[MAXM];
//int etoe[MAXM];
//bool vis[MAXM];
//
//int path[MAXM];
//int cntp;
//
//int ans[MAXM];
//int cnta;
//
//void addEdge(int x, int y) {
//    nextg[++cntg] = headg[x];
//    tog[cntg] = y;
//    headg[x] = cntg;
//}
//
//bool linkEdge() {
//    for (int i = 1; i <= m; i++) {
//        pairEdge[{u[i], v[i]}] = i;
//        start[i] = true;
//    }
//    int siz = chain[1], l = 2, r = l + siz - 1;
//    int a, b, ledge, redge;
//    while (siz > 0) {
//        ledge = 0;
//        for (int i = l; i < r; i++) {
//            a = chain[i];
//            b = chain[i + 1];
//            auto it = pairEdge.find({a, b});
//            if (it == pairEdge.end()) {
//                return false;
//            }
//            redge = it->second;
//            if (ledge != 0) {
//                if (etoe[ledge] != 0 && etoe[ledge] != redge) {
//                    return false;
//                }
//                etoe[ledge] = redge;
//                start[redge] = false;
//            }
//            ledge = redge;
//        }
//        siz = chain[r + 1];
//        l = r + 2;
//        r = l + siz - 1;
//    }
//    return true;
//}
//
//int getChainEnd(int edge) {
//    while (etoe[edge] != 0) {
//        if (vis[edge]) {
//            return -1;
//        }
//        vis[edge] = true;
//        edge = etoe[edge];
//    }
//    return edge;
//}
//
//bool compress() {
//    for (int i = 1; i <= m; i++) {
//        if (start[i]) {
//            int x = u[i];
//            int y = v[i];
//            if (etoe[i] != 0) {
//                int end = getChainEnd(i);
//                if (end == -1) {
//                    return false;
//                }
//                y = v[end];
//                auto it = chainHead.find({x, y});
//                if (it != chainHead.end()) {
//                    lastChain[i] = it->second;
//                }
//                chainHead[{x, y}] = i;
//            }
//            addEdge(x, y);
//            outDeg[x]++;
//            inDeg[y]++;
//            edgeCnt++;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (inDeg[i] != outDeg[i]) {
//            return false;
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        curv[i] = headg[i];
//    }
//    return true;
//}
//
//void euler(int u) {
//    for (int e = curv[u]; e > 0; e = curv[u]) {
//        curv[u] = nextg[e];
//        euler(tog[e]);
//    }
//    path[++cntp] = u;
//}
//
//void decompress() {
//    for (int i = cntp; i >= 1; i--) {
//        int y = path[i];
//        if (cnta == 0) {
//            ans[++cnta] = y;
//        } else {
//            int x = ans[cnta];
//            auto it = chainHead.find({x, y});
//            if (it == chainHead.end()) {
//                ans[++cnta] = y;
//            } else {
//                int cur = it->second;
//                if (lastChain[cur] != 0) {
//                    chainHead[{x, y}] = lastChain[cur];
//                } else {
//                    chainHead.erase(it);
//                }
//                while (cur > 0) {
//                    ans[++cnta] = v[cur];
//                    cur = etoe[cur];
//                }
//            }
//        }
//    }
//}
//
//bool compute() {
//    if (!linkEdge()) {
//        return false;
//    }
//    if (!compress()) {
//        return false;
//    }
//    euler(1);
//    if (cntp != edgeCnt + 1) {
//        return false;
//    }
//    decompress();
//    return cnta == m + 1;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> u[i] >> v[i];
//    }
//    cin >> t;
//    for (int i = 1, siz, idx = 0; i <= t; i++) {
//        cin >> siz;
//        chain[++idx] = siz;
//        for (int j = 1; j <= siz; j++) {
//            cin >> chain[++idx];
//        }
//    }
//    bool check = compute();
//    if (!check) {
//        cout << "NIE\n";
//    } else {
//        cout << "TAK\n";
//        for (int i = 1; i <= cnta; i++) {
//            cout << ans[i] << "\n";
//        }
//    }
//    return 0;
//}