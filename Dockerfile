ARG BASE_IMAGE=ubuntu:bionic-20200311
FROM $BASE_IMAGE
ENV BASE_IMAGE=$BASE_IMAGE

LABEL maintainer "rio <riosun269@gmail.com>"

ENV DEBIAN_FRONTEND=noninteractive

#=============
# Set WORKDIR
#=============

WORKDIR /root

#==================
# General Packages
#------------------
# openjdk-8-jdk
#   Java
# ca-certificates
#   SSL client
# tzdata
#   Timezone
# zip
#   Make a zip file
# unzip
#   Unzip zip file
# curl
#   Transfer data from or to a server
# wget
#   Network downloader
# libqt5webkit5
#   Web content engine (Fix issue in Android)
# libgconf-2-4
#   Required package for chrome and chromedriver to run on Linux
# xvfb
#   X virtual framebuffer
# gnupg
#   Encryption software. It is needed for nodejs
# salt-minion
#   Infrastructure management (client-side)
#==================
RUN apt-get -qqy update && \
    apt-get -qqy --no-install-recommends install \
    openjdk-8-jdk \
    ca-certificates \
    tzdata \
    zip \
    unzip \
    curl \
    wget \
    libqt5webkit5 \
    libgconf-2-4 \
    xvfb \
    gnupg \
    salt-minion \
  && rm -rf /var/lib/apt/lists/*

#===============
# Set JAVA_HOME
#===============
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64/jre" \
    PATH=$PATH:$JAVA_HOME/bin

#=====================
# Install Android SDK
#=====================
ARG SDK_VERSION=sdk-tools-linux-3859397
ARG ANDROID_BUILD_TOOLS_VERSION=26.0.0
ARG ANDROID_PLATFORM_VERSION="android-25"

ENV SDK_VERSION=$SDK_VERSION \
    ANDROID_BUILD_TOOLS_VERSION=$ANDROID_BUILD_TOOLS_VERSION \
    ANDROID_HOME=/root

RUN wget -O tools.zip https://dl.google.com/android/repository/${SDK_VERSION}.zip && \
    unzip tools.zip && rm tools.zip && \
    chmod a+x -R $ANDROID_HOME && \
    chown -R root:root $ANDROID_HOME

ENV PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin

# https://askubuntu.com/questions/885658/android-sdk-repositories-cfg-could-not-be-loaded
RUN mkdir -p ~/.android && \
    touch ~/.android/repositories.cfg && \
    echo y | sdkmanager "platform-tools" && \
    echo y | sdkmanager "build-tools;$ANDROID_BUILD_TOOLS_VERSION" "emulator" && \
    echo y | sdkmanager "platforms;$ANDROID_PLATFORM_VERSION"

RUN yes Y | $ANDROID_HOME/tools/bin/sdkmanager --licenses
RUN $ANDROID_HOME/tools/bin/sdkmanager --list
RUN $ANDROID_HOME/tools/bin/sdkmanager "system-images;android-25;google_apis;x86"
RUN echo "no" | $ANDROID_HOME/tools/bin/avdmanager --verbose create avd --force --name "test" --device "pixel" --package "system-images;android-25;google_apis;x86" --tag "google_apis" --abi "x86"
RUN $ANDROID_HOME/emulator/emulator -list-avds

#RUN apt-add-repository main
RUN addgroup libvirt
RUN addgroup kvm
RUN adduser `id -un` libvirt
RUN adduser `id -un` kvm
RUN apt-get -y update && DEBIAN_FRONTEND=noninteractive apt-get install -y qemu-kvm libvirt-bin ubuntu-vm-builder bridge-utils

ENV PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/build-tools

#====================================
# Install latest nodejs, npm, appium
# Using this workaround to install Appium -> https://github.com/appium/appium/issues/10020 -> Please remove this workaround asap
#====================================
ARG APPIUM_VERSION=1.18.0
ENV APPIUM_VERSION=$APPIUM_VERSION

RUN curl -sL https://deb.nodesource.com/setup_12.x | bash && \
    apt-get -qqy install nodejs && \
    npm install -g appium@${APPIUM_VERSION} --unsafe-perm=true --allow-root && \
    exit 0 && \
    npm cache clean && \
    apt-get remove --purge -y npm && \
    apt-get autoremove --purge -y && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    apt-get clean

#================================
# Install Gradle
#================================
RUN wget https://services.gradle.org/distributions/gradle-5.4.1-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-5.4.1-bin.zip

RUN mkdir /opt/gradlew \
    && /opt/gradle/gradle-5.4.1/bin/gradle wrapper --gradle-version 5.4.1 --distribution-type all -p /opt/gradlew \
    && /opt/gradle/gradle-5.4.1/bin/gradle wrapper -p /opt/gradlew

#================================
# Install maven 3.3.9
#================================
RUN wget --no-verbose -O /tmp/apache-maven-3.3.9-bin.tar.gz http://www-eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz && \
    tar xzf /tmp/apache-maven-3.3.9-bin.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-3.3.9 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin  && \
    rm -f /tmp/apache-maven-3.3.9-bin.tar.gz

ENV MAVEN_HOME /opt/maven

#================================
# APPIUM Test Distribution (ATD)
#================================
ARG ATD_VERSION=1.2
ENV ATD_VERSION=$ATD_VERSION
RUN wget -nv -O RemoteAppiumManager.jar "https://github.com/AppiumTestDistribution/ATD-Remote/releases/download/${ATD_VERSION}/RemoteAppiumManager-${ATD_VERSION}.jar"

#================================
# Setup environment
#================================
ENV GRADLE_HOME=/opt/gradle/gradle-5.4.1
ENV LD_LIBRARY_PATH=$ANDROID_HOME/emulator/lib64:$ANDROID_HOME/emulator/lib64/qt/lib

#==================================
# Fix Issue with timezone mismatch
#==================================
ENV TZ="US/Pacific"
RUN echo "${TZ}" > /etc/timezone

#===============
# Expose Ports
#---------------
# 4723
#   Appium port
# 4567
#   ATD port
#===============
EXPOSE 4723
EXPOSE 4567

#====================================================
# Scripts to run appium and connect to Selenium Grid
#====================================================
#COPY entry_point.sh \
#     generate_config.sh \
#     wireless_connect.sh \
#     wireless_autoconnect.sh \
#     /root/

#RUN chmod +x /root/entry_point.sh && \
#    chmod +x /root/generate_config.sh && \
#    chmod +x /root/wireless_connect.sh && \
#    chmod +x /root/wireless_autoconnect.sh

#========================================
# Run xvfb and appium server
#========================================
#CMD /root/wireless_autoconnect.sh && /root/entry_point.sh

#========================================
# Run emulator
#========================================
ENV PROJECT_HOME /home/src/app
COPY ./ ${PROJECT_HOME}
WORKDIR ${PROJECT_HOME}
RUN chmod +x packageTest.sh
RUN ./packageTest.sh
#RUN bash runEmulator.sh