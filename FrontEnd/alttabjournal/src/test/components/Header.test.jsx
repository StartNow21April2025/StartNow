import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Header from "../../main/components/Header";
import { mockFetch } from "../utils/testUtils";

// Mock the modals
jest.mock("../../main/pages/SignInModal", () => {
  return function MockSignInModal({ onClose, onLoginSuccess }) {
    return (
      <div data-testid="signin-modal">
        <button onClick={() => onLoginSuccess({ name: "Test User" })}>
          Login Success
        </button>
        <button onClick={onClose}>Close</button>
      </div>
    );
  };
});

jest.mock("../../main/pages/RegisterModal", () => {
  return function MockRegisterModal({ onClose, onRegisterSuccess }) {
    return (
      <div data-testid="register-modal">
        <button onClick={() => onRegisterSuccess({ name: "New User" })}>
          Register Success
        </button>
        <button onClick={onClose}>Close</button>
      </div>
    );
  };
});

describe("Header Component", () => {
  const mockToggleSidebar = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    mockFetch(null, false); // Default to no user
  });

  test("renders header elements", () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    expect(screen.getByText("AltTabJournal")).toBeInTheDocument();
    expect(screen.getByText("Home")).toBeInTheDocument();
    expect(screen.getByText("Topics ▾")).toBeInTheDocument();
    expect(
      screen.getByPlaceholderText("What are you looking for?")
    ).toBeInTheDocument();
  });

  test("shows sign in and register buttons when not logged in", async () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    await waitFor(() => {
      expect(screen.getByText("Sign In")).toBeInTheDocument();
      expect(screen.getByText("Register")).toBeInTheDocument();
    });
  });

  test("shows user info and logout when logged in", async () => {
    mockFetch({ name: "John Doe" }, true);

    render(<Header onToggleSidebar={mockToggleSidebar} />);

    await waitFor(() => {
      expect(screen.getByText("Welcome, John Doe")).toBeInTheDocument();
      expect(screen.getByText("Logout")).toBeInTheDocument();
    });
  });

  test("opens and closes sign in modal", async () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    await waitFor(() => {
      fireEvent.click(screen.getByText("Sign In"));
    });

    expect(screen.getByTestId("signin-modal")).toBeInTheDocument();

    fireEvent.click(screen.getByText("Close"));
    expect(screen.queryByTestId("signin-modal")).not.toBeInTheDocument();
  });

  test("handles successful login", async () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    await waitFor(() => {
      fireEvent.click(screen.getByText("Sign In"));
    });

    fireEvent.click(screen.getByText("Login Success"));

    await waitFor(() => {
      expect(screen.getByText("Welcome, Test User")).toBeInTheDocument();
    });
  });

  test("toggles topics dropdown", () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    fireEvent.click(screen.getByText("Topics ▾"));
    expect(screen.getByText("Wellness")).toBeInTheDocument();
    expect(screen.getByText("Gaming")).toBeInTheDocument();

    fireEvent.click(screen.getByText("Topics ▾"));
    expect(screen.queryByText("Wellness")).not.toBeInTheDocument();
  });

  test("calls onToggleSidebar when toggle button is clicked", () => {
    render(<Header onToggleSidebar={mockToggleSidebar} />);

    fireEvent.click(screen.getByText("☰"));
    expect(mockToggleSidebar).toHaveBeenCalledTimes(1);
  });
});
