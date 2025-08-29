import { API_BASE_URL } from "../config/api.js";

export const fetchSectionContent = async (section) => {
  try {
    const res = await fetch(
      `${API_BASE_URL}/api/homePageSections?section=${section}`
    );
    if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
    return await res.json();
  } catch (err) {
    console.error("Failed to fetch section:", section, err);
    return null; // or throw err
  }
};
