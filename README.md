
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
**2. Spring boot app**
* Clone the repository:
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
Go to [http://localhost:8080/demo/api/automobiles](http://localhost:8080/demo/api/automobiles) to test and would specify basic authorization a username: `user` and password: `user` or username: `admin` and password: `admin`

* GET request to `/demo/api/automobiles/` returns a list of "automobiles";
* GET request to `/demo/api/automobiles/1` returns the "automobile" with ID 1;
* POST request to `/demo/api/automobiles/` with a "automobile" object as JSON creates a new "automobile";
* PUT request to `/demo/api/automobiles/3` with a "automobile" object as JSON updates the "automobile" with ID 3;
* DELETE request to `/demo/api/automobiles/4` deletes the "automobile" with ID 4;
* DELETE request to `/demo/api/automobiles/` deletes all the "automobiles".

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



