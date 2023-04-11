import os
print("please input test file index:")
a=input()
os.system("java -jar main.jar <testpoint%s.stdin >out.txt"%a)
os.system("fc testpoint%s.stdout out.txt"%a)

