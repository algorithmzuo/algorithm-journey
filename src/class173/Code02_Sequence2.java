package class173;

// 序列，C++版
// 给定一个长度为n的数组arr，初始时刻认为是第0秒
// 接下来发生m条操作，第i条操作发生在第i秒，操作类型如下
// 操作 1 l r v : arr[l..r]范围上每个数加v，v可能是负数
// 操作 2 x v   : 不包括当前这一秒，查询过去多少秒内，arr[x] >= v
// 2 <= n、m <= 10^5
// -10^9 <= 数组中的值 <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3863
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Event {
//    int op, x, t, v, q;
//};
//
//bool EventCmp(Event &a, Event &b) {
//    return a.x != b.x ? a.x < b.x : a.t < b.t;
//}
//
//const int MAXN = 100001;
//const int MAXB = 501;
//int n, m;
//int arr[MAXN];
//
//Event event[MAXN << 2];
//int cnte, cntq;
//
//long long tim[MAXN];
//long long sortv[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//long long lazy[MAXB];
//
//int ans[MAXN];
//
//void addChange(int x, int t, int v) {
//    event[++cnte].op = 1;
//    event[cnte].x = x;
//    event[cnte].t = t;
//    event[cnte].v = v;
//}
//
//void addQuery(int x, int t, int v) {
//    event[++cnte].op = 2;
//    event[cnte].x = x;
//    event[cnte].t = t;
//    event[cnte].v = v;
//    event[cnte].q = ++cntq;
//}
//
//void innerAdd(int l, int r, long long v) {
//    for (int i = l; i <= r; i++) {
//        tim[i] += v;
//    }
//    for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
//        sortv[i] = tim[i];
//    }
//    sort(sortv + bl[bi[l]], sortv + br[bi[l]] + 1);
//}
//
//void add(int l, int r, long long v) {
//    if (l > r) {
//        return;
//    }
//    if (bi[l] == bi[r]) {
//        innerAdd(l, r, v);
//    } else {
//        innerAdd(l, br[bi[l]], v);
//        innerAdd(bl[bi[r]], r, v);
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            lazy[i] += v;
//        }
//    }
//}
//
//int innerQuery(int l, int r, long long v) {
//    v -= lazy[bi[l]];
//    int ans = 0;
//    for (int i = l; i <= r; i++) {
//        if (tim[i] >= v) {
//            ans++;
//        }
//    }
//    return ans;
//}
//
//int getCnt(int i, long long v) {
//    v -= lazy[i];
//    int l = bl[i], r = br[i], m, pos = br[i] + 1;
//    while (l <= r) {
//        m = (l + r) >> 1;
//        if (sortv[m] >= v) {
//            pos = m;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return br[i] - pos + 1;
//}
//
//int query(int l, int r, long long v) {
//    if (l > r) {
//        return 0;
//    }
//    int ans = 0;
//    if (bi[l] == bi[r]) {
//    	ans = innerQuery(l, r, v);
//    } else {
//    	ans += innerQuery(l, br[bi[l]], v);
//    	ans += innerQuery(bl[bi[r]], r, v);
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            ans += getCnt(i, v);
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = (int)sqrt(m);
//    bnum = (m + blen - 1) / blen;
//    for (int i = 1; i <= m; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, m);
//    }
//    sort(event + 1, event + cnte + 1, EventCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    m++;
//    for (int t = 2, op, l, r, v, x; t <= m; t++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> l >> r >> v;
//            addChange(l, t, v);
//            addChange(r + 1, t, -v);
//        } else {
//            cin >> x >> v;
//            addQuery(x, t, v);
//        }
//    }
//    prepare();
//    for (int i = 1; i <= cnte; i++) {
//        if (event[i].op == 1) {
//            add(event[i].t, m, event[i].v);
//        } else {
//            ans[event[i].q] = query(1, event[i].t - 1, 1LL * event[i].v - arr[event[i].x]);
//        }
//    }
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}