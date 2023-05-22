FROM amazoncorretto:20

WORKDIR /app

COPY . .

RUN mkdir -p target

RUN /usr/lib/jvm/java-20-amazon-corretto/bin/javac -d out --module-source-path "./*/src/main/java/" $(find . -name "*.java")

CMD ["/usr/lib/jvm/java-20-amazon-corretto/bin/java", "-ea", "-p", "out", "-m", "isAscii/com.github.pkpkpk.isAscii.Main"]

