import { screen } from "@testing-library/react";
import ArticleCard from "../../main/components/ArticleCard";
import { renderWithRouter } from "../utils/testUtils";

describe("ArticleCard Component", () => {
  const mockProps = {
    title: "Test Article Title",
    description: "This is a test article description",
    slug: "test-article-slug",
  };

  test("renders article card with correct content", () => {
    renderWithRouter(<ArticleCard {...mockProps} />);

    expect(screen.getByText("Test Article Title")).toBeInTheDocument();
    expect(
      screen.getByText("This is a test article description")
    ).toBeInTheDocument();
  });

  test("creates correct link to blog post", () => {
    renderWithRouter(<ArticleCard {...mockProps} />);

    const link = screen.getByRole("link");
    expect(link).toHaveAttribute("href", "/blog/test-article-slug");
  });

  test("renders with empty props", () => {
    renderWithRouter(<ArticleCard title="" description="" slug="" />);

    const link = screen.getByRole("link");
    expect(link).toHaveAttribute("href", "/blog/");
  });

  test("handles special characters in props", () => {
    const specialProps = {
      title: "Article with quotes & symbols",
      description: "Description with <tags> and & symbols",
      slug: "special-chars-123",
    };

    renderWithRouter(<ArticleCard {...specialProps} />);

    expect(
      screen.getByText("Article with quotes & symbols")
    ).toBeInTheDocument();
    expect(
      screen.getByText("Description with <tags> and & symbols")
    ).toBeInTheDocument();
  });
});
