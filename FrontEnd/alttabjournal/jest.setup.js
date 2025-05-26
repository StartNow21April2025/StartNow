/* eslint-env node */
import "@testing-library/jest-dom";
import { TextEncoder, TextDecoder } from "util";

// Set up TextEncoder/TextDecoder
if (typeof globalThis.TextEncoder === "undefined") {
  globalThis.TextEncoder = TextEncoder;
  globalThis.TextDecoder = TextDecoder;
}

// Mock WebSocket
global.WebSocket = jest.fn(() => ({
  close: jest.fn(),
  send: jest.fn(),
  addEventListener: jest.fn(),
  removeEventListener: jest.fn(),
}));

// Mock SockJS and Stomp
global.SockJS = jest.fn(() => ({
  close: jest.fn(),
}));

global.Stomp = {
  over: jest.fn(() => ({
    connect: jest.fn((_, successCallback) => successCallback()),
    disconnect: jest.fn(),
    subscribe: jest.fn(),
    send: jest.fn(),
  })),
};

// Mock URL.createObjectURL
global.URL.createObjectURL = jest.fn();

// Mock IntersectionObserver
global.IntersectionObserver = class IntersectionObserver {
  constructor() {
    this.observe = jest.fn();
    this.unobserve = jest.fn();
    this.disconnect = jest.fn();
  }
};

// Clean up mocks after each test
afterEach(() => {
  jest.clearAllMocks();
});

// Set up a default window location if needed
Object.defineProperty(window, "location", {
  value: {
    href: "http://localhost/",
    pathname: "/",
    search: "",
    hash: "",
  },
  writable: true,
});
