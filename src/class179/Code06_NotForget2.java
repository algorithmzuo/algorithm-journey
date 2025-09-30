package class179;

// 盼君勿忘，C++版
// 一个序列中每种数字只保留一个，得到的累加和，叫做去重累加和
// 给定一个长度为n的数组arr，接下来是m条查询，查询格式如下
// 查询 l r p : arr[l..r]范围上，累加每个子序列的去重累加和 % p 的结果打印
// 1 <= n、m、arr[i] <= 10^5
// 1 <= p <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P5072
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, mod, id;
//};
//
//const int MAXN = 100001;
//const int MAXB = 401;
//int n, m;
//int arr[MAXN];
//Query query[MAXN];
//int bi[MAXN];
//
//int head;
//int lst[MAXN];
//int nxt[MAXN];
//
//int cnt[MAXN];
//long long sum[MAXN];
//
//long long smlPower[MAXB];
//long long bigPower[MAXB];
//
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if (bi[a.l] & 1) {
//        return a.r < b.r;
//    } else {
//        return a.r > b.r;
//    }
//}
//
//void addNode(int x) {
//    lst[head] = x;
//    nxt[x] = head;
//    head = x;
//}
//
//void delNode(int x) {
//    if (x == head) {
//        head = nxt[head];
//        lst[head] = 0;
//        nxt[x] = 0;
//    } else {
//        nxt[lst[x]] = nxt[x];
//        lst[nxt[x]] = lst[x];
//        lst[x] = 0;
//        nxt[x] = 0;
//    }
//}
//
//void add(int num) {
//    if (cnt[num] > 0) {
//        sum[cnt[num]] -= num;
//    }
//    if (cnt[num] > 0 && sum[cnt[num]] == 0) {
//        delNode(cnt[num]);
//    }
//    cnt[num]++;
//    if (cnt[num] > 0 && sum[cnt[num]] == 0) {
//        addNode(cnt[num]);
//    }
//    if (cnt[num] > 0) {
//        sum[cnt[num]] += num;
//    }
//}
//
//void del(int num) {
//    if (cnt[num] > 0) {
//        sum[cnt[num]] -= num;
//    }
//    if (cnt[num] > 0 && sum[cnt[num]] == 0) {
//        delNode(cnt[num]);
//    }
//    cnt[num]--;
//    if (cnt[num] > 0 && sum[cnt[num]] == 0) {
//        addNode(cnt[num]);
//    }
//    if (cnt[num] > 0) {
//        sum[cnt[num]] += num;
//    }
//}
//
//void setAns(int len, int mod, int id) {
//    int blen = (int)sqrt(len);
//    int bnum = (len + blen - 1) / blen;
//    smlPower[0] = 1;
//    for (int i = 1; i <= blen; i++) {
//        smlPower[i] = (smlPower[i - 1] << 1) % mod;
//    }
//    bigPower[0] = 1;
//    for (int i = 1; i <= bnum; i++) {
//        bigPower[i] = (bigPower[i - 1] * smlPower[blen]) % mod;
//    }
//    long long res = 0, tmp;
//    for (int p = head; p > 0; p = nxt[p]) {
//        tmp = bigPower[len / blen] * smlPower[len % blen] % mod;
//        tmp -= bigPower[(len - p) / blen] * smlPower[(len - p) % blen] % mod;
//        tmp = (tmp * sum[p]) % mod;
//        res = ((res + tmp) % mod + mod) % mod;
//    }
//    ans[id] = res;
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int mod = query[i].mod;
//        int id = query[i].id;
//        while (winl > jobl) {
//            add(arr[--winl]);
//        }
//        while (winr < jobr) {
//            add(arr[++winr]);
//        }
//        while (winl < jobl) {
//            del(arr[winl++]);
//        }
//        while (winr > jobr) {
//            del(arr[winr--]);
//        }
//        setAns(jobr - jobl + 1, mod, id);
//    }
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query[i].l >> query[i].r >> query[i].mod;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}