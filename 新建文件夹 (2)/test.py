import os
for i in range(86):
    os.system("java -jar main.jar <testpoint%d.stdin >out.txt"%i)
    os.system("fc testpoint%d.stdout out.txt"%i)
