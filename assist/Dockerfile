FROM alpine:latest AS builder

ENV GRADLE_VERSION 3.2

WORKDIR /
RUN apk update && \
    apk add git nodejs npm openjdk11 && \
    git clone --depth=1 https://github.com/ustcwudi/service

WORKDIR /service
RUN chmod +x gradlew
RUN ./gradlew :generator:bootRun --args=assist

WORKDIR /service/web
RUN npm install && npm run build
RUN mv /service/web/dist /service/assist/src/main/resources/static

WORKDIR /service
RUN ./gradlew :assist:build

FROM alpine:latest AS final
RUN apk update && \
    apk add openjdk11-jre

WORKDIR /
COPY --from=builder /service/assist/build/libs/assist-0.0.1.jar .
COPY --from=builder /service/assist/data/ ./data/

LABEL com.centurylinklabs.watchtower.enable="true"

CMD java -jar assist-0.0.1.jar
