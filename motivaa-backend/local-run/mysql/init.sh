#!/bin/bash

echo "Creating motivaa_admin user"
cat <<EOF > /docker-entrypoint-initdb.d/init.sql
CREATE USER 'motivaa_admin'@'%' IDENTIFIED BY '${MOTIVAA_ADMIN_PASSWORD}';
GRANT ALL PRIVILEGES ON *.* TO 'motivaa_admin'@'%';
FLUSH PRIVILEGES;
EOF
echo "If no error above, motivaa_admin user successfully created"