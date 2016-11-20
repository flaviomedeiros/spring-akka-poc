## Spring Boot and Akka Actors integration PoC

### Introduction

This application mimics an analytics events registration, like product page access by a customer.
The main purpose is to demonstrate how to leverage all the Spring Boot based microservice benefits and use the power of Akka for processing logic.
It's possible to create a microservice application entirely with Akka, but this PoC is for Spring Boot.

On the code you can see how Spring Boot is configured and how Actor can be injected by Spring DI and use their injected dependcies transparently.

### Building the application:

- You need maven on your path
- On command line, call:
```shell
mvn compile
```

### Running the aplication from command line
```shell
mvn spring-boot:run
```

**Sample startup output:**
```
2016-11-19 20:11:06.340  INFO 3048 --- [           main] c.f.AkkaSpringPocApplication             : Starting AkkaSpringPocApplication on IFMTZ0292 with PID 3048 (C:\Users\flavio.medeiros\Documents\Projetos\akka-spring-poc\target\classes started by flavio.medeiros in C:\Users\flavio.medeiros\Documents\Projetos\akka-spring-poc)
2016-11-19 20:11:06.355  INFO 3048 --- [           main] c.f.AkkaSpringPocApplication             : No active profile set, falling back to default profiles: default
2016-11-19 20:11:06.541  INFO 3048 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@7ae42ce3: startup date [Sat Nov 19 20:11:06 GFT 2016]; root of context hierarchy
2016-11-19 20:11:10.256  INFO 3048 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat initialized with port(s): 8080 (http)
2016-11-19 20:11:10.293  INFO 3048 --- [           main] o.apache.catalina.core.StandardService   : Starting service Tomcat
2016-11-19 20:11:10.295  INFO 3048 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.6
2016-11-19 20:11:10.608  INFO 3048 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2016-11-19 20:11:10.609  INFO 3048 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 4075 ms
2016-11-19 20:11:10.989  INFO 3048 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Mapping servlet: 'dispatcherServlet' to [/]
2016-11-19 20:11:10.996  INFO 3048 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2016-11-19 20:11:10.997  INFO 3048 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2016-11-19 20:11:10.997  INFO 3048 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2016-11-19 20:11:11.004  INFO 3048 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2016-11-19 20:11:12.905  INFO 3048 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@7ae42ce3: startup date [Sat Nov 19 20:11:06 GFT 2016]; root of context hierarchy
2016-11-19 20:11:13.042  INFO 3048 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/events/{event}],methods=[POST],produces=[application/json]}" onto public org.springframework.web.context.request.async.DeferredResult<org.springframework.http.ResponseEntity> com.flaviomedeiros.web.EventController.postEvent(java.lang.String,java.lang.String)
2016-11-19 20:11:13.051  INFO 3048 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/events],methods=[GET],produces=[application/json]}" onto public org.springframework.web.context.request.async.DeferredResult<org.springframework.http.ResponseEntity> com.flaviomedeiros.web.EventController.postEvent()
2016-11-19 20:11:13.064  INFO 3048 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2016-11-19 20:11:13.065  INFO 3048 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2016-11-19 20:11:13.164  INFO 3048 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2016-11-19 20:11:13.165  INFO 3048 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2016-11-19 20:11:13.271  INFO 3048 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2016-11-19 20:11:13.804  INFO 3048 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2016-11-19 20:11:13.937  INFO 3048 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2016-11-19 20:11:13.948  INFO 3048 --- [           main] c.f.AkkaSpringPocApplication             : Started AkkaSpringPocApplication in 9.3 seconds (JVM running for 10.35)
2016-11-19 20:11:22.279  INFO 3048 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
2016-11-19 20:11:22.285  INFO 3048 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
2016-11-19 20:11:22.323  INFO 3048 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 31 ms
```

### How to open the project on your preferred IDE

The application is a maven project, just import on your preferred IDE and enjoy.