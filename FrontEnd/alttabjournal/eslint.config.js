import js from "@eslint/js";
import react from "eslint-plugin-react";
import jsxA11y from "eslint-plugin-jsx-a11y";
import prettier from "eslint-plugin-prettier";

export default [
  {
    ignores: ["dist", "node_modules", "coverage"],
  },
  js.configs.recommended,
  {
    // Base configuration for all files
    plugins: {
      react,
      jsxA11y,
      prettier,
    },
    languageOptions: {
      parserOptions: {
        ecmaVersion: "latest",
        sourceType: "module",
        ecmaFeatures: {
          jsx: true,
        },
      },
      globals: {
        // Browser globals
        window: "readonly",
        document: "readonly",
        fetch: "readonly",
        console: "readonly",
        alert: "readonly",
      },
    },
    rules: {
      "react/react-in-jsx-scope": "off",
      "react/jsx-uses-react": "error",
      "react/jsx-uses-vars": "error",
      semi: ["error", "always"],
      quotes: ["error", "double"],
      "no-undef": "error",
      "prettier/prettier": "error",
    },
  },
  // Test files configuration
  {
    files: [
      "**/*.test.js",
      "**/*.test.jsx",
      "**/*.spec.js",
      "**/*.spec.jsx",
      "**/jest.setup.js",
      "src/test/**/*.jsx",
    ],
    languageOptions: {
      globals: {
        // Jest globals
        jest: "readonly",
        describe: "readonly",
        it: "readonly",
        test: "readonly",
        expect: "readonly",
        beforeEach: "readonly",
        afterEach: "readonly",
        beforeAll: "readonly",
        afterAll: "readonly",
        global: "readonly",
        // Browser globals (also needed in tests)
        window: "readonly",
        document: "readonly",
        fetch: "readonly",
        console: "readonly",
        alert: "readonly",
      },
    },
    settings: {
      jest: {
        version: "detect",
      },
    },
  },
  // Source files configuration
  {
    files: ["src/main/**/*.jsx", "src/main/**/*.js"],
    languageOptions: {
      globals: {
        // Browser globals for source files
        window: "readonly",
        document: "readonly",
        fetch: "readonly",
        console: "readonly",
        alert: "readonly",
      },
    },
  },
];
