# ProtoBuf 
    
    protocol buffers 是一种语言无关支持 Java、C++、Python 等多种语言、
    平台无关、可扩展的序列化结构数据的方法它可用于（数据）通信协议、数据存储等。

    Protocol Buffers 是一种灵活，高效，自动化机制的结构数据序列化方法
    －可类比 XML，但是比 XML 更小（3 ~ 10倍）、更快（20 ~ 100倍）、更为简单。

    你可以定义数据的结构，然后使用特殊生成的源代码轻松的在各种数据流中
    使用各种语言进行编写和读取结构数据。你甚至可以更新数据结构，
    而不破坏由旧数据结构编译的已部署程序。


### 安装环境win

    主要用于编译 .proto 文件为 相应的语言文件

下载地址
>https://github.com/protocolbuffers/protobuf/releases

这里我选择protoc-3.15.8-win64
>https://github.com/protocolbuffers/protobuf/releases/download/v3.15.8/protoc-3.15.8-win64.zip

解压后 在环境变量 path中加入D:\protoc-3.15.8-win64\bin;

打开cmd输入 protoc --version  可以看到相应的版本信息说明安装成功

### 使用

创建 Message.proto文件 以下为例子

```aidl


syntax = "proto3"; //protobuf语法有 proto2和proto3两种，这里指定 proto3

option java_package = "plus.ojbk.protocol.protobuf";
option java_outer_classname = "Message";

message Message {
    string requestId = 1;
    CommandType cmd = 2;
    string content = 3;
    enum CommandType {
        NORMAL = 0; //常规业务消息
        HEARTBEAT_REQUEST = 1; //客户端心跳消息
        HEARTBEAT_RESPONSE = 2; //服务端心跳消息
    }
}


```

我们在上例中定义了一个名为 Message 的 消息，语法很简单，message 关键字后跟上消息名称：

```aidl

message xxx {

}

```

在.proto文件的目录下 打开cmd 输入 protoc Message.proto --java_out=./   敲回车


Message.proto 为你的.proto文件
--java_out=“你要输出的位置”    
./ 为当前目录


就会自动生成需要的 java类 

其他语言请自行查阅文档


参考资料:

>https://colobu.com/2017/03/16/Protobuf3-language-guide/