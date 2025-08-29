import { render, screen } from "@testing-library/react";
import Home from "../../main/pages/Home";

// Mock the child components
jest.mock("../../main/components/HeroSection", () => {
  return function MockHeroSection() {
    return <div data-testid="hero-section">Hero Section</div>;
  };
});

jest.mock("../../main/components/TrendingSection", () => {
  return function MockTrendingSection() {
    return <div data-testid="trending-section">Trending Section</div>;
  };
});

describe("Home Component", () => {
  test("renders home page sections", () => {
    render(<Home />);

    expect(screen.getByTestId("hero-section")).toBeInTheDocument();
    expect(screen.getByTestId("trending-section")).toBeInTheDocument();
  });

  test("renders without crashing", () => {
    render(<Home />);
    expect(screen.getByTestId("hero-section")).toBeInTheDocument();
  });
});
