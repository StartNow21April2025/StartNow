import { API_BASE_URL } from "../../main/config/api";

describe("API Configuration", () => {
  test("exports correct API base URL", () => {
    expect(API_BASE_URL).toBe("http://13.204.176.38");
  });

  test("API_BASE_URL is a string", () => {
    expect(typeof API_BASE_URL).toBe("string");
  });

  test("API_BASE_URL is not empty", () => {
    expect(API_BASE_URL).toBeTruthy();
    expect(API_BASE_URL.length).toBeGreaterThan(0);
  });
});
