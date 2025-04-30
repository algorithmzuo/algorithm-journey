package class167;

// 八纵八横，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3733
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct BitSet {
//    int len;
//    vector<int> arr;
//
//    BitSet() {
//        len = (1000 + 31) / 32;
//        arr.assign(len, 0);
//    }
//
//    BitSet(const string& s) {
//        len = (1000 + 31) / 32;
//        arr.assign(len, 0);
//        for (int i = 0, j = (int)s.size() - 1; i < (int)s.size(); i++, j--) {
//            set(i, s[j] - '0');
//        }
//    }
//
//    int get(int i) const {
//        return (arr[i / 32] >> (i % 32)) & 1;
//    }
//
//    void set(int i, int v) {
//        if (v) {
//            arr[i / 32] |= 1 << (i % 32);
//        } else {
//            arr[i / 32] &= ~(1 << (i % 32));
//        }
//    }
//
//    void copy(const BitSet& other) {
//        arr = other.arr;
//    }
//
//    void eor(const BitSet& other) {
//        for (int i = 0; i < len; i++) {
//            arr[i] ^= other.arr[i];
//        }
//    }
//
//    void clear() {
//        fill(arr.begin(), arr.end(), 0);
//    }
//};
//
//const int MAXN = 501;
//const int MAXQ = 1001;
//const int MAXT = 10001;
//const int BIT = 999;
//
//int n, m, q;
//int x[MAXQ];
//int y[MAXQ];
//BitSet w[MAXQ];
//int edgeCnt = 0;
//int last[MAXQ];
//
//BitSet basis[BIT + 1];
//int inspos[BIT + 1];
//int basiz = 0;
//
//int father[MAXN];
//int siz[MAXN];
//BitSet eor[MAXN];
//int rollback[MAXT][2];
//int opsize = 0;
//
//int head[MAXQ << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//BitSet tow[MAXT];
//int cnt = 0;
//
//BitSet ans[MAXQ];
//
//void insert(BitSet num) {
//    for (int i = BIT; i >= 0; i--) {
//        if (num.get(i)) {
//            if (basis[i].get(i) == 0) {
//                basis[i].copy(num);
//                inspos[basiz++] = i;
//                return;
//            }
//            num.eor(basis[i]);
//        }
//    }
//}
//
//BitSet maxEor() {
//    BitSet res = BitSet();
//    for (int i = BIT; i >= 0; i--) {
//        if (res.get(i) == 0 && basis[i].get(i) == 1) {
//            res.eor(basis[i]);
//        }
//    }
//    return res;
//}
//
//void cancel(int oldsiz) {
//    while (basiz > oldsiz) {
//        basis[inspos[--basiz]].clear();
//    }
//}
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//BitSet getEor(int i) {
//    BitSet res = BitSet();
//    while (i != father[i]) {
//        res.eor(eor[i]);
//        i = father[i];
//    }
//    return res;
//}
//
//bool Union(int u, int v, const BitSet& w) {
//    int fu = find(u);
//    int fv = find(v);
//    BitSet weight = BitSet();
//    weight.eor(getEor(u));
//    weight.eor(getEor(v));
//    weight.eor(w);
//    if (fu == fv) {
//        insert(weight);
//        return false;
//    }
//    if (siz[fu] < siz[fv]) {
//        int tmp = fu;
//        fu = fv;
//        fv = tmp;
//    }
//    father[fv] = fu;
//    siz[fu] += siz[fv];
//    eor[fv].copy(weight);
//    rollback[++opsize][0] = fu;
//    rollback[opsize][1] = fv;
//    return true;
//}
//
//void undo() {
//    int fu = rollback[opsize][0];
//    int fv = rollback[opsize--][1];
//    father[fv] = fv;
//    eor[fv].clear();
//    siz[fu] -= siz[fv];
//}
//
//void addEdge(int i, int u, int v, const BitSet& w) {
//    nxt[++cnt] = head[i];
//    tox[cnt] = u;
//    toy[cnt] = v;
//    tow[cnt].copy(w);
//    head[i] = cnt;
//}
//
//void add(int jobl, int jobr, int jobx, int joby, const BitSet& jobw, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, joby, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, jobw, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, jobw, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int oldsiz = basiz;
//    int unionCnt = 0;
//    for (int e = head[i]; e > 0; e = nxt[e]) {
//        if (Union(tox[e], toy[e], tow[e])) {
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        ans[l] = maxEor();
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    cancel(oldsiz);
//    for (int k = 1; k <= unionCnt; k++) {
//        undo();
//    }
//}
//
//void print(const BitSet& bs) {
//    bool flag = false;
//    for (int i = BIT, s; i >= 0; i--) {
//        s = bs.get(i);
//        if (s == 1) {
//            flag = true;
//        }
//        if (flag) {
//            cout << s;
//        }
//    }
//    if (!flag) {
//        cout << '0';
//    }
//    cout << '\n';
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 0; i <= BIT; i++) {
//        basis[i] = BitSet();
//    }
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//        eor[i] = BitSet();
//    }
//    int u, v;
//    string str;
//    for (int i = 1; i <= m; i++) {
//        cin >> u >> v >> str;
//        Union(u, v, BitSet(str));
//    }
//    ans[0] = maxEor();
//    string op;
//    int k;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == "Add") {
//            ++edgeCnt;
//            cin >> x[edgeCnt] >> y[edgeCnt];
//            cin >> str;
//            w[edgeCnt] = BitSet(str);
//            last[edgeCnt] = i;
//        } else if (op == "Cancel") {
//            cin >> k;
//            add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//            last[k] = 0;
//        } else {
//            cin >> k;
//            add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//            cin >> str;
//            w[k] = BitSet(str);
//            last[k] = i;
//        }
//    }
//    for (int i = 1; i <= edgeCnt; i++) {
//        if (last[i] > 0) {
//            add(last[i], q, x[i], y[i], w[i], 1, q, 1);
//        }
//    }
//    if (q > 0) {
//        dfs(1, q, 1);
//    }
//    for (int i = 0; i <= q; i++) {
//        print(ans[i]);
//    }
//    return 0;
//}