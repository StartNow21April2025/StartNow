// jest.config.js (ES module format)
export default {
  testEnvironment: "jest-environment-jsdom",
  collectCoverage: true,
  coverageDirectory: "coverage",
  collectCoverageFrom: [
    "src/**/*.{js,jsx}",
    "!src/**/*.{test,spec}.{js,jsx}",
    "!src/index.js",
  ],
  clearMocks: true,
  testMatch: ["**/?(*.)+(spec|test).[tj]s?(x)", "**/*Test.[tj]s?(x)"],
  setupFilesAfterEnv: ["<rootDir>/jest.setup.js"],
  moduleNameMapper: {
    "\\.(css)$": "identity-obj-proxy",
    "^sockjs-client$": "<rootDir>/src/test/__mocks__/sockjs-client.js",
    "^stompjs$": "<rootDir>/src/test/__mocks__/stompjs.js",
  },
  transformIgnorePatterns: ["/node_modules/(?!(sockjs-client|stompjs)/)"],
  verbose: true,
  testEnvironmentOptions: {
    url: "http://localhost/",
  },
};
