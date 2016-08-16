// Sample solution for Refract Facts
// Author: Bryce Sandlund

#include <iostream>
#include <cstdio>
#define _USE_MATH_DEFINES
#include <cmath>

using namespace std;

typedef long long ll;

int main() {
    double d, h, x, n1, n2;
    for (ll cs = 1; cin >> d && d; ++cs) {
        cin >> h >> x >> n1 >> n2;

        double hi = M_PI/2;
        double lo = 0;

        // binary searh for the angle!
        while (fabs(hi - lo) > 1e-5) {
            double mid = (hi+lo)/2;

            double y = d / tan(mid);
            
            bool too_low = y > x;

            if (!too_low) {
                double theta1 = M_PI - M_PI/2 - mid;
                double theta2 = asin((n2*sin(theta1))/n1);

                double a = (x-y) / tan(theta2);

                too_low = a < h;
            }

            if (too_low) {
                lo = mid;
            }
            else {
                hi = mid;
            }
        }

        printf("%.2lf\n", hi * 360.0/(2*M_PI));
    }
    return 0;
}
