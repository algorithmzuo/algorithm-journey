package class166;

// 检查二分图，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5787
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = 3000001;
//int n, m, k;
//
//int father[MAXN << 1];
//int siz[MAXN << 1];
//int rollback[MAXN << 1][2];
//int opsize = 0;
//
//int head[MAXN << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//int cnt = 0;
//
//bool ans[MAXN];
//
//void addEdge(int i, int x, int y) {
//	nxt[++cnt] = head[i];
//	tox[cnt] = x;
//	toy[cnt] = y;
//	head[i] = cnt;
//}
//
//int find(int i) {
//	while (i != father[i]) {
//        i = father[i];
//    }
//	return i;
//}
//
//void Union(int x, int y) {
//	int fx = find(x);
//	int fy = find(y);
//	if (siz[fx] < siz[fy]) {
//		int tmp = fx;
//		fx = fy;
//		fy = tmp;
//	}
//	father[fy] = fx;
//	siz[fx] += siz[fy];
//	rollback[++opsize][0] = fx;
//	rollback[opsize][1] = fy;
//}
//
//void undo() {
//	int fx = rollback[opsize][0];
//	int fy = rollback[opsize--][1];
//	father[fy] = fy;
//	siz[fx] -= siz[fy];
//}
//
//void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
//	if (jobl <= l && r <= jobr) {
//		addEdge(i, jobx, joby);
//	} else {
//		int mid = (l + r) >> 1;
//		if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, l, mid, i << 1);
//        }
//		if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
//        }
//	}
//}
//
//void dfs(int l, int r, int i) {
//	bool check = true;
//	int unionCnt = 0;
//	for (int ei = head[i]; ei > 0; ei = nxt[ei]) {
//		int x = tox[ei], y = toy[ei], fx = find(x), fy = find(y);
//		if (fx == fy) {
//			check = false;
//			break;
//		} else {
//			Union(x, y + n);
//			Union(y, x + n);
//			unionCnt += 2;
//		}
//	}
//	if (check) {
//		if (l == r) {
//			ans[l] = true;
//		} else {
//			int mid = (l + r) >> 1;
//			dfs(l, mid, i << 1);
//			dfs(mid + 1, r, i << 1 | 1);
//		}
//	} else {
//		for (int k = l; k <= r; k++) {
//            ans[k] = false;
//        }
//	}
//	for (int k = 1; k <= unionCnt; k++) {
//		undo();
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k;
//	for (int i = 1; i <= n * 2; i++) {
//		father[i] = i;
//		siz[i] = 1;
//	}
//	for (int i = 1, x, y, l, r; i <= m; i++) {
//        cin >> x >> y >> l >> r;
//		add(l + 1, r, x, y, 1, k, 1);
//	}
//	dfs(1, k, 1);
//	for (int i = 1; i <= k; i++) {
//		if (ans[i]) {
//            cout << "Yes" << "\n";
//        } else {
//        	cout << "No" << "\n";
//        }
//	}
//	return 0;
//}