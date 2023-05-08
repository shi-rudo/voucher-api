# Use a multi-architecture base image
# docker build --build-arg BUILDPLATFORM=linux/arm64 -t myapp:arm64 .
# docker build --build-arg BUILDPLATFORM=linux/amd64 -t myapp:x86_64 .
FROM --platform=${BUILDPLATFORM} openjdk:17-oracle
EXPOSE 8080
RUN mkdir /app
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# docker run -p 8080:8080 myapp:arm64