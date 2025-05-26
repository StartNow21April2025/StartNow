// AppTest.jsx
import React from "react";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import App from "../main/App";

// Mock components
jest.mock("../main/components/Header", () => () => (
  <div data-testid="header">Header Component</div>
));

jest.mock("../main/components/FeaturedPost", () => () => (
  <div data-testid="featured-post">Featured Post Component</div>
));

jest.mock("../main/components/LatestArticles", () => () => (
  <div data-testid="latest-articles">Latest Articles Component</div>
));

jest.mock("../main/components/Footer", () => () => (
  <div data-testid="footer">Footer Component</div>
));

jest.mock("../main/components/Blog", () => () => (
  <div data-testid="blog">Blog Component</div>
));

jest.mock("../main/components/Community", () => () => (
  <div data-testid="community">Community Component</div>
));

describe("App", () => {
  const renderWithRoute = (initialEntry) => {
    render(
      <App
        RouterComponent={({ children }) => (
          <MemoryRouter initialEntries={[initialEntry]}>
            {children}
          </MemoryRouter>
        )}
      />
    );
  };

  test("renders homepage components on route '/'", () => {
    renderWithRoute("/");

    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("featured-post")).toBeInTheDocument();
    expect(screen.getByTestId("latest-articles")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.queryByTestId("blog")).not.toBeInTheDocument();
    expect(screen.queryByTestId("community")).not.toBeInTheDocument();
  });

  test("renders blog page components on route '/blog/:slug'", () => {
    renderWithRoute("/blog/test-slug");

    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("blog")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.queryByTestId("featured-post")).not.toBeInTheDocument();
    expect(screen.queryByTestId("latest-articles")).not.toBeInTheDocument();
  });

  test("renders community page components on route '/community'", () => {
    renderWithRoute("/community");

    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("community")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.queryByTestId("featured-post")).not.toBeInTheDocument();
    expect(screen.queryByTestId("latest-articles")).not.toBeInTheDocument();
  });
});
