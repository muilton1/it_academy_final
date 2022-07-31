FROM postgres:14.4-alpine
MAINTAINER ITAcademy

ADD /classifier-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /events-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /user-service/ddl/*.sql /docker-entrypoint-initdb.d
ADD /user-service/dml/*.sql /docker-entrypoint-initdb.d