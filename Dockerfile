FROM maven:3.9-amazoncorretto-20
WORKDIR /app
COPY . .
RUN mvn clean compile package dependency:copy-dependencies
ENV CLASSPATH="/app/target/dependency/*"
CMD ["/usr/lib/jvm/java-20-amazon-corretto/bin/java", "-Xmx4g",  "--add-modules", "jdk.incubator.vector", "-cp", "target/benchmarks.jar", "org.openjdk.jmh.Main"]

