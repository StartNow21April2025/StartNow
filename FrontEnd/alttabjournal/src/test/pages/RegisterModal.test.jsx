import { render, screen, fireEvent } from "@testing-library/react";
import RegisterModal from "../../main/pages/RegisterModal";

global.alert = jest.fn();

describe("RegisterModal Component", () => {
  const mockOnClose = jest.fn();
  const mockOnRegisterSuccess = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    global.alert.mockClear();
  });

  test("renders register form", () => {
    render(
      <RegisterModal
        onClose={mockOnClose}
        onRegisterSuccess={mockOnRegisterSuccess}
      />
    );

    expect(
      screen.getByRole("heading", { name: "Register" })
    ).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Name")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Email")).toBeInTheDocument();
    expect(
      screen.getByPlaceholderText("Password (min 8 characters)")
    ).toBeInTheDocument();
    expect(
      screen.getByRole("button", { name: "Register" })
    ).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Cancel" })).toBeInTheDocument();
  });

  test("calls onClose when cancel button is clicked", () => {
    render(
      <RegisterModal
        onClose={mockOnClose}
        onRegisterSuccess={mockOnRegisterSuccess}
      />
    );

    fireEvent.click(screen.getByRole("button", { name: "Cancel" }));
    expect(mockOnClose).toHaveBeenCalledTimes(1);
  });

  test("renders all form fields correctly", () => {
    render(
      <RegisterModal
        onClose={mockOnClose}
        onRegisterSuccess={mockOnRegisterSuccess}
      />
    );

    const nameInput = screen.getByPlaceholderText("Name");
    const emailInput = screen.getByPlaceholderText("Email");
    const passwordInput = screen.getByPlaceholderText(
      "Password (min 8 characters)"
    );

    expect(nameInput).toHaveAttribute("type", "text");
    expect(emailInput).toHaveAttribute("type", "email");
    expect(passwordInput).toHaveAttribute("type", "password");
  });

  test("has proper form structure", () => {
    render(
      <RegisterModal
        onClose={mockOnClose}
        onRegisterSuccess={mockOnRegisterSuccess}
      />
    );

    expect(screen.getByRole("button", { name: "Register" })).toHaveAttribute(
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
      <RegisterModal
        onClose={mockOnClose}
        onRegisterSuccess={mockOnRegisterSuccess}
      />
    );

    const nameInput = screen.getByPlaceholderText("Name");
    const emailInput = screen.getByPlaceholderText("Email");
    const passwordInput = screen.getByPlaceholderText(
      "Password (min 8 characters)"
    );

    expect(nameInput).toHaveAttribute("required");
    expect(emailInput).toHaveAttribute("required");
    expect(passwordInput).toHaveAttribute("required");
    expect(passwordInput).toHaveAttribute("minLength", "8");
  });
});
