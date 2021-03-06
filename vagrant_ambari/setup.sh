#!/usr/bin/env bash

echo "hello"

sudo yum clean all

sudo echo never > /sys/kernel/mm/redhat_transparent_hugepage/defrag
sudo echo never > /sys/kernel/mm/redhat_transparent_hugepage/enabled

sudo echo "echo never > /sys/kernel/mm/redhat_transparent_hugepage/defrag" >> /etc/rc.local
sudo echo "echo never > /sys/kernel/mm/redhat_transparent_hugepage/enabled" >> /etc/rc.local

sudo yum -y install wget ntp java-1.8.0-openjdk java-1.8.0-openjdk-devel

sudo service iptables stop
sudo chkconfig iptables off
sudo service ntpd start
sudo chkconfig ntpd on

sudo sed -i 's/^SELINUX=.*/SELINUX=disabled/g' /etc/sysconfig/selinux
sudo sed -i 's/^SELINUX=.*/SELINUX=disabled/g' /etc/selinux/config
sudo setenforce 0

sed -i 's/^127.0.0.1.*/127.0.0.1 localhost localhost.localdomain localhost4 localhost4.localdomain4/' /etc/hosts

ssh-keygen -t rsa -N "" -f ~/.ssh/id_rsa


#Install Ambari
sudo yum install -y python27 git

wget -nv http://public-repo-1.hortonworks.com/ambari/centos6/2.x/updates/2.4.0.1/ambari.repo -O /etc/yum.repos.d/ambari.repo

sudo yum install -y ambari-server

ambari-server setup -s

ambari-server start

sudo yum install -y ambari-agent

ambari-agent start
