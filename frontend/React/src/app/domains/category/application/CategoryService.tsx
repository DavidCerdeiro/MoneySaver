import type { CategoryData } from "../schemas/Category";

export async function addCategory(data: CategoryData) {
  const response = await fetch("/api/categories/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Category addition failed");
  }
}

export async function modifyCategory(data: CategoryData) {
  console.log("Modifying category with data:", data);
  const response = await fetch("/api/categories/modify", {
    
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Category modification failed");
  }
}

export async function obtainAllCategories(data: { idUser: number }) {
  const response = await fetch("/api/categories/all", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
  
  return await response.json();
}