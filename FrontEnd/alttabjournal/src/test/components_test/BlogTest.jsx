// BlogTest.jsx
import React from "react";
import { render, screen } from "@testing-library/react";
import Blog from "../../main/components/Blog";
import { MemoryRouter } from "react-router-dom";

// Mock useParams
jest.mock("react-router-dom", () => {
  const original = jest.requireActual("react-router-dom");
  return {
    __esModule: true,
    ...original,
    useParams: () => ({
      slug: "top-10-valorant-agents-for-rank-push",
    }),
  };
});

describe("Blog component", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  afterEach(() => {
    global.fetch.mockClear();
    delete global.fetch;
  });

  it("displays loading message initially", () => {
    global.fetch = jest.fn(() => new Promise(() => {}));

    render(
      <MemoryRouter>
        <Blog />
      </MemoryRouter>
    );

    expect(screen.getByText("Loading...")).toBeInTheDocument();
  });

  it("renders article content when data is fetched", async () => {
    const fakeData = { fullContent: "<p>Hello World</p>" };

    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve(fakeData),
      })
    );

    const { container } = render(
      <MemoryRouter>
        <Blog />
      </MemoryRouter>
    );

    await screen.findByText("Hello World");

    const blogContainer = container.querySelector(".blog-container");
    expect(blogContainer).toHaveStyle(
      "background-image: url(/assets/images/background.jpg)"
    );
  });

  it("renders 'Article not found' when article data is empty", async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        json: () => Promise.resolve({}),
      })
    );

    render(
      <MemoryRouter>
        <Blog />
      </MemoryRouter>
    );

    await screen.findByText("Article not found");
  });

  it("renders 'Article not found' when fetch fails", async () => {
    const consoleError = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});

    global.fetch = jest.fn(() => Promise.reject(new Error("API error")));

    render(
      <MemoryRouter>
        <Blog />
      </MemoryRouter>
    );

    await screen.findByText("Article not found");

    consoleError.mockRestore();
  });
});
