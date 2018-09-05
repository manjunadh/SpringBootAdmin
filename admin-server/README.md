# spring boot admin Server

Run this project as a Spring Boot app (e.g. import into IDE and run main method.
OR Run from gradle parent project as gradle :admin-server:bootRun. 

ie. 
pwd
fgl-microservices/config/support-config

fgl-microservices/config/support-config $ gradle :admin-server:bootRun


It will start up on port 8761 - ie. http://localhost:8761/

## Resources

| Path             | Description  |
|------------------|--------------|
| /                        | Home page (HTML UI) listing service registrations          |
| /eureka/apps         | Raw registration metadata |
