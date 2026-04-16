@Configuration
class ValidationConfig {
    @Bean
    fun validator(): Validator = LocalValidatorFactoryBean()
}