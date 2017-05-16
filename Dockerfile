FROM ubuntu:14.04
MAINTAINER Ider Toufik
VOLUME /tmp
COPY . /learn
RUN sh -c 'touch /learn'
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get update -y && apt-get install -y gcc && apt-get install -y g++ && apt-get install -y python &&
    apt-get clean

RUN cd /learn

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", " cd /learn ; java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar ./build/libs/learn-0.0.1-SNAPSHOT.jar" ]



