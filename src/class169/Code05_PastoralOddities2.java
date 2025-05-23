package class169;

// 点的度都是奇数的最小瓶颈，C++版
// 一共有n个点，初始没有边，依次加入m条无向边，每条边有边权
// 每次加入后，询问是否存在一个边集，满足每个点连接的边的数量都是奇数
// 如果存在，希望边集的最大边权，尽可能小，如果不存在打印-1
// 2 <= n <= 10^5
// 1 <= m <= 3 * 10^5
// 1 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF603E
// 测试链接 : https://codeforces.com/problemset/problem/603/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int x, y, w, tim, rak;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    return a.w < b.w;
//}
//
//const int MAXN = 100001;
//const int MAXM = 300001;
//int n, m;
//
//Edge edge[MAXM];
//Edge wsort[MAXM];
//
//int oddnum;
//int father[MAXN];
//int siz[MAXN];
//int rollback[MAXN][2];
//int opsize = 0;
//
//int ans[MAXM];
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//bool Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (fx == fy) {
//        return false;
//    }
//    if ((siz[fx] & 1) == 1 && (siz[fy] & 1) == 1) {
//        oddnum -= 2;
//    }
//    if (siz[fx] < siz[fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//    return true;
//}
//
//void undo() {
//    int fx = rollback[opsize][0];
//    int fy = rollback[opsize--][1];
//    father[fy] = fy;
//    siz[fx] -= siz[fy];
//    if ((siz[fx] & 1) == 1 && (siz[fy] & 1) == 1) {
//        oddnum += 2;
//    }
//}
//
//void compute(int el, int er, int vl, int vr) {
//    if (el > er) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = el; i <= er; i++) {
//            ans[i] = vl;
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        int unionCnt1 = 0;
//        for (int i = vl; i <= mid; i++) {
//            if (wsort[i].tim < el) {
//                if (Union(wsort[i].x, wsort[i].y)) {
//                    unionCnt1++;
//                }
//            }
//        }
//        int unionCnt2 = 0;
//        int split = er + 1;
//        for (int i = el; i <= er; i++) {
//            if (edge[i].rak <= mid) {
//                if (Union(edge[i].x, edge[i].y)) {
//                    unionCnt2++;
//                }
//            }
//            if (oddnum == 0) {
//                split = i;
//                break;
//            }
//        }
//        for (int i = 1; i <= unionCnt2; i++) {
//            undo();
//        }
//        compute(el, split - 1, mid + 1, vr);
//        for (int i = 1; i <= unionCnt1; i++) {
//            undo();
//        }
//        int unionCnt3 = 0;
//        for (int i = el; i <= split - 1; i++) {
//            if (edge[i].rak < vl) {
//                if (Union(edge[i].x, edge[i].y)) {
//                    unionCnt3++;
//                }
//            }
//        }
//        compute(split, er, vl, mid);
//        for (int i = 1; i <= unionCnt3; i++) {
//            undo();
//        }
//    }
//}
//
//void prepare() {
//    oddnum = n;
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        wsort[i].x = edge[i].x;
//        wsort[i].y = edge[i].y;
//        wsort[i].w = edge[i].w;
//        wsort[i].tim = edge[i].tim;
//    }
//    sort(wsort + 1, wsort + m + 1, EdgeCmp);
//    for (int i = 1; i <= m; i++) {
//        wsort[i].rak = i;
//        edge[wsort[i].tim].rak = i;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i].x >> edge[i].y >> edge[i].w;
//        edge[i].tim = i;
//    }
//    prepare();
//    compute(1, m, 1, m + 1);
//    for (int i = 1; i <= m; i++) {
//        if (ans[i] == m + 1) {
//            cout << -1 << '\n';
//        } else {
//            cout << wsort[ans[i]].w << '\n';
//        }
//    }
//    return 0;
//}