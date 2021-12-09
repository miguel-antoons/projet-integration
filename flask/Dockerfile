# Use the Python3.7.2 image
FROM python:3.7.2-stretch

# check/install update and tools add
RUN apt update && apt upgrade -y 
RUN apt install iputils-ping -y  
RUN apt install net-tools -y
RUN apt install nano -y
RUN apt install sudo -y

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app 
ADD . /app

# Install the dependencies
RUN pip install -r requirements.txt

#EXPOSE PORT FLASK FLASK
EXPOSE 587/tcp

# run the command to start uWSGI
CMD ["uwsgi", "app.ini"]
