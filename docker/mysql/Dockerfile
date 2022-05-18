# IMPORTANT when building this image note the expected files in copy --> context has to be in the project root
FROM mysql:8.0.29
COPY docs/teachu.sql /docker-entrypoint-initdb.d/a_schema.sql
COPY docs/teachu-data.sql /docker-entrypoint-initdb.d/b_data.sql

EXPOSE 3306