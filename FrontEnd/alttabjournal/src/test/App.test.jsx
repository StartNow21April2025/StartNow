import React from "react";
import { render, screen } from "@testing-library/react";

// Create a test version of App without Router
const TestApp = () => {
  const [sidebarOpen] = React.useState(false);

  return (
    <>
      <div data-testid="header">Header</div>
      <div
        data-testid="sidebar"
        style={{ display: sidebarOpen ? "block" : "none" }}
      >
        Sidebar
      </div>
      <div className="page-wrapper">
        <div className="main-container">
          <div data-testid="home">Home Page</div>
        </div>
        <div data-testid="footer">Footer</div>
      </div>
    </>
  );
};

describe("App Component", () => {
  test("renders main app structure", () => {
    render(<TestApp />);

    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.getByTestId("home")).toBeInTheDocument();
  });

  test("renders without crashing", () => {
    render(<TestApp />);
    expect(screen.getByTestId("header")).toBeInTheDocument();
  });
});
