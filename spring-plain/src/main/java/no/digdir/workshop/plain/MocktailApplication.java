package no.digdir.workshop.plain;

import no.digdir.workshop.plain.repository.MocktailRepository;
import no.digdir.workshop.plain.service.MocktailService;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.List;

public class MocktailApplication {
    public static void main(String[] args) {
        // Gjer noko for å få applikasjonen i gang
        var context = new GenericApplicationContext();

        // Registrer BeanPostProcessor for å handsama @PostConstruct og andre JSR-250 annotasjonar
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        var dsDef = new RootBeanDefinition(DriverManagerDataSource.class);
        dsDef.getPropertyValues().add("driverClassName", "org.h2.Driver");
        dsDef.getPropertyValues().add("url", "jdbc:h2:mem:mocktails;DB_CLOSE_DELAY=-1");
        dsDef.getPropertyValues().add("username", "sa");
        context.registerBeanDefinition("dataSource", dsDef);

        var txManagerDef = new RootBeanDefinition(DataSourceTransactionManager.class);
        txManagerDef.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        context.registerBeanDefinition("transactionManager", txManagerDef);

        var proxyCreatorDef = new RootBeanDefinition(DefaultAdvisorAutoProxyCreator.class);
        context.registerBeanDefinition("autoProxyCreator", proxyCreatorDef);

        var txSourceDef = new RootBeanDefinition(AnnotationTransactionAttributeSource.class);
        context.registerBeanDefinition("transactionAttributeSource", txSourceDef);

        var interceptorDef = new RootBeanDefinition(TransactionInterceptor.class);
        interceptorDef.getConstructorArgumentValues().addIndexedArgumentValue(0, new RuntimeBeanReference("transactionManager"));
        interceptorDef.getConstructorArgumentValues().addIndexedArgumentValue(1, new RuntimeBeanReference("transactionAttributeSource"));
        context.registerBeanDefinition("transactionInterceptor", interceptorDef);

        var advisorDef = new RootBeanDefinition(BeanFactoryTransactionAttributeSourceAdvisor.class);
        advisorDef.getPropertyValues().add("adviceBeanName", "transactionInterceptor");
        advisorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference("transactionAttributeSource"));
        context.registerBeanDefinition("transactionAdvisor", advisorDef);

        // Registrer MocktailRepository med BeanDefinition
        var repositoryDef = new RootBeanDefinition(MocktailRepository.class);
        repositoryDef.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        context.registerBeanDefinition("mocktailRepository", repositoryDef);

        // Registrer MocktailService med BeanDefinition
        var serviceDef = new RootBeanDefinition(MocktailService.class);
        serviceDef.setAutowireMode(RootBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        context.registerBeanDefinition("mocktailService", serviceDef);

        context.registerBean(BeanPostProcessor.class, () -> new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                var clazz = bean.getClass();
                var ctors = clazz.getDeclaredConstructors();

                System.out.println(">>> " + beanName + " (" + clazz.getSimpleName() + ")");

                for (var ctor : ctors) {
                    var params = ctor.getParameterTypes();
                    if (params.length > 0) {
                        System.out.println("    Avhengigheiter injisert via konstruktør:");
                        for (var param : params) {
                            System.out.println("      - " + param.getSimpleName());
                        }
                    } else {
                        System.out.println("    Ingen avhengigheiter");
                    }
                }

                return bean;
            }
        });

        context.refresh();

        var repository = context.getBean(MocktailRepository.class);
        repository.createTable();

        var service = context.getBean(MocktailService.class);
        System.out.println("Klasse: " + service.getClass().getName());
        try {
            service.addMocktails(
                    List.of("Virgin Mojito", "FEIL", "Shirley Temple"),
                    List.of(
                            List.of("lime", "mynte", "sukker", "sodavatn"),
                            List.of("feil"),
                            List.of("ginger ale", "grenadine", "sitron")
                    )
            );
        } catch (RuntimeException e) {
            System.out.println("Feil: " + e.getMessage());
        }

        System.out.println("Mocktails i registeret:");
        service.getAllMocktails().forEach(System.out::println);

        context.close();
    }
}
