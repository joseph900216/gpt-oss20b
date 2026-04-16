@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/health").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic()          // Basic Auth
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    override fun userDetailsService(): UserDetailsService = InMemoryUserDetailsManager(
        User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .authorities("ROLE_USER")
            .build()
    )
}