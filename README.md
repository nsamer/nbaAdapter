### How to build

```mvn clean install```


### How to run

#### Prerequisites:
- java 21
- lombok plugin - if running in IntelliJ/ STS

### Local setup 
Start the application with a "local" Spring profile active + add a VM option `-DteleEnv=local`

1) application.properties 
The application defaults on port number 8065 unless specified in an env variable PORT
2) Run the application as Spring Boot, the main class is - NbaAdaptorApplication


