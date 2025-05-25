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
  testMatch: [
    "**/?(*.)+(spec|test).[tj]s?(x)",
    "**/*Test.[tj]s?(x)", // This pattern matches files ending with Test.jsx/js
  ],
  setupFilesAfterEnv: ["<rootDir>/jest.setup.js"],
  moduleNameMapper: {
    "\\.(css)$": "identity-obj-proxy",
  },
};
