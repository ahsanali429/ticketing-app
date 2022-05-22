FROM centos
RUN yum install -y java-1.8.0-openjdk-devel.x86_64 dos2unix
ENV JAVA_HOME /etc/alternatives/jre
RUN $JAVA_HOME/bin/java -version
RUN echo "Java installed successfully!!!"

WORKDIR /callsign-task-ticket-system
COPY . .
RUN echo "Copy path done"
RUN mvn clean install
RUN echo "Maven install done"
RUN echo "Starting Spring Boot application!!!"
CMD mvn spring-boot:run
RUN echo "Successfully start spring app"
