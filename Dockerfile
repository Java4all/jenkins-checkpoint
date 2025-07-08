FROM maven:3.9.10-amazoncorretto-17
WORKDIR /app
COPY . /app
CMD ["mvn", "clean", "install", "-U", "-s", ".mvn/settings.xml"]
