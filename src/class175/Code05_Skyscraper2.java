package class175;

// 雅加达的摩天楼，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3645
// 测试链接 : https://uoj.ac/problem/111
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int idx, jump, step;
//};
//
//const int MAXN = 30001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cnt;
//
//deque<Node> que;
//bitset<MAXN> bs[MAXN];
//bool vis[MAXN];
//
//void add(int idx, int jump) {
//    nxt[++cnt] = head[idx];
//    to[cnt] = jump;
//    head[idx] = cnt;
//}
//
//void extend(int idx, int jump, int step) {
//    if (!bs[idx].test(jump)) {
//        bs[idx].set(jump);
//        que.push_back({idx, jump, step});
//    }
//    if (!vis[idx]) {
//        vis[idx] = true;
//        for (int e = head[idx], nextJump; e; e = nxt[e]) {
//            nextJump = to[e];
//            if (!bs[idx].test(nextJump)) {
//                bs[idx].set(nextJump);
//                que.push_back({idx, nextJump, step});
//            }
//        }
//    }
//}
//
//int bfs(int s, int t) {
//    if (s == t) {
//        return 0;
//    }
//    for (int i = 0; i < n; i++) {
//        bs[i].reset();
//        vis[i] = false;
//    }
//    que.clear();
//    vis[s] = true;
//    for (int e = head[s], jump; e; e = nxt[e]) {
//        jump = to[e];
//        if (!bs[s].test(jump)) {
//            bs[s].set(jump);
//            que.push_back({s, jump, 0});
//        }
//    }
//    while (!que.empty()) {
//        Node cur = que.front();
//        que.pop_front();
//        int idx = cur.idx;
//        int jump = cur.jump;
//        int step = cur.step;
//        if (idx - jump == t || idx + jump == t) {
//            return step + 1;
//        }
//        if (idx - jump >= 0) {
//            extend(idx - jump, jump, step + 1);
//        }
//        if (idx + jump < n) {
//            extend(idx + jump, jump, step + 1);
//        }
//    }
//    return -1;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    int s, sjump, t, tjump;
//    cin >> s >> sjump >> t >> tjump;
//    add(s, sjump);
//    add(t, tjump);
//    for (int i = 2, idx, jump; i < m; i++) {
//        cin >> idx >> jump;
//        add(idx, jump);
//    }
//    cout << bfs(s, t) << '\n';
//    return 0;
//}