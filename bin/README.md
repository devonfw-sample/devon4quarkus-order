# Quarkus Demo Project: Order Service
The application demonstrates a service, which is able to create and manage orders. To fetch the detailed information of ordered item(s), it uses a REST service provided [here](https://github.com/devonfw-sample/devon4quarkus-reference). Fetching the items directly from the client will allow attacker to manipulate the item information e.g. price and therefore must be avoided by all means.

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


## Packaging, running the application and creating native executable

Please refer to https://quarkus.io/guides/maven-tooling.html