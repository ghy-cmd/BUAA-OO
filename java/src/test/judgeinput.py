import subprocess
import os
import json
import time
from mpmath.libmp.libmpf import prec_to_dps
from sympy.core.sympify import sympify
path=os.getcwd()
with open("config.json", "r")as config:
    configlist = json.loads(config.readline())
name = 'homeworkthird2'
batch = 0
mainclass, mainclasspath, root = configlist[name]
pretime=time.time()
subprocess.Popen("java -cp "+path+"/out <input.txt >output.txt "+mainclass,shell=True).wait(timeout=10)
duration=time.time()-pretime
with open("output.txt", "r") as output:
    outputline=output.readline()
    fx = sympify(outputline)
with open("input.txt", "r") as origin:
    originline=origin.readline()
    fo = sympify(originline.replace(" ","").replace("\t","")).diff()
if(duration>2):
    print("TLE warning,your time is:"+str(duration))
    print(originline)
if(not fx.equals(fo)):
    print((fx-fo).simplify())
    print("input is:")
    print(originline)
    print("expected ans:")
    print(fo)
    print("your output:")
    print(fx)
    exit(1)
else:
    print("this input is ok!")
