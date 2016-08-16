/*************************************************************
**                                                          **
**  Sample solution for "Lunacy"                            **
**       Steven J Zeil                                      **
**       10/16/2007                                         **
**                                                          **
*************************************************************/


#include <iomanip>
#include <iostream>


using namespace std;




int main()
{
  double earth, moon;
  while (cin >> earth && earth >= 0.0) 
    {
      moon = 0.167 * earth;
      cout << fixed << setprecision(2) << earth
	   << " "
	   << fixed << setprecision(2) << moon
	   << endl;
    }
  return 0;
}
