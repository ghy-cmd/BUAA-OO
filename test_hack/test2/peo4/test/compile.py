import re
import os
import subprocess
import json
NEED_COMPILE=True
def judge(path):
    print(path)
    with open(path, "r",encoding='utf-8') as file:
        content = file.read()
    content = content.replace(" ", "")
    if("publicstaticvoidmain" in content):
        content = content.split("publicstaticvoidmain")[0]
        mainclass = re.search(r"publicclass(.+?){", content).group(1)
    else:
        mainclass = None
    if("package" in content):

        temp = re.search(r"package(.+);", content).group(1)
        if(mainclass != None):
            mainclass = temp+"."+mainclass
        temp = temp.replace(".", "\\\\")
        sring=r"(.+)"+temp+"(.+)"
        root = re.search(sring, path).group(1)

    else:
        root = None
    return root, mainclass


def traversalDir_FirstDir(path):
    list = []
    if (os.path.exists(path)):
        files = os.listdir(path)
        for file in files:
            m = os.path.join(path, file)
            if (os.path.isdir(m)):
                h = os.path.split(m)
                print(h[1])
                list.append(h[1])
        return list


def compile():
    path = os.getcwd()
    try:
        file=open("config.json","r")
        configlist=json.loads(file.readline())
        file.close()
        if(NEED_COMPILE):
            for name in configlist:
                mainclass, mainclasspath, root = configlist[name]
                subprocess.Popen("cd "+path+"\\"+name+" && mkdir out",
                                shell=True).wait()
                subprocess.Popen(["javac","-encoding","utf-8","-sourcepath",root,"-d",path+"\\"+name+"\\out",mainclasspath]).wait()
        return configlist
    except:
        configlist={}
        allpath = ['src']
        for targetpath in allpath:
            files = []
            filelist = os.walk(path+"\\"+targetpath)
            for folder in filelist:
                for file in folder[2]:
                    print(file)
                    if(file.endswith(".java")):
                        files.append(folder[0]+"\\"+file)
            print(files)
            root = None
            mainclass = None
            mainclasspath = None
            for filepath in files:

                res1, res2 = judge(filepath)
                root = None
                if(res1 != None):
                    root = res1
                if(res2 != None):
                    mainclass = res2
                    mainclasspath = filepath
            if(root == None):
                root = os.path.dirname(mainclasspath)
            configlist[targetpath] = [mainclass, mainclasspath, root]
            for name in configlist:
                mainclass, mainclasspath, root = configlist[name]
                subprocess.Popen("mkdir out",
                                shell=True).wait()
                subprocess.Popen(["javac","-encoding","utf-8","-sourcepath",root,"-d",path+"\\out",mainclasspath]).wait()
        with open("config.json","w")as configfile:
            configfile.writelines(json.dumps(configlist))
            configfile.close()
        return configlist


def main():
    compile()
if(__name__ == "__main__"):
    main()
