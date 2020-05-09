FROM ubuntu 
RUN apt-get update
RUN apt-get install -y nginx
COPY index.nginx-debian.html /var/www/html
EXPOSE 80
CMD nginx -g 'daemon off;'