import math
"""
 Problem: Refract Facts
 Team: Symbol Banging Monkeys
"""
line = input()
line_list = line.split(" ")
d = float(line_list[0])
h = float(line_list[1])
x = float(line_list[2])
n_1 = float(line_list[3])
n_2 = float(line_list[4])

p = 0.1
min_dif = 1000
while p <= x:
    phi = math.atan(d/p)
    top = n_2 * d * math.sqrt( (h**2) + ((x-p)**2) )
    bottom = n_1 * (x - p) * math.sqrt( (d**2) + (p**2) )
    dif = abs(math.atan(top/bottom)*180/math.pi - (phi*180/math.pi))
    if dif < min_dif:
        deg_phi = phi*180/math.pi
        min_dif = dif
    
    p += 0.01

print ("%.2f" % deg_phi)
