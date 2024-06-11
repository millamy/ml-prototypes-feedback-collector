FROM ubuntu:20.04

# Set the working directory
WORKDIR /usr/src/app

# Install JDK 21 and MongoDB with dependencies
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk gnupg wget && \
    wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | apt-key add - && \
    echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-4.4.list && \
    apt-get update && \
    apt-get install -y mongodb-org && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Python 3.8 and pip
RUN apt-get update && \
    apt-get install -y python3.8 python3-pip

# Install necessary Python packages
RUN pip3 install numpy==1.20.0 \
                matplotlib==3.3.4 \
                Pillow==8.1.0 \
                opencv-python-headless==4.5.1.48 \
                argparse==1.4.0 \
                torch==1.7.1 \
                torchvision==0.8.2

# Create MongoDB data directory
RUN mkdir -p /data/db

# Create a file to indicate that the application is running in a Docker container
RUN touch /usr/src/app/RUNNING_IN_DOCKER

# Copy the entire project into the container
COPY . /usr/src/app/

# Give execute permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the Spring Boot application using the Gradle wrapper, skipping tests
RUN ./gradlew clean build -x test

# Expose the necessary ports
EXPOSE 8080 27017

# Copy the startup script into the container and give it execute permission
COPY start.sh /usr/src/app/start.sh
RUN chmod +x /usr/src/app/start.sh

# Set the command to run the startup script
CMD ["/usr/src/app/start.sh"]
