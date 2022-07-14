FROM openjdk:8-jdk-alpine as build
WORKDIR /account-service
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY account account
RUN ./mvnw install
RUN cd account/account-app && mkdir -p target/dependency  \
    && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
WORKDIR /app
ARG DEPENDENCY=/account-service/account/account-app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes .
ENV JAVA_OPTS=""
EXPOSE 8091 9091
ENTRYPOINT exec java $JAVA_OPTS -cp .:./lib/* com.av.finance.account.AccountServiceApplication