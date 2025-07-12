package class174;

// 未来日记，C++版
// 给定一个长度为n的数组arr，一共有m条操作，每条操作类型如下
// 操作 1 l r x y : arr[l..r]范围上，所有值x变成值y
// 操作 2 l r k   : arr[l..r]范围上，查询第k小的值
// 1 <= n、m、arr[i] <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4119
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//char buf[1000000], *p1 = buf, *p2 = buf;
//
//inline char getChar() {
//    return p1 == p2 && (p2 = (p1 = buf) + fread(buf, 1, 1000000, stdin), p1 == p2) ? EOF : *p1++;
//}
//
//inline int read() {
//    int s = 0;
//    char c = getChar();
//    while (!isdigit(c)) {
//        c = getChar();
//    }
//    while (isdigit(c)) {
//        s = s * 10 + c - '0';
//        c = getChar();
//    }
//    return s;
//}
//
//const int MAXN = 100001;
//const int MAXB = 401;
//int n, m;
//int arr[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int idxset[MAXN];
//int valset[MAXB][MAXN];
//int setval[MAXB][MAXN];
//
//int sum1[MAXB][MAXB];
//int sum2[MAXB][MAXN];
//int cnt1[MAXB];
//int cnt2[MAXN];
//
//void build(int b) {
//    for (int i = 1; i <= blen; i++) {
//        valset[b][setval[b][i]] = 0;
//    }
//    int cnt = 0;
//    for (int i = bl[b]; i <= br[b]; i++) {
//        if (valset[b][arr[i]] == 0) {
//            cnt++;
//            valset[b][arr[i]] = cnt;
//            setval[b][cnt] = arr[i];
//        }
//        idxset[i] = valset[b][arr[i]];
//    }
//}
//
//void writeArray(int b) {
//    for (int i = bl[b]; i <= br[b]; i++) {
//        arr[i] = setval[b][idxset[i]];
//    }
//}
//
//void innerUpdate(int l, int r, int x, int y) {
//    writeArray(bi[l]);
//    for (int i = l; i <= r; i++) {
//        if (arr[i] == x) {
//            sum1[bi[i]][bi[x]]--;
//            sum1[bi[i]][bi[y]]++;
//            sum2[bi[i]][x]--;
//            sum2[bi[i]][y]++;
//            arr[i] = y;
//        }
//    }
//    build(bi[l]);
//}
//
//void xtoy(int b, int x, int y) {
//    valset[b][y] = valset[b][x];
//    setval[b][valset[b][x]] = y;
//    valset[b][x] = 0;
//}
//
//void update(int l, int r, int x, int y) {
//    if (x == y || (sum2[bi[r]][x] - sum2[bi[l] - 1][x] == 0)) {
//        return;
//    }
//    for (int b = bi[n]; b >= bi[l]; b--) {
//        sum1[b][bi[x]] -= sum1[b - 1][bi[x]];
//        sum1[b][bi[y]] -= sum1[b - 1][bi[y]];
//        sum2[b][x] -= sum2[b - 1][x];
//        sum2[b][y] -= sum2[b - 1][y];
//    }
//    if (bi[l] == bi[r]) {
//        innerUpdate(l, r, x, y);
//    } else {
//        innerUpdate(l, br[bi[l]], x, y);
//        innerUpdate(bl[bi[r]], r, x, y);
//        for (int b = bi[l] + 1; b <= bi[r] - 1; b++) {
//            if (sum2[b][x] != 0) {
//                if (sum2[b][y] != 0) {
//                    innerUpdate(bl[b], br[b], x, y);
//                } else {
//                    sum1[b][bi[y]] += sum2[b][x];
//                    sum1[b][bi[x]] -= sum2[b][x];
//                    sum2[b][y] += sum2[b][x];
//                    sum2[b][x] = 0;
//                    xtoy(b, x, y);
//                }
//            }
//        }
//    }
//    for (int b = bi[l]; b <= bi[n]; b++) {
//        sum1[b][bi[x]] += sum1[b - 1][bi[x]];
//        sum1[b][bi[y]] += sum1[b - 1][bi[y]];
//        sum2[b][x] += sum2[b - 1][x];
//        sum2[b][y] += sum2[b - 1][y];
//    }
//}
//
//void addCnt(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        cnt1[bi[arr[i]]]++;
//        cnt2[arr[i]]++;
//    }
//}
//
//void clearCnt(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        cnt1[bi[arr[i]]] = cnt2[arr[i]] = 0;
//    }
//}
//
//int query(int l, int r, int k) {
//    int ans = 0;
//    bool inner = bi[l] == bi[r];
//    if (inner) {
//        writeArray(bi[l]);
//        addCnt(l, r);
//    } else {
//        writeArray(bi[l]);
//        writeArray(bi[r]);
//        addCnt(l, br[bi[l]]);
//        addCnt(bl[bi[r]], r);
//    }
//    int sumCnt = 0;
//    int vblock = 0;
//    for (int b = 1; b <= bi[MAXN - 1]; b++) {
//        int cnt = cnt1[b] + (inner ? 0 : sum1[bi[r] - 1][b] - sum1[bi[l]][b]);
//        if (sumCnt + cnt >= k) {
//            vblock = b;
//            break;
//        } else {
//            sumCnt += cnt;
//        }
//    }
//    for (int v = (vblock - 1) * blen + 1; v <= vblock * blen; v++) {
//        int cnt = cnt2[v] + (inner ? 0 : sum2[bi[r] - 1][v] - sum2[bi[l]][v]);
//        if (sumCnt + cnt >= k) {
//            ans = v;
//            break;
//        } else {
//            sumCnt += cnt;
//        }
//    }
//    if (inner) {
//        clearCnt(l, r);
//    } else {
//        clearCnt(l, br[bi[l]]);
//        clearCnt(bl[bi[r]], r);
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = 300;
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i < MAXN; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//        build(i);
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = 1; j < MAXB; j++) {
//            sum1[i][j] = sum1[i - 1][j];
//        }
//        for (int j = 1; j < MAXN; j++) {
//            sum2[i][j] = sum2[i - 1][j];
//        }
//        for (int j = bl[i]; j <= br[i]; j++) {
//            sum1[i][bi[arr[j]]]++;
//            sum2[i][arr[j]]++;
//        }
//    }
//}
//
//int main() {
//    n = read();
//    m = read();
//    for (int i = 1; i <= n; i++) {
//        arr[i] = read();
//    }
//    prepare();
//    for (int i = 1, op, l, r, x, y, k; i <= m; i++) {
//        op = read();
//        l = read();
//        r = read();
//        if (op == 1) {
//            x = read();
//            y = read();
//            update(l, r, x, y);
//        } else {
//            k = read();
//            printf("%d\n", query(l, r, k));
//        }
//    }
//    return 0;
//}