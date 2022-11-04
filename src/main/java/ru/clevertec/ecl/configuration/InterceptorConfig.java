package ru.clevertec.ecl.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.ecl.interceptor.CommonInterceptor;
import ru.clevertec.ecl.interceptor.OrderFindByIdInterceptor;
import ru.clevertec.ecl.interceptor.OrderInterceptor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor;
    private final OrderInterceptor orderInterceptor;
    private final OrderFindByIdInterceptor orderFindByIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/v1/tags/**")
                .addPathPatterns("/v1/certificates/**")
                .addPathPatterns("/v1/users/**");
        registry.addInterceptor(orderInterceptor)
                .addPathPatterns("/v1/orders")
                .addPathPatterns("/v1/orders/sequence");
        registry.addInterceptor(orderFindByIdInterceptor)
                .addPathPatterns("/v1/orders/*")
                .excludePathPatterns("/v1/orders/sequence");
    }
}
