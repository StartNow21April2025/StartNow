// AppTest.jsx
import React from "react";
import { render, screen } from "@testing-library/react";
import App from "../main/App"; // Adjust the path for App accordingly

// Mock child components so we can detect their presence while isolating App's routing.
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

describe("App", () => {
  beforeEach(() => {
    // Reset history before each test (if needed)
    window.history.pushState({}, "Test page", "/");
  });

  test("renders homepage components on route \"/\"", () => {
    window.history.pushState({}, "Home Page", "/");
    render(<App />);
    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("featured-post")).toBeInTheDocument();
    expect(screen.getByTestId("latest-articles")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.queryByTestId("blog")).not.toBeInTheDocument();
  });

  test("renders blog page components on route \"/blog/:slug\"", () => {
    window.history.pushState({}, "Blog Page", "/blog/test-slug");
    render(<App />);
    expect(screen.getByTestId("header")).toBeInTheDocument();
    expect(screen.getByTestId("blog")).toBeInTheDocument();
    expect(screen.getByTestId("footer")).toBeInTheDocument();
    expect(screen.queryByTestId("featured-post")).not.toBeInTheDocument();
    expect(screen.queryByTestId("latest-articles")).not.toBeInTheDocument();
  });
});