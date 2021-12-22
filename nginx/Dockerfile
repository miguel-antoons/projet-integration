# Use the Nginx image
FROM nginx
# check/install update and tools add
RUN apt update && apt upgrade -y 
RUN apt install iputils-ping -y  
RUN apt install net-tools -y
RUN apt install nano -y
RUN apt install sudo -y
RUN apt install openssl -y
RUN apt install python3-certbot-nginx -y
# Remove the default nginx.conf
RUN rm /etc/nginx/conf.d/default.conf

# Replace with our own nginx.conf
COPY nginx.conf /etc/nginx/

COPY headers_secure.conf /etc/nginx/snippets/headers_secure.conf
