import { Agent } from "../../main/model/Agent";

describe("Agent Model", () => {
  test("has correct structure", () => {
    expect(Agent).toHaveProperty("name");
    expect(Agent).toHaveProperty("imageUrl");
    expect(Agent).toHaveProperty("title");
    expect(Agent).toHaveProperty("quote");
    expect(Agent).toHaveProperty("description");
    expect(Agent).toHaveProperty("strengths");
    expect(Agent).toHaveProperty("weaknesses");
  });

  test("has string properties", () => {
    expect(typeof Agent.name).toBe("string");
    expect(typeof Agent.imageUrl).toBe("string");
    expect(typeof Agent.title).toBe("string");
  });
});
