package class165;

// 团建，C++版
// 一共有n个人，编号1~n，一共有k种颜色，编号1~k
// 每个人给定一种颜色，颜色相同的人认为是一个小组，可能有的颜色的组是没人的
// 给定m条边，每条边连接两个人，代表这两人之间有矛盾
// 假设组a和组b，两个组的人一起去团建，组a和组b的所有人，可以重新打乱
// 如果所有人最多分成两个集团，每人都要参加划分，并且每个集团的内部不存在矛盾
// 那么组a和组b就叫做一个"合法组对"，注意，组b和组a就不用重复计算了
// 一共有k个组，随意选两个组的情况很多，计算一共有多少个"合法组对"
// 1 <= n、m、k <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1444C
// 测试链接 : https://codeforces.com/problemset/problem/1444/C
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct CrossEdge {
//    int u, ucolor, v, vcolor;
//};
//
//bool CrossEdgeCmp(CrossEdge x, CrossEdge y) {
//    if(x.ucolor != y.ucolor) {
//    	return x.ucolor < y.ucolor;
//    } else {
//    	return x.vcolor < y.vcolor;
//    }
//}
//
//const int MAXN = 500001;
//int n, m, k;
//
//int color[MAXN];
//int edge[MAXN][2];
//
//CrossEdge crossEdge[MAXN];
//int cnt = 0;
//
//bool conflict[MAXN];
//
//int father[MAXN << 1];
//int siz[MAXN << 1];
//int rollback[MAXN << 1][2];
//int opsize;
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//void Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//}
//
//void undo() {
//    int fx = rollback[opsize][0];
//    int fy = rollback[opsize--][1];
//    father[fy] = fy;
//    siz[fx] -= siz[fy];
//}
//
//void filter() {
//    for (int i = 1; i <= 2 * n; ++i) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    for (int i = 1, u, v; i <= m; i++) {
//        u = edge[i][0];
//        v = edge[i][1];
//        if (color[u] < color[v]) {
//            crossEdge[++cnt].u = u;
//            crossEdge[cnt].ucolor = color[u];
//            crossEdge[cnt].v = v;
//            crossEdge[cnt].vcolor = color[v];
//        } else if (color[u] > color[v]) {
//            crossEdge[++cnt].u = v;
//            crossEdge[cnt].ucolor = color[v];
//            crossEdge[cnt].v = u;
//            crossEdge[cnt].vcolor = color[u];
//        } else {
//            if (conflict[color[u]]) {
//                continue;
//            }
//            if (find(u) == find(v)) {
//                k--;
//                conflict[color[u]] = true;
//            } else {
//            	Union(u, v + n);
//            	Union(v, u + n);
//            }
//        }
//    }
//}
//
//long long compute() {
//    sort(crossEdge + 1, crossEdge + cnt + 1, CrossEdgeCmp);
//    long long ans = (long long)k * (k - 1) / 2;
//    int u, ucolor, v, vcolor, unionCnt;
//    for (int l = 1, r = 1; l <= cnt; l = ++r) {
//        ucolor = crossEdge[l].ucolor;
//        vcolor = crossEdge[l].vcolor;
//        while (r + 1 <= cnt && crossEdge[r + 1].ucolor == ucolor && crossEdge[r + 1].vcolor == vcolor) {
//            r++;
//        }
//        if (conflict[ucolor] || conflict[vcolor]) {
//            continue;
//        }
//        unionCnt = 0;
//        for (int i = l; i <= r; i++) {
//            u = crossEdge[i].u;
//            v = crossEdge[i].v;
//            if (find(u) == find(v)) {
//                ans--;
//                break;
//            } else {
//            	Union(u, v + n);
//            	Union(v, u + n);
//                unionCnt += 2;
//            }
//        }
//        for (int i = 1; i <= unionCnt; i++) {
//            undo();
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k;
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i][0] >> edge[i][1];
//    }
//    filter();
//    cout << compute() << "\n";
//    return 0;
//}