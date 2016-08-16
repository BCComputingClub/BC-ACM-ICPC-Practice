// Sample solution for Positive Con Sequences
// Author: Bryce Sandlund

#include <iostream>
#include <vector>
#include <cmath>

#define EP 1e-10

using namespace std;

typedef long long ll;
typedef vector<ll> vi;

// tests if the sequence is arithmetic
bool is_arith(vi &nums) {
    ll diff = nums[1] - nums[0];
    for (ll i = 1; i < 4; ++i) {
        if (nums[i] - nums[i-1] != diff)
            return false;
    }
    return true;
}

// tests if the sequence is geometric
bool is_geo(vi &nums) {
    double diff = (double)nums[1] / nums[0];
    for (ll i = 1; i < 4; ++i) {
        if (fabs((double)nums[i] / nums[i-1] - diff) > EP)
            return false;
    }
    return true;
}

// doesn't mess with epsilons, only works if the constant in the
// geometric sequence is an integer
bool is_geo_int(vi &nums) {
    if (nums[1] % nums[0] != 0)
        return false;
    ll diff = nums[1] / nums[0];
    for (ll i = 1; i < 4; ++i) {
        if (nums[i] % nums[i-1] != 0 || nums[i] / nums[i-1] != diff)
            return false;
    }
    return true;
}

int main() {
    for (ll cs = 1; true; ++cs) {
        vi nums(4);
        for (ll i = 0; i < 4; ++i) {
            cin >> nums[i];
        }

        if (nums[0] == -1 && nums[1] == -1)
            break;

        ll ans = -1;
        for (ll i = 0; i < 4; ++i) {
            if (nums[i] == -1) {
                // try all possibilities!
                for (ll j = 1; j <= 1000000; ++j) {
                    nums[i] = j;
                    if (is_arith(nums) || is_geo_int(nums)) {
                        ans = j;
                        break;
                    }
                }
            }
        }

        cout << ans << endl;
    }
    return 0;
}
