
## Lightweight RESTful API with Spring Boot, PostgreSQL, JPA, Docker, Lombok, Spotify plugin, etc.

[![Build Status](https://travis-ci.org/OKaluzny/spring-boot-docker-postgres.svg?branch=master)](https://travis-ci.org/OKaluzny/spring-boot-docker-postgres)

## How it works:
**1. Docker. First you need to install docker**
* Download Docker [Here](https://localhost:8443/api/v1/objects). Hint: Enable Hyper-V feature on windows and restart;
* Then open powershell and check:
```bash
docker info
```
or, and you see versions docker & docker compose
```bash
docker -v
```
```bash
docker-compose -v
```
* Now we need to set up postgres database. We can get it from the Docker Hub. But we need to create a volume for store data:
```bash
docker create -v /var/lib/postgresql/data --name PostgresData alpine
```
* Create the database:
```bash
docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=postgres -d --volumes-from PostgresData postgres
```

**2. Spring boot app**
* Clone the repository
```bash
git clone https://github.com/OKaluzny/spring-boot-docker-postgres.git
```
* Build the maven project:
```bash
mvn clean install
```
* Now run:
```bash
docker-compose up
```
* Go to [http://localhost:8080/api/automobiles](http://localhost:8080/api/automobiles) to test and must specify a username: `user` and password: `user`

**3. Docker control commands**
* Check all the images you have:
```bash
docker images
```
* If you have to want see running containers:
```bash
docker ps
```
**4. End stop app**
*  Stop containers:
```bash
docker-compose down
```



