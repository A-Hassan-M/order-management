FROM maven:3.8.5-openjdk-17
WORKDIR /order-management
COPY ./pom.xml ./
#RUN mvn clean install
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
COPY . .
CMD mvn spring-boot:run