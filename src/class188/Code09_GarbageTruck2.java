package class188;

// 垃圾车，C++版
// 一共有n个点，m条无向边，所有点不保证连通
// 每条边的属性为 u v s t : u和v是端点，s是初始状态，t是最终状态
// 状态的数值只有0和1两种，当一辆车通过一条无向边，那么状态会翻转
// 所有的边都要达成最终状态，所以需要若干辆车来完成这个目标
// 一辆车的路线中，可以指定一个起点，最终回到起点，别的点不能重复出现
// 如果存在方案，提供任何一种方案即可，首先打印需要几辆车
// 然后对每辆车，先打印通过的边数，再打印依次到达了哪些点
// 如果不存在方案，打印"NIE"
// 1 <= n <= 10^5    1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3520
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 2000001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int eid[MAXM];
//int cntg;
//
//int deg[MAXN];
//int cur[MAXN];
//bool visNode[MAXN];
//bool visEdge[MAXM];
//
//bool inpath[MAXN];
//int path[MAXM];
//int cntp;
//
//int ansArr[MAXM];
//int ansl[MAXM];
//int ansr[MAXM];
//int idx;
//int cnta;
//
//void addEdge(int u, int v, int id) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    eid[cntg] = id;
//    head[u] = cntg;
//}
//
//void getCircle(int u) {
//    cnta++;
//    ansArr[++idx] = u;
//    ansl[cnta] = idx;
//    while (path[cntp] != u) {
//        inpath[path[cntp]] = false;
//        ansArr[++idx] = path[cntp];
//        cntp--;
//    }
//    ansArr[++idx] = u;
//    ansr[cnta] = idx;
//}
//
//void euler(int u) {
//    visNode[u] = true;
//    for (int e = cur[u]; e > 0; e = cur[u]) {
//        cur[u] = nxt[e];
//        if (!visEdge[eid[e]]) {
//            visEdge[eid[e]] = true;
//            euler(to[e]);
//        }
//    }
//    if (inpath[u]) {
//        getCircle(u);
//    } else {
//        inpath[u] = true;
//        path[++cntp] = u;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v, s, t; i <= m; i++) {
//        cin >> u >> v >> s >> t;
//        if (s != t) {
//            deg[u]++;
//            deg[v]++;
//            addEdge(u, v, i);
//            addEdge(v, u, i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        cur[i] = head[i];
//    }
//    bool check = true;
//    for (int i = 1; i <= n; i++) {
//        if ((deg[i] & 1) == 1) {
//            check = false;
//            break;
//        }
//    }
//    if (!check) {
//        cout << "NIE\n";
//    } else {
//        for (int i = 1; i <= n; i++) {
//            if (!visNode[i]) {
//                euler(i);
//            }
//        }
//        cout << cnta << "\n";
//        for (int i = 1; i <= cnta; i++) {
//            cout << (ansr[i] - ansl[i]) << " ";
//            for (int j = ansl[i]; j <= ansr[i]; j++) {
//                cout << ansArr[j] << " ";
//            }
//            cout << "\n";
//        }
//    }
//    return 0;
//}