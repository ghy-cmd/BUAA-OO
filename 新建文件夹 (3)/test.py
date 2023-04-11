import os
for i in range(464):
    os.system("java -jar main.jar <testpoint%d.stdin >out.txt"%i)
    res=os.system("fc testpoint%d.stdout out.txt"%i)
    if(res!=0):
        exit(114514)
