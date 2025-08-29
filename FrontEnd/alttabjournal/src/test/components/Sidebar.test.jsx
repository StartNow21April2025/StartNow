import { render, screen, fireEvent } from "@testing-library/react";
import Sidebar from "../../main/components/Sidebar";

describe("Sidebar Component", () => {
  const mockOnClose = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  test("renders sidebar when open", () => {
    render(<Sidebar isOpen={true} onClose={mockOnClose} />);

    expect(screen.getByText("✕")).toBeInTheDocument();
    expect(screen.getByText("Dashboard")).toBeInTheDocument();
    expect(screen.getByText("Bookmarks")).toBeInTheDocument();
    expect(screen.getByText("Saved Posts")).toBeInTheDocument();
    expect(screen.getByText("Settings")).toBeInTheDocument();
  });

  test("does not render sidebar when closed", () => {
    render(<Sidebar isOpen={false} onClose={mockOnClose} />);

    expect(screen.queryByText("Dashboard")).toBeInTheDocument();
  });

  test("calls onClose when close button is clicked", () => {
    render(<Sidebar isOpen={true} onClose={mockOnClose} />);

    fireEvent.click(screen.getByText("✕"));
    expect(mockOnClose).toHaveBeenCalledTimes(1);
  });

  test("calls onClose when overlay is clicked", () => {
    render(<Sidebar isOpen={true} onClose={mockOnClose} />);

    const overlay = screen.getByText("Dashboard").closest(".sidebar-overlay");
    fireEvent.click(overlay);
    expect(mockOnClose).toHaveBeenCalledTimes(1);
  });

  test("does not close when sidebar content is clicked", () => {
    render(<Sidebar isOpen={true} onClose={mockOnClose} />);

    fireEvent.click(screen.getByText("Dashboard"));
    expect(mockOnClose).not.toHaveBeenCalled();
  });
});
