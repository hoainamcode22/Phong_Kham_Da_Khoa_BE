package com.example.phongkham_da_khoa;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired private UserRepository userRepo;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
