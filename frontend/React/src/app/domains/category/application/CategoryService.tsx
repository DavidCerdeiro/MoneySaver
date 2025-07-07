import type { CategoryData } from "../schemas/Category";

export async function addCategory(data: CategoryData) {
  const response = await fetch("/api/categories/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
}