package class154;

// 数字序列，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4331
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//int n;
//long long arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//int from[MAXN];
//int to[MAXN];
//int siz[MAXN];
//int stk[MAXN];
//long long ans[MAXN];
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        ls[i] = rs[i] = dist[i] = 0;
//    }
//}
//
//int find(int i) {
//    fa[i] = fa[i] == i ? i : find(fa[i]);
//    return fa[i];
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) return i + j;
//    int tmp;
//    if (arr[i] < arr[j]) {
//        tmp = i; i = j; j = tmp;
//    }
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        tmp = ls[i]; ls[i] = rs[i]; rs[i] = tmp;
//    }
//    dist[i] = dist[rs[i]] + 1;
//    fa[ls[i]] = fa[rs[i]] = i;
//    return i;
//}
//
//int pop(int i) {
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    fa[i] = merge(ls[i], rs[i]);
//    ls[i] = rs[i] = dist[i] = 0;
//    return fa[i];
//}
//
//long long compute() {
//    for (int i = 1; i <= n; i++) {
//        fa[i] = from[i] = to[i] = i;
//        siz[i] = 1;
//    }
//    int stackSize = 0;
//    for (int i = 1, pre, cur, s; i <= n; i++) {
//        while (stackSize > 0) {
//            pre = find(stk[stackSize]);
//            cur = find(i);
//            if (arr[pre] <= arr[cur]) {
//                break;
//            }
//            s = siz[pre] + siz[cur];
//            cur = merge(pre, cur);
//            while (s > ((i - from[pre] + 1 + 1) / 2)) {
//                cur = pop(cur);
//                s--;
//            }
//            from[cur] = from[pre];
//            to[cur] = i;
//            siz[cur] = s;
//            stackSize--;
//        }
//        stk[++stackSize] = i;
//    }
//    long long sum = 0;
//    for (int i = 1, cur; i <= stackSize; i++) {
//        cur = find(stk[i]);
//        for (int j = from[cur]; j <= to[cur]; j++) {
//            ans[j] = arr[cur];
//            sum += llabs(ans[j] - arr[j]);
//        }
//    }
//    return sum;
//}
//
//int main() {
//    ios_base::sync_with_stdio(false);
//    cin.tie(NULL);
//    cin >> n;
//    prepare();
//    long long x;
//    for (int i = 1; i <= n; i++) {
//        cin >> x;
//        arr[i] = x - i;
//    }
//    long long res = compute();
//    cout << res << "\n";
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] + i << (i == n ? '\n' : ' ');
//    }
//    return 0;
//}