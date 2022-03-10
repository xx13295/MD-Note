# 

C:\Program Files\RDP Wrapper

在 rdpwrap.ini 里面追加。

然后重新update

最好能用vnc 救援 哈哈 万一等下 rdp没启动就连不上了。

10.0.17763.1 看Service state [Running]  后面的ver. 获取


可能要用到的命令
>net stop termservice

>net start termservice

```
[10.0.17763.1]
LocalOnlyPatch.x86=1
LocalOnlyOffset.x86=AF8E4
LocalOnlyCode.x86=jmpshort
LocalOnlyPatch.x64=1
LocalOnlyOffset.x64=77941
LocalOnlyCode.x64=jmpshort
SingleUserPatch.x86=1
SingleUserOffset.x86=4D505
SingleUserCode.x86=nop
SingleUserPatch.x64=1
SingleUserOffset.x64=1322C
SingleUserCode.x64=Zero
DefPolicyPatch.x86=1
DefPolicyOffset.x86=4BD09
DefPolicyCode.x86=CDefPolicy_Query_eax_ecx
DefPolicyPatch.x64=1
DefPolicyOffset.x64=17F45
DefPolicyCode.x64=CDefPolicy_Query_eax_rcx
SLInitHook.x86=1
SLInitOffset.x86=5B02A
SLInitFunc.x86=New_CSLQuery_Initialize
SLInitHook.x64=1
SLInitOffset.x64=1ABFC
SLInitFunc.x64=New_CSLQuery_Initialize
 
[10.0.17763.1-SLInit]
bInitialized.x86      =CD798
bServerSku.x86        =CD79C
lMaxUserSessions.x86  =CD7A0
bAppServerAllowed.x86 =CD7A8
bRemoteConnAllowed.x86=CD7AC
bMultimonAllowed.x86  =CD7B0
ulMaxDebugSessions.x86=CD7B4
bFUSEnabled.x86       =CD7B8

bInitialized.x64      =ECAB0
bServerSku.x64        =ECAB4
lMaxUserSessions.x64  =ECAB8
bAppServerAllowed.x64 =ECAC0
bRemoteConnAllowed.x64=ECAC4
bMultimonAllowed.x64  =ECAC8
ulMaxDebugSessions.x64=ECACC
bFUSEnabled.x64       =ECAD0

```


