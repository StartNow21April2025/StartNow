/* global global, jest, beforeEach */
// Global test setup
import "@testing-library/jest-dom";
import "whatwg-fetch";

// Mock window.alert
global.alert = jest.fn();

// Mock fetch globally
global.fetch = jest.fn();

// Mock window.location for Router tests
Object.defineProperty(window, "location", {
  value: {
    href: "http://localhost/",
    origin: "http://localhost",
    pathname: "/",
  },
  writable: true,
});

// Reset mocks before each test
beforeEach(() => {
  jest.clearAllMocks();
  global.fetch.mockClear();
});
