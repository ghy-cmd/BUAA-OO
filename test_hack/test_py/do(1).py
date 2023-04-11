import os
import random
import sys
import time
from multiprocessing import Process
from typing import OrderedDict
from mpmath.libmp.libmpf import prec_to_dps
from sympy.core.sympify import sympify
from xeger import Xeger
import subprocess
import sympy
count = 0
def run(fx,gy):
    for j in range(25):
        u=random.random()*1
        ux=fx.evalf(subs ={'x':u})
        uy=fy.evalf(subs ={'x':u})
        if(ux!=uy):
            print(ux,uy)
            sys.exit(1)

def creat_factor():
    x = Xeger(limit=25)
    return x.xeger(r'((x([ \t]\*\*[ \t]{0,2}[+-]?[1-9]{1,2})?)|([+-]?[1-9]{1,5}))')

def get_blank():
    x = Xeger(limit=25)
    return x.xeger(r'[ \t]{0,2}')
def creat_term():
    t = random.randint(0, 2)
    x = Xeger(limit=25)

    str = ""
    if(t):
        str = "+"
    else:
        str = "-"
    return str+get_blank()+creat_factor()+get_blank()+"*"+get_blank()+creat_factor()+get_blank()+"*"+get_blank()+creat_factor()
def getsign():
    t = random.randint(0, 1)
    str = ""
    if(t):
        str = "+"
    else:
        str = "-"
    return str
def creat_expr():
    t = random.randint(0, 2)
    x = Xeger(limit=25)
    str = ""
    if(t):
        str = "+"
    else:
        str = "-"
    t=random.randint(1,3)
    res=str+get_blank()+creat_term()
    for i in range(t):
        res+=getsign()+get_blank()+creat_term()
    res+=get_blank()
    return res
time0=0
time1=0
timegsw=0
timegxy=0
time2=0
time3=0
orderdict={}
orderdict['peo1']=['java -classpath ./peo1/out Main']
orderdict['peo2']=['java -classpath ./peo2/out Main']
orderdict['peo3']=['java -classpath ./peo3/out MainClass']
orderdict['peo4']=['java -classpath ./peo4/out MainClass']
orderdict['peo5']=['java -classpath ./peo5/out MainClass']
orderdict['peo6']=['java -classpath ./peo6/out Tester']
while(1):
    pre=time.time()
    with open("input.txt","w") as input:
        input.write(creat_expr())
    inp=open("input.txt","r")
    inp2=open("input.txt","r")
    gxy=open("gxy.txt","w")
    gsw=open("gsw.txt","w")
    p1=subprocess.Popen('java -classpath ./peo1/out Main',stdin=inp,stdout=gxy)
    p2=subprocess.Popen('java -classpath ./peo2/out Main',stdin=inp2,stdout=gsw)
    while(subprocess.Popen.poll(p1)==None or subprocess.Popen.poll(p2)==None):
        pass
    with open("gsw.txt", "r") as gsw:
        gswline=gsw.readline()
        gswlen=len(gswline)
        fx = sympify(gswline)
    with open("input.txt", "r") as origin:
        fo = sympify(origin.readline().replace(" ","").replace("\t","")).diff()
    with open("gxy.txt", "r") as gxy:
        gxyline=gxy.readline()
        gxylen=len(gxyline)
        fy = sympify(gxyline)
    if(fo-fy!=0):
        break
    if(fx-fo!=0):
        break
    if(fx-fy!=0):
        break
    print(count)
    count += 1
    time0+=time.time()-pre
    print(time0)
    if(count % 100 == 0):
        print(time.time())