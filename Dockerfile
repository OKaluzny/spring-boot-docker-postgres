FROM openjdk:17
ADD /target/spring-boot-keycloak-docker-postgres.jar spring-boot-keycloak-docker-postgres.jar
ENTRYPOINT ["java", "-jar", "spring-boot-keycloak-docker-postgres.jar"]