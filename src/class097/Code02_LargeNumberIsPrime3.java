// 判断较大的数字是否是质数(Miller-Rabin测试)
// C++同学可以提交如下代码
// 可以通过所有测试用例，核心在于第11行，整型成了128位
// 测试链接 : https://www.luogu.com.cn/problem/U148828

/*

#include <bits/stdc++.h>
using namespace std;

typedef __int128 ll;
typedef pair<int, int> pii;

template<typename T> inline T read() {
    T x = 0, f = 1; char ch = 0;
    for(; !isdigit(ch); ch = getchar()) if(ch == '-') f = -1;
    for(; isdigit(ch); ch = getchar()) x = (x << 3) + (x << 1) + (ch - '0');
    return x * f;
}

template<typename T> inline void write(T x) {
    if(x < 0) putchar('-'), x = -x;
    if(x > 9) write(x / 10);
    putchar(x % 10 + '0');
}

template<typename T> inline void print(T x, char ed = '\n') {
    write(x), putchar(ed);
}

ll t, n;

ll qpow(ll a, ll b, ll mod) {
    ll ret = 1;
    while(b) {
        if(b & 1) ret = (ret * a) % mod;
        a = (a * a) % mod;
        b >>= 1;
    }
    return ret % mod;
}

vector<ll> p = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

bool miller_rabin(ll n) {
    if(n < 3 || n % 2 == 0) return n == 2;
    ll u = n - 1, t = 0;
    while(u % 2 == 0) u /= 2, ++ t;
    for(auto a : p) {
        if(n == a) return 1;
        if(n % a == 0) return 0;
        ll v = qpow(a, u, n);
        if(v == 1) continue;
        ll s = 1;
        for(; s <= t; ++ s) {
            if(v == n - 1) break;
            v = v * v % n;
        }
        if(s > t) return 0; 
    }
    return 1;
}

int main() {
    t = read<ll>();
    while(t --) {
        n = read<ll>();
        if(miller_rabin(n)) puts("Yes");
        else puts("No");
    }
    return 0;
}

*/


