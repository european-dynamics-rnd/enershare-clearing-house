# **Clearing House Deployment guide**

Prerequisites:
```
Docker
Git
java 17
Maven 3.8.x (or later)
Node.js ^12.20.0 || ^14.15.0 || ^16.10.0
```

1. The first step is to clone this repository <https://github.com/european-dynamics-rnd/enershare-clearing-house> , by typing:
```

git clone https://github.com/european-dynamics-rnd/enershare-clearing-house.git
```
2. For backend, you should build the spring application running 
following command<br>
``mvn clean package``
3. Copy the **enershare-0.0.1-SNAPSHOT.jar** file from the target folder to the **deployment/eneshare-backend** directory.
4. In the **frontend/src/environments** folder, locate the environment.prod.ts file. Update it 
by setting the **serverUrl** to the URL of your backend application. 
5. After this you can build the angular project running the following commands
```
npm install 
ng build --configuration production
```
6. Copy the dist folder to **deployment/enershare-frontend**
7. In the Dockerfile located under **deployment/backend**, 
configure the **cors.allowed-origins** setting by specifying the URL of your frontend application.

8.There is the docker-compose.yml file located under deployment directory. 
Run following command to start the containers
```
$docker-compose up –d --build
$docker-compose logs -f
```