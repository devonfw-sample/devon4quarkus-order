package com.devonfw.quarkus;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        tags = {@Tag(name = "order", description = "Order API.")},
        info = @Info(title = "Order Service Quarkus demo",
                version = "1.0.0",
                contact = @Contact(name = "Devonfw Support")))
public class DemoApplication extends Application {
}
