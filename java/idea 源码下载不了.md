## 有时候idea会出现cannot download sources的情况

我们只需要 打开idea右下角的terminal

输入

> mvn dependency:resolve -Dclassifier=sources

然后回车

稍等片刻：看到Build success 说明成功


就可以 查看源码了
