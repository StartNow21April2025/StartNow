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
    plugins: {
      react,
      jsxA11y,
      prettier,
    },
    files: ["src/**/*.js", "src/**/*.jsx"],
    languageOptions: {
      parserOptions: {
        ecmaVersion: "latest",
        sourceType: "module",
        ecmaFeatures: {
          jsx: true,
        },
      },
    },
    rules: {
      // If you're using the automatic JSX runtime, you can disable this:
      "react/react-in-jsx-scope": "off",
      // These two rules are important for marking JSX variables as "used":
      "react/jsx-uses-react": "error",
      "react/jsx-uses-vars": "error",
      semi: ["error", "always"],
      quotes: ["error", "double"],
      "no-undef": "off",
      "prettier/prettier": "error",
    },
  },
];
