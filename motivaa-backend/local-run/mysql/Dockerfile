# Dockerfile
FROM mysql
COPY init.sh /tmp/init.sh
COPY createMotivaaDatabase.sql /docker-entrypoint-initdb.d/createMotivaaDatabase.sql
RUN chmod +x /tmp/init.sh