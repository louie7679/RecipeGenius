# Database Configuration
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.driver=org.postgresql.Driver"
# export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.url=${DB_URL}"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.user=${DB_USER}"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.password=${DB_PW}"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.databaseName=${DB_NAME}"
# 这个配置可能不是必需的，但还是将它保留了，以防万一。
# export JAVA_OPTS="$JAVA_OPTS -Ddatabase.portNumber=${DB_PORT}"

# Logging Configuration
export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.org.springframework=INFO"

# AWS Configuration
# export CATALINA_OPTS="$CATALINA_OPTS -Daws.region=${REGION}"
export JAVA_OPTS="$JAVA_OPTS -Daws.region=us-east-1"
export JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=${AWS_ACCESS_KEY_ID}"
export JAVA_OPTS="$JAVA_OPTS -Daws.secretKey=${AWS_SECRET_KEY}"

# Application Configuration
export JAVA_OPTS="$JAVA_OPTS -Dserver.port=8080"
export JAVA_OPTS="$JAVA_OPTS -DSECRET_KEY=${SECRET_KEY}"
export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=dev"
export JAVA_OPTS="$JAVA_OPTS -Djms.queue.name=${QUEUE_NAME}"

# Additional Configuration (commented out)
#export JAVA_OPTS="$JAVA_OPTS -ea"
#export JAVA_OPTS="$JAVA_OPTS -Xms2g"


