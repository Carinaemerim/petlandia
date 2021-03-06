package br.edu.ifrs.canoas.webapp.config;

import br.edu.ifrs.canoas.webapp.components.HeaderInterceptor;
import br.edu.ifrs.canoas.webapp.components.ManagerInterceptor;
import br.edu.ifrs.canoas.webapp.config.auth.VerifyAccessInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Autowired
    private ManagerInterceptor managerInterceptor;
    @Autowired
    private HeaderInterceptor headerInterceptor;
    @Autowired
    private VerifyAccessInterceptor verifyAccessInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/auth/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
        FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(headerInterceptor).addPathPatterns("/**");
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager", "/manager/**");
        registry.addInterceptor(verifyAccessInterceptor)
                .excludePathPatterns("/webjars/**",
                        "/css/**",
                        "/js/**").addPathPatterns("/**");


    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean beanValidator = new LocalValidatorFactoryBean();
        beanValidator.setValidationMessageSource(messageSource());
        return beanValidator;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setBasenames("classpath:messages", "classpath:ValidationMessages");
        return messageSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Locale change interceptor.
     *
     * @return the locale change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public LocaleResolver localeResolver() {
        LocaleContextHolder.setDefaultLocale(new Locale("pt", "BR"));
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(LocaleContextHolder.getLocale());
        return slr;
    }

}