#Use the offical MongoDB image
FROM mongo:latest

# check/install update and tools add
RUN apt update && apt upgrade -y 
RUN apt install iputils-ping -y  
RUN apt install net-tools -y
RUN apt install nano -y
RUN apt install sudo -y
RUN apt install openssl -y

COPY mongod.conf /etc
