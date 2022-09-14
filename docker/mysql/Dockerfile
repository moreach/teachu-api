# IMPORTANT when building this image note the expected files in copy --> context has to be in the project root
FROM mysql:8.0.29

ARG STARTUP_SCRIPTS_DIR="."

COPY $STARTUP_SCRIPTS_DIR/teachu.sql /docker-entrypoint-initdb.d/a_schema.sql
COPY $STARTUP_SCRIPTS_DIR/teachu-data.sql /docker-entrypoint-initdb.d/b_data.sql

EXPOSE 3306