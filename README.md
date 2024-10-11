# **Clearing House Deployment guide**

To proceed with the installation of Clearing House, the user must use the */docker* folder of this repository that contains all the necessary configuration.

1. The first step is to clone this repository <https://github.com/european-dynamics-rnd/enershare-clearing-house> , by typing:
```
cd /opt/clearing-house/
git clone https://github.com/european-dynamics-rnd/enershare-clearing-house.git
```

2. There is the docker-compose.yml file located under /opt/onenet-true-connector/backend/docker that contains all the configuration of the onenet fiware true connector containers. Go to that file by typing the command
```
cd /opt/onenet-true-connector/backend/docker
```

3. :warning: At this step you must request from us a file with ".env" name and put it on the same folder /opt/onenet-true-connector/docker.
Email us on the helias.karagozidis@eurodyn.com to get the ".env" file.

4. After puting the ".env" file in position you can start the containers with the below commands  

```
$docker-compose up –d
$docker-compose logs -f
```
5. If no errors are seen, this means that clearing was successfully deployed on your premisses.
