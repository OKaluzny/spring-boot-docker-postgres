
## Lightweight RESTful API with Spring Boot, Keycloak, Docker, PostgreSQL, JPA, Lombok, Spotify plugin, OpenAPI, etc.

[![Build Status](https://travis-ci.org/OKaluzny/spring-boot-docker-postgres.svg?branch=master)](https://travis-ci.org/OKaluzny/spring-boot-docker-postgres)

## How it works:
**1. Docker. First, you need to install docker**
* Download Docker [Here](https://docs.docker.com/docker-for-windows/install/). Hint: Enable Hyper-V feature on windows and restart;
* Then open powershell and check:
```bash
docker info
```
or check docker version
```bash
docker -v
```
or docker compose version
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
* Running the containers:
  
This command will build the docker containers and start them.
```bash
docker-compose up
```
or

This is a similar command as above, except it will run all the processes in the background.
```bash
docker-compose -f docker-compose.yml up
```

Appendix A.

All commands should be run from project root (where docker-compose.yml locates)

* If you have to want to see running containers. Checklist docker containers
```bash
docker container list -a
```
or
```bash
docker-compose ps
```
* Screenshot

![Screenshot docker containers list](/images/screenshot1.png)
*Screenshot with runnings containers*

**3. Keycloak**

Create Initial Admin User
go to [http://localhost:8080/auth](http://localhost:8080/auth) and fill in the create initial admin form with username as `admin` and password as the `Pa55w0rd`.

![Screenshot](/images/screenshot2.png)
*Keycloak — Create Initial Admin*

Click Create and proceed to administration console and then login with your created initial admin.

![Screenshot](/images/screenshot3.png)
*Keycloak — Log In Admin*

The first screen Keycloak shows after login is the Master realm details.

![Screenshot](/images/screenshot4.png)
*Keycloak — Master Realm Details*

### Create a Realm

Let’s name our first realm `automobile`:

![Screenshot](/images/screenshot5.png)
*Keycloak — automobile*

### Create a Role

Roles are used to categorize the user. In an application, the permission to access resources is often granted to the role rather than the user. Admin, User, and Manager are all typical roles that may exist in an organization.

To create a role, click the “Roles” menu on the left followed by the “Add Role” button on the page.

![Screenshot](/images/screenshot6.png)
*Keycloak — Add Role*

### Create a User

Keycloak does not come with any pre-created user, so let’s create our first user, “Oleg” Click on the `Users` menu on the left and then click the `Add User` button.

![Screenshot](/images/screenshot7.png)
*Keycloak — Add User*

Oieg will require a password to login to Keycloak. Create a password credential for user your Oleg. Click on the “Credentials” tab and write both password and password confirmation as the `password`. Turn off the `Temporary password` switch so we do not need to change the password on the next login. Click “Set Password” to save your password credential.
Finally, you need to assign the created `PERSON` role to Oleg. To do this, click on the Role Mappings tab, select the PERSON role, and click on “add selected”.

### Add a Client

Clients are entities that will request the authentication of a user. Clients come in two forms. The first type of client is an application that wants to participate in single-sign-on. These clients just want Keycloak to provide security for them. The other type of client is one that is requesting an access token so that it can invoke other services on behalf of the authenticated user.

Let’s create a client that we will use to secure our Spring Boot REST service.

Click on the Clients menu on the left and then click on Add Client.

![Screenshot](/images/screenshot8.png)
*Keycloak — Add Client*

In the form, fill Client Id as `app`, select `OpenID Connect` for the Client Protocol and click Save.
In the client details form, we need to change the `Access Type` to `confidential` instead of defaulted public (where client secret is not required). Turn on the `“Authorization Enabled”` switch before you save the details.

### Request an Access Token

A client requests a security token by making a Token Exchange request to the token endpoint in Keycloak using the HTTP POST method.

Go to [http://localhost:8080/auth/realms/automobile/protocol/openid-connect/token](http://localhost:8080/auth/realms/automobile/protocol/openid-connect/token)
  
![Screenshot](/images/screenshot9.png)
*Postman — Token Exchange Request*

Then copy `access token` and paste 

![Screenshot](/images/screenshot10.png)
*Postman — Token Exchange Request*

or

![Screenshot](/images/screenshot11.png)
*Postman — Token Exchange Request*




**Guide for using endpoints the app:**

Go to [http://localhost:8088/demo/api/automobiles](http://localhost:8088/demo/api/automobiles) to test and would specify basic authorization a username: `user` and password: `user` or username: `admin` and password: `admin`

* GET request to `/api/automobiles/` returns a list of "automobiles";
* GET request to `/api/automobiles/1` returns the "automobile" with ID 1;
* POST request to `/api/automobiles/` with a "automobile" object as JSON creates a new "automobile";
* PUT request to `/api/automobiles/3` with a "automobile" object as JSON updates the "automobile" with ID 3;
* DELETE request to `/api/automobiles/4` deletes the "automobile" with ID 4;
* DELETE request to `/api/automobiles/` deletes all the "automobiles".
---
* GET request to `/api/automobiles?color=madeira-violet` returns the "automobile"`s with color madeira-violet;
* GET request to `/api/automobiles?name=BMW&color=techno-violet` returns the "automobile"`s with name BMW and color techno-violet;
* GET request to `/api/automobiles?colorStartsWith=Ma&page=0&size=2` returns the "automobile"`s with color which starts with "m". Included Pagination and sorting;

or use Swagger API [http://localhost:8088/demo/swagger-ui.html](http://localhost:8088/demo/swagger-ui.html)

and generation API docks [http://localhost:8088/demo/v3/api-docs.yaml](http://localhost:8088/demo/v3/api-docs.yaml)

* Do not forget, if you see db, open the Windows Services Manager on your Windows 10 computer and stop postgres

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
* Remove old stopped containers of docker-compose
```bash
docker-compose rm -f
```



