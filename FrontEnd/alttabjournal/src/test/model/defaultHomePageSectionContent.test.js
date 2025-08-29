import { defaultHomePageSectionContent } from "../../main/model/defaultHomePageSectionContent";

describe("defaultHomePageSectionContent Model", () => {
  test("has correct structure", () => {
    expect(defaultHomePageSectionContent).toHaveProperty("slug");
    expect(defaultHomePageSectionContent).toHaveProperty("title");
    expect(defaultHomePageSectionContent).toHaveProperty("tag");
    expect(defaultHomePageSectionContent).toHaveProperty("imageUrl");
    expect(defaultHomePageSectionContent).toHaveProperty("author");
    expect(defaultHomePageSectionContent).toHaveProperty("date");
  });

  test("has string properties", () => {
    expect(typeof defaultHomePageSectionContent.slug).toBe("string");
    expect(typeof defaultHomePageSectionContent.title).toBe("string");
    expect(typeof defaultHomePageSectionContent.tag).toBe("string");
  });
});
