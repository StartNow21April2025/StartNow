/* global global, jest */
import { render } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";

// Custom render function with Router wrapper
export const renderWithRouter = (ui, options = {}) => {
  const Wrapper = ({ children }) => <BrowserRouter>{children}</BrowserRouter>;

  return render(ui, { wrapper: Wrapper, ...options });
};

// Mock fetch responses
export const mockFetch = (response, ok = true) => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      ok,
      json: () => Promise.resolve(response),
      status: ok ? 200 : 400,
    })
  );
};

export const mockFetchError = () => {
  global.fetch = jest.fn(() => Promise.reject(new Error("Network error")));
};
