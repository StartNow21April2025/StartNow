// babel.config.cjs
module.exports = {
  presets: [
    "@babel/preset-env",
    [
      "@babel/preset-react",
      {
        runtime: "automatic", // enables the new JSX transform
      },
    ],
  ],
};
