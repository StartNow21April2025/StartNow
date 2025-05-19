import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import LatestArticles from "../../main/components/LatestArticles";
import { BrowserRouter } from "react-router-dom";

// Mock ArticleCard just to isolate LatestArticles tests
jest.mock("../../main/components/ArticleCard", () => (props) => {
  return (
    <div data-testid="article-card">
      <h3>{props.title}</h3>
      <p>{props.description}</p>
    </div>
  );
});

describe("LatestArticles component", () => {
  beforeEach(() => {
    jest.restoreAllMocks();
  });

  it("shows loading state initially", () => {
    // mock fetch but never resolve it to keep loading state
    global.fetch = jest.fn(() => new Promise(() => {}));

    render(
      <BrowserRouter>
        <LatestArticles />
      </BrowserRouter>
    );

    expect(screen.getByText(/Loading articles/i)).toBeInTheDocument();
  });

  it("renders articles after successful fetch", async () => {
    const mockArticles = [
      { title: "Article 1", description: "Desc 1", slug: "article-1" },
      { title: "Article 2", description: "Desc 2", slug: "article-2" },
    ];
    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve(mockArticles),
      })
    );

    render(
      <BrowserRouter>
        <LatestArticles />
      </BrowserRouter>
    );

    // Wait for articles to be rendered
    const articleCards = await screen.findAllByTestId("article-card");
    expect(articleCards).toHaveLength(mockArticles.length);

    // Check title presence (simple content check)
    expect(screen.getByText("Article 1")).toBeInTheDocument();
    expect(screen.getByText("Article 2")).toBeInTheDocument();
  });

  it("logs an error on fetch failure", async () => {
    const consoleErrorSpy = jest.spyOn(console, "error").mockImplementation(() => {});
    global.fetch = jest.fn(() => Promise.reject("API is down"));

    render(
      <BrowserRouter>
        <LatestArticles />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(
        "Failed to fetch articles",
        "API is down"
      );
    });

    consoleErrorSpy.mockRestore();
  });
});
