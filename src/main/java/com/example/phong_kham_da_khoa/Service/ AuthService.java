package com.example.phongkham_da_khoa;

@Service
public class AuthService implements UserDetailsService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.findByEmail(req.email).isPresent())
            throw new RuntimeException("Email đã được đăng ký");

        User user = new User();
        user.setEmail(req.email);
        user.setName(req.name);
        user.setPassword(encoder.encode(req.password));
        user.setRole(req.role);
        user.setVerified(false);

        userRepo.save(user);
        String token = jwtUtil.generateToken(loadUserByUsername(user.getEmail()));
        return new AuthResponse(token, user.getName(), user.getRole());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Email không đúng"));
        if (!encoder.matches(req.password, user.getPassword()))
            throw new RuntimeException("Mật khẩu sai");

        String token = jwtUtil.generateToken(loadUserByUsername(user.getEmail()));
        return new AuthResponse(token, user.getName(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
