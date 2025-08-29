# Backend Test Suite

This directory contains comprehensive tests for the AltTabJournal Spring Boot backend application.

## Test Structure

```
src/test/java/com/startnow/blog/
├── configuration/           # Test configuration classes
├── controller_tests/        # REST controller unit tests
├── exception_tests/         # Exception handler tests
├── integration_tests/       # Integration and end-to-end tests
├── repository_tests/        # Repository layer tests
├── security_tests/          # Security configuration tests
├── service_tests/           # Business logic service tests
├── util_tests/              # Utility class tests
└── StartNowApplicationTests.java  # Application context tests
```

## Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=UserServiceTests

# Run tests in specific package
mvn test -Dtest="com.startnow.blog.controller_tests.*"

# Run integration tests only
mvn test -Dtest="*IntegrationTests"
```

## Test Categories

### Unit Tests
- **Controllers**: Test REST endpoints with mocked services
- **Services**: Test business logic with mocked repositories
- **Repositories**: Test data access layer with mocked dependencies
- **Utilities**: Test helper classes like JWT utilities
- **Exception Handlers**: Test global exception handling

### Integration Tests
- **API Endpoints**: Test complete request/response cycles
- **Security**: Test authentication and authorization flows
- **Database**: Test actual database interactions (when applicable)

## Test Technologies

- **JUnit 5**: Primary testing framework
- **Mockito**: Mocking framework for unit tests
- **Spring Boot Test**: Integration testing support
- **MockMvc**: Web layer testing
- **TestContainers**: For database integration tests (if needed)

## Test Profiles

### application.properties (test)
```properties
# Test-specific configurations
spring.profiles.active=test
logging.level.com.startnow.blog=DEBUG
```

## Key Test Classes

### Controller Tests
- **UserAuthControllerTests**: Authentication endpoints (sign-in, register, logout, me)
- **AgentControllerTests**: Agent CRUD operations
- **ArticleControllerTests**: Article management
- **HomePageSectionControllerTests**: Home page content retrieval

### Service Tests
- **UserServiceTests**: User authentication and management
- **AgentServiceTests**: Agent business logic
- **ArticleServiceTests**: Article business logic

### Utility Tests
- **JwtUtilTests**: JWT token generation, validation, and extraction

### Security Tests
- **SecurityConfigTests**: Security configuration validation

### Integration Tests
- **UserAuthIntegrationTests**: End-to-end authentication flows

## Test Data Management

### Test Entities
- Use builder pattern for creating test data
- Mock external dependencies (DynamoDB, AWS services)
- Use @MockBean for Spring context tests

### Database Testing
- Use in-memory databases for integration tests
- Clean up test data after each test
- Use @Transactional for rollback behavior

## Coverage Goals

- **Line Coverage**: > 80%
- **Branch Coverage**: > 70%
- **Method Coverage**: > 90%

## Best Practices

1. **Isolation**: Each test should be independent
2. **Mocking**: Mock external dependencies and focus on unit under test
3. **Assertions**: Use descriptive assertions with meaningful error messages
4. **Test Data**: Use builders and factories for consistent test data
5. **Naming**: Use descriptive test method names that explain the scenario
6. **Documentation**: Use @DisplayName for readable test descriptions

## Common Test Patterns

### Controller Testing
```java
@ExtendWith(MockitoExtension.class)
class ControllerTests {
    private MockMvc mockMvc;
    
    @Mock
    private ServiceInterface service;
    
    @InjectMocks
    private Controller controller;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
}
```

### Service Testing
```java
@ExtendWith(MockitoExtension.class)
class ServiceTests {
    @InjectMocks
    private Service service;
    
    @Mock
    private Repository repository;
}
```

### Integration Testing
```java
@SpringBootTest
@AutoConfigureTestMockMvc
@ActiveProfiles("test")
class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;
}
```

## Troubleshooting

### Common Issues
1. **Context Loading**: Ensure all required beans are mocked or configured
2. **Security**: Use @WithMockUser for authenticated endpoints
3. **Database**: Use @DataJpaTest for repository tests
4. **Web Layer**: Use @WebMvcTest for controller-only tests

### Test Failures
- Check mock configurations
- Verify test data setup
- Ensure proper exception handling
- Review assertion expectations