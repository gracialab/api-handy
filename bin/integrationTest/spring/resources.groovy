import handy.api.security.filters.JwtAuthenticationFilter

beans = {

    jwtAuthenticationFilter(JwtAuthenticationFilter) {
        tokenService = ref('tokenService')
    }
}
