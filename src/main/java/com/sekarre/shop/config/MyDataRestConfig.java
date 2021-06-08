package com.sekarre.shop.config;

import com.sekarre.shop.entity.Country;
import com.sekarre.shop.entity.Product;
import com.sekarre.shop.entity.ProductCategory;
import com.sekarre.shop.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

@RequiredArgsConstructor
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE};

        Class<?>[] classesToRestrict = {Product.class, ProductCategory.class, Country.class, State.class};

        setUnsupportedActions(config, theUnsupportedActions, classesToRestrict);

        exposeIds(config);
    }


    private void exposeIds(RepositoryRestConfiguration config) {
        config.exposeIdsFor(entityManager.getMetamodel().getEntities()
                .stream()
                .map(Type::getJavaType)
                .toArray(Class[]::new));
    }


    private void setUnsupportedActions(RepositoryRestConfiguration config,
                                       HttpMethod[] theUnsupportedActions,
                                       Class<?>... types) {
        for (Class<?> type : types) {
            config.getExposureConfiguration()
                    .forDomainType(type)
                    .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                    .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
        }
    }
}
