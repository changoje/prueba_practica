FROM openjdk:17-jdk-slim
COPY "./build/libs/spf-msa-account-1.0.1.jar" "app.jar"
EXPOSE 8080
EXPOSE 5432
ENTRYPOINT ["java","-jar","app.jar"]