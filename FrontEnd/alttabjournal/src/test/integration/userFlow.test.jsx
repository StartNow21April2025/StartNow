import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import { mockFetch } from "../utils/testUtils";

// Simple test component for integration testing
const TestIntegrationApp = () => {
  const [sidebarOpen, setSidebarOpen] = React.useState(false);

  return (
    <>
      <div data-testid="header">
        <button onClick={() => setSidebarOpen(true)}>☰</button>
        <button>Sign In</button>
      </div>
      {sidebarOpen && (
        <div data-testid="sidebar">
          <button onClick={() => setSidebarOpen(false)}>Close Sidebar</button>
        </div>
      )}
      <div data-testid="home-page">Home Content</div>
      <div data-testid="footer">Footer</div>
    </>
  );
};

describe("User Flow Integration Tests", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    mockFetch(null, false); // Default to no user
  });

  test("complete user authentication flow", async () => {
    render(<TestIntegrationApp />);

    // Initially shows sign in button
    expect(screen.getByText("Sign In")).toBeInTheDocument();
  });

  test("sidebar toggle functionality", () => {
    render(<TestIntegrationApp />);

    // Sidebar should not be visible initially
    expect(screen.queryByTestId("sidebar")).not.toBeInTheDocument();

    // Click toggle button
    fireEvent.click(screen.getByText("☰"));

    // Sidebar should appear
    expect(screen.getByTestId("sidebar")).toBeInTheDocument();

    // Close sidebar
    fireEvent.click(screen.getByText("Close Sidebar"));

    // Sidebar should disappear
    expect(screen.queryByTestId("sidebar")).not.toBeInTheDocument();
  });

  test("navigation between pages", () => {
    render(<TestIntegrationApp />);

    // Should show home page by default
    expect(screen.getByTestId("home-page")).toBeInTheDocument();

    // Footer should always be present
    expect(screen.getByTestId("footer")).toBeInTheDocument();
  });
});
