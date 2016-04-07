FROM ubuntu

MAINTAINER Rajiv Jha (rajiv.jha@appdynamics.com)

################## BEGIN INSTALLATION ######################
# Postgre Docker Installation Files

# set timezone to UTC
RUN mv /etc/localtime /etc/localtime.bak
RUN ln -s /usr/share/zoneinfo/Etc/UTC /etc/localtime

# Setup SSH
RUN apt-get update && apt-get -y -q install openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:welcome1' | chpasswd
RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config
# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd


RUN sudo gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 94558F59
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ precise-pgdg main" > /etc/apt/sources.list.d/pgdg.list

# Installing postgresql, Postgresql client and contrib
RUN apt-get -y -q install python-software-properties software-properties-common \
    && apt-get -y -q install postgresql-9.3 postgresql-client-9.3 postgresql-contrib-9.3

USER postgres
RUN /etc/init.d/postgresql start \
    && psql --command "CREATE USER pguser1 WITH SUPERUSER PASSWORD 'welcome1';" \
    && createdb -O pguser1 pgdb1

USER root
RUN rm /etc/postgresql/9.3/main/pg_hba.conf
ADD /pg_hba.conf /etc/postgresql/9.3/main/
RUN chmod 777 /etc/postgresql/9.3/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> /etc/postgresql/9.3/main/postgresql.conf
RUN rm /etc/ssh/ssh_host*
RUN sudo dpkg-reconfigure openssh-server

EXPOSE 5432
EXPOSE 22

RUN mkdir -p /var/run/postgresql && chown -R postgres /var/run/postgresql
VOLUME  ["/etc/postgresql", "/var/log/postgresql", "/var/lib/postgresql"]

USER postgres
CMD ["/usr/lib/postgresql/9.3/bin/postgres", "-D", "/var/lib/postgresql/9.3/main", "-c", "config_file=/etc/postgresql/9.3/main/postgresql.conf"]

RUN /etc/init.d/postgresql restart

