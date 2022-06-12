FROM golang:1.16-alpine AS builder

ENV GRADLE_VERSION 3.2

WORKDIR /
RUN apk update && \
    apk add git nodejs npm curl unzip && \
    git clone --depth=1 https://github.com/ustcwudi/service && \
    curl https://downloads.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip > gradle.zip && \
    unzip gradle.zip

ENV PATH "$PATH:/gradle-${GRADLE_VERSION}/bin/"

WORKDIR /service
RUN gradlew :generator:bootRun --args=assist

WORKDIR /service/web
RUN npm install && npm run build
RUN mv /service/web/dist /service/assist/src/main/resources/static

WORKDIR /service
RUN gradlew :assist:build

FROM alpine:latest AS final
RUN apk update && \
    apk add openjdk11-jre

WORKDIR /
COPY --from=builder /service/assist/build/libs/assist-0.0.1.jar .

LABEL com.centurylinklabs.watchtower.enable="true"

CMD java run -jar assist-0.0.1.jar
