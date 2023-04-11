import subprocess
import os
import json
import time
from mpmath.libmp.libmpf import prec_to_dps
from sympy.core.sympify import sympify
CI=False
def main():
    path=os.getcwd()
    with open("config.json","r")as config:
        configlist=json.loads(config.readline())
    name='src'
    batch = 0
    mainclass, mainclasspath, root = configlist[name]
    while(1):
        for count in range(100):
            with open("input.txt","w") as inputt:
                subprocess.Popen(["python","test/gen.py"],stdout=inputt).wait()
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
                print("input is:")
                print(originline)
                print("expected ans:")
                print(fo)
                print("your output:")
                print(fx)
                exit(1)
        
        if(CI):
            exit(0)
        batch=batch+1
        print("finish batch %s"%str(batch))
if(__name__=="__main__"):
    main()
