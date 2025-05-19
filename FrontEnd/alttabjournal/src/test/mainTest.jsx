import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import App from "../main/App";

// Mock fetch globally before tests run
beforeAll(() => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () => Promise.resolve([{ id: 1, title: "Test Article" }]),
    })
  );
});

afterAll(() => {
  global.fetch.mockClear();
  delete global.fetch;
});

test("renders the <App /> component with fetched articles", async () => {
  render(<App />);

  // Assuming your App or child component renders article title after fetch
  await waitFor(() => expect(screen.getByText(/Test Article/i)).toBeInTheDocument());
});
