FROM openjdk:17-jdk-alpine

# 애플리케이션 JAR 파일을 이미지에 복사
# ARG JAR_FILE=build/libs/collector.jar
#COPY ${JAR_FILE} ./app.jar

# 로컬 - 빌드 컨텍스트 - 이미지
# 빌드 컨텍스트에서의 파일을 이미지로 복사함.
COPY . .


# 컨테이너에서 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "./collector.jar"]