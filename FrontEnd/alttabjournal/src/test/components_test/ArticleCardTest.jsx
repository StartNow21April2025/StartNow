// ArticleCard.test.jsx
import React from "react";
import { render, screen } from "@testing-library/react";
import ArticleCard from "../../main/components/ArticleCard";
import { MemoryRouter } from "react-router-dom";

describe("ArticleCard", () => {
  const props = {
    title: "Test Title",
    description: "Test Description",
    slug: "test-slug",
  };

  test("renders article card with correct title and description", () => {
    render(
      <MemoryRouter>
        <ArticleCard {...props} />
      </MemoryRouter>
    );

    // Verify that title and description are in the document
    expect(screen.getByText("Test Title")).toBeInTheDocument();
    expect(screen.getByText("Test Description")).toBeInTheDocument();
  });

  test("renders a Link with the correct URL", () => {
    render(
      <MemoryRouter>
        <ArticleCard {...props} />
      </MemoryRouter>
    );

    // Get the link element by its role
    const linkElement = screen.getByRole("link");
    expect(linkElement).toHaveAttribute("href", "/blog/test-slug");
  });
});