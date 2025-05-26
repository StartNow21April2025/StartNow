import React from "react";
import App from "../main/App";

// Mock createRoot function
const mockRender = jest.fn();
const mockCreateRoot = jest.fn(() => ({
  render: mockRender,
}));

// Mock react-dom/client module
jest.mock("react-dom/client", () => ({
  createRoot: mockCreateRoot,
}));

// Mock App component
jest.mock("../main/App", () => {
  return function MockApp() {
    return <div data-testid="mock-app">App</div>;
  };
});

// Mock CSS import
jest.mock("../main/index.css", () => ({}));

describe("Main", () => {
  let root;

  beforeEach(() => {
    // Reset all mocks
    jest.clearAllMocks();

    // Set up DOM
    root = document.createElement("div");
    root.id = "root";
    document.body.appendChild(root);
  });

  afterEach(() => {
    // Clean up DOM
    if (root && root.parentNode) {
      root.parentNode.removeChild(root);
    }
    jest.resetModules();
  });

  test("initializes React correctly", async () => {
    // Import main
    await import("../main/main.jsx");

    // Verify createRoot was called with root element
    expect(mockCreateRoot).toHaveBeenCalledWith(root);

    // Verify render was called
    expect(mockRender).toHaveBeenCalledTimes(1);

    // Get the render argument (should be StrictMode with App)
    const renderArg = mockRender.mock.calls[0][0];

    // Verify StrictMode wrapper
    expect(renderArg.type).toBe(React.StrictMode);

    // Verify App is child of StrictMode
    const app = renderArg.props.children;
    expect(app.type).toBe(App);
  });

  test("root element is properly set up", async () => {
    // Verify root element exists
    const rootElement = document.getElementById("root");
    expect(rootElement).toBeTruthy();
    expect(rootElement.id).toBe("root");

    // Import main
    await import("../main/main.jsx");

    // Verify createRoot was called with correct element
    expect(mockCreateRoot).toHaveBeenCalledWith(rootElement);
  });
});
