#M1 Basic HardCode Mode
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.driver=org.postgresql.Driver"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.org.springframework=INFO"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.region=us-east-2"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.url=jdbc:postgresql://172.17.0.3:5432/RecipeRecDB"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.user=admin"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.password=Training123!"
export JAVA_OPTS="$JAVA_OPTS -DSECRET_KEY=tengfei-ascending"
export JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=AKIAWCY2OUKORAAACQFS"
export JAVA_OPTS="$JAVA_OPTS -Daws.secretKey=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"
export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=dev"

#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.driver=org.postgresql.Driver"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
#export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.org.springframework=INFO"
#export CATALINA_OPTS="$CATALINA_OPTS -Daws.region=${REGION}"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.url=jdbc:postgresql://${DB_URL}:${DB_PORT}/${DB_NAME}"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.user=${DB_USER}"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.password=${DB_PW}"
#export JAVA_OPTS="$JAVA_OPTS -DSECRET_KEY=tengfei-ascending"
#export JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=${AWS_ACCESS_KEY_ID}"
#export JAVA_OPTS="$JAVA_OPTS -Daws.secretKey=${AWS_SECRET_KEY}"
#export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=${PROFILE}"

# docker run --name mvc-api-3 -p 8000:8080 -e REGION=us-east-2 -e DB_URL=172.17.0.3 -e DB_PORT=5432 -e DB_NAME=RecipeRecDB -e DB_USER=admin -e DB_PW=Training123! -e AWS_ACCESS_KEY_ID=AKIAWCY2OUKORAAACQFS -e AWS_SECRET_KEY=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 -e PROFILE=dev recipegenius:2