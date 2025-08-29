import { render, screen } from "@testing-library/react";
import Footer from "../../main/components/Footer";

describe("Footer Component", () => {
  test("renders footer content", () => {
    render(<Footer />);

    expect(screen.getByText("AltTabJournal")).toBeInTheDocument();
    expect(
      screen.getByText("Level up your knowledge, one scroll at a time.")
    ).toBeInTheDocument();
  });

  test("renders quick links", () => {
    render(<Footer />);

    expect(screen.getByText("Quick Links")).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "Home" })).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "Topics" })).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "About" })).toBeInTheDocument();
  });

  test("renders social links", () => {
    render(<Footer />);

    expect(screen.getByText("Follow Us")).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "Twitter" })).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "YouTube" })).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "Discord" })).toBeInTheDocument();
  });

  test("renders copyright with current year", () => {
    render(<Footer />);

    const currentYear = new Date().getFullYear();
    expect(
      screen.getByText(`© ${currentYear} AltTabJournal. All rights reserved.`)
    ).toBeInTheDocument();
  });
});
