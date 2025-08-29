# Frontend Test Suite

This directory contains comprehensive tests for the AltTabJournal frontend application.

## Test Structure

```
src/test/
├── __mocks__/           # Mock implementations for external libraries
├── api/                 # API function tests
├── components/          # Component unit tests
├── config/              # Configuration tests
├── integration/         # Integration and user flow tests
├── pages/               # Page component tests
├── utils/               # Test utilities and helpers
├── setupTests.js        # Global test setup
└── README.md           # This file
```

## Running Tests

```bash
# Run all tests with coverage
npm test

# Run tests in watch mode
npm test -- --watch

# Run specific test file
npm test Header.test.jsx

# Run tests with verbose output
npm test -- --verbose
```

## Test Categories

### Unit Tests

- **Components**: Test individual React components in isolation
- **API**: Test API functions and data fetching
- **Config**: Test configuration modules

### Integration Tests

- **User Flows**: Test complete user interactions across components
- **Navigation**: Test routing and page transitions

## Test Utilities

### `testUtils.js`

- `renderWithRouter()`: Renders components with React Router context
- `mockFetch()`: Mocks fetch API responses
- `mockFetchError()`: Mocks fetch API errors

### Mocks

- **sockjs-client**: WebSocket connection mock
- **stompjs**: STOMP protocol mock

## Coverage

Tests are configured to generate coverage reports in the `coverage/` directory. View the HTML report by opening `coverage/lcov-report/index.html` in your browser.

## Best Practices

1. **Isolation**: Each test should be independent and not rely on other tests
2. **Mocking**: Mock external dependencies and API calls
3. **Assertions**: Use descriptive assertions that clearly indicate what is being tested
4. **Cleanup**: Tests automatically clean up mocks between runs
5. **Accessibility**: Test components for accessibility compliance where applicable
