import { render, screen, fireEvent } from "@testing-library/react";
import SignInModal from "../../main/pages/SignInModal";

// Mock window.alert
global.alert = jest.fn();

describe("SignInModal Component", () => {
  const mockOnClose = jest.fn();
  const mockOnLoginSuccess = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    global.alert.mockClear();
  });

  test("renders sign in form", () => {
    render(
      <SignInModal onClose={mockOnClose} onLoginSuccess={mockOnLoginSuccess} />
    );

    expect(
      screen.getByRole("heading", { name: "Sign In" })
    ).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Email")).toBeInTheDocument();
    expect(
      screen.getByPlaceholderText("Password (min 8 characters)")
    ).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Sign In" })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Cancel" })).toBeInTheDocument();
  });

  test("calls onClose when cancel button is clicked", () => {
    render(
      <SignInModal onClose={mockOnClose} onLoginSuccess={mockOnLoginSuccess} />
    );

    fireEvent.click(screen.getByRole("button", { name: "Cancel" }));
    expect(mockOnClose).toHaveBeenCalledTimes(1);
  });

  test("renders form inputs correctly", () => {
    render(
      <SignInModal onClose={mockOnClose} onLoginSuccess={mockOnLoginSuccess} />
    );

    const emailInput = screen.getByPlaceholderText("Email");
    const passwordInput = screen.getByPlaceholderText(
      "Password (min 8 characters)"
    );

    expect(emailInput).toHaveAttribute("type", "email");
    expect(passwordInput).toHaveAttribute("type", "password");
    expect(passwordInput).toHaveAttribute("minLength", "8");
  });

  test("has proper form structure", () => {
    render(
      <SignInModal onClose={mockOnClose} onLoginSuccess={mockOnLoginSuccess} />
    );

    expect(screen.getByRole("button", { name: "Sign In" })).toHaveAttribute(
      "type",
      "submit"
    );
    expect(screen.getByRole("button", { name: "Cancel" })).toHaveAttribute(
      "type",
      "button"
    );
  });

  test("validates required fields", () => {
    render(
      <SignInModal onClose={mockOnClose} onLoginSuccess={mockOnLoginSuccess} />
    );

    const emailInput = screen.getByPlaceholderText("Email");
    const passwordInput = screen.getByPlaceholderText(
      "Password (min 8 characters)"
    );

    expect(emailInput).toHaveAttribute("required");
    expect(passwordInput).toHaveAttribute("required");
    expect(passwordInput).toHaveAttribute("minLength", "8");
  });
});
