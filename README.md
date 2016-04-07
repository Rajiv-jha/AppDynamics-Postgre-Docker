# Docker-Postgres

Dockerfile and configuration files for Postgres-Docker

Building the container images 
-----------------------------

To Build the Postgres Container clone this repository and than cd to the relevant build directories and run:

-docker build -t appdynamics/ecommerce-postgre .

Running the container 
---------------------

To use the docker images run :

docker run -p 5432:5432 -p 2224:22 --name postgre -e POSTGRES_ROOT_PASSWORD=welcome1 -d  appdynamics/ecommerce-postgre

Performing CURD in the Container 
--------------------------------

-Login into the container : docker exec -it postgre bash 

-than login into User with DB : psql -U pguser1 -W pgdb1

Now you can perform any operation here 

Performing CURD through JDBC driver 
-----------------------------------


