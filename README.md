# Quarkus Demo Project: Order Service

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Requirements
* This application will consume [this rest service](https://github.com/devonfw-sample/devon4quarkus-reference). Please ensure to have it up and running and correctly configured (`application.properties`) before trying to test this application.

```
product-service-key/mp-rest/url=<external-service-url>
```
> **_NOTE:_**  The RestClient here is automatically generated based on the openapi file provided by external service. So if there is any change in the external service, please update the openapi file corresponding in `/main/resources/`

* The app uses data persistence and you need a working database to use it. You can start the DB containers using simple cmd:
 ```
docker-compose up
 ```                                                                        
If you want to use other DB, modify the params in `application.properties`

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
If the port is used, consider use `-Ddebug=<port>` to specify debug port manually.

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8081/q/dev/.

## Access your endpoints

Go to http://localhost:8081/order/v1

## OpenAPI & Swagger UI

With your app running, go to http://localhost:8081/q/swagger-ui to see the Swagger UI visualizing your API. You can access the YAML OpenAPI schema under http://localhost:8081/q/openapi


## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
