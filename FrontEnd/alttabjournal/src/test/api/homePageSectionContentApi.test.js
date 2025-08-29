import { fetchSectionContent } from "../../main/api/homePageSectionContentApi";
import { mockFetch, mockFetchError } from "../utils/testUtils";

describe("homePageSectionContentApi", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe("fetchSectionContent", () => {
    test("fetches section content successfully", async () => {
      const mockData = { title: "Test Section", content: "Test content" };
      mockFetch(mockData, true);

      const result = await fetchSectionContent("hero");

      expect(global.fetch).toHaveBeenCalledWith(
        "http://13.204.176.38/api/homePageSections?section=hero"
      );
      expect(result).toEqual(mockData);
    });

    test("returns null when fetch fails", async () => {
      mockFetch(null, false);

      const result = await fetchSectionContent("hero");

      expect(result).toBeNull();
    });

    test("handles network errors", async () => {
      mockFetchError();

      const result = await fetchSectionContent("hero");

      expect(result).toBeNull();
    });

    test("constructs correct URL with section parameter", async () => {
      mockFetch({}, true);

      await fetchSectionContent("trending");

      expect(global.fetch).toHaveBeenCalledWith(
        "http://13.204.176.38/api/homePageSections?section=trending"
      );
    });
  });
});
