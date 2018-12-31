FROM openjdk:8-jre
# FROM openjdk:8-jdk-alpine

# VOLUME 指向了一个/tmp的目录，由于 Spring Boot 使用内置的Tomcat容器，Tomcat 默认使用/tmp作为工作目录。这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp

MAINTAINER alfred.zhou <alfred0223@126.com>

ADD death-knight-service-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

# 为了缩短 Tomcat 的启动时间，添加java.security.egd的系统属性指向/dev/urandom作为 ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
