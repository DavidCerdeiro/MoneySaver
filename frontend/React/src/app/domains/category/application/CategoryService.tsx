import type { CategoryData } from "../schemas/Category";
import { apiFetch } from "../../shared/service/ApiClient";

/**
 * Adds a new category.
 * @param data The category data to add.
 * @returns The added category data.
 */
export async function addCategory(data: CategoryData) {
  return await apiFetch("/api/categories/add", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

/**
 * Modifies an existing category.
 * @param data The category data to modify.
 * @returns The modified category data.
 */
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

/**
 * Obtains all categories.
 * @returns A list of all categories.
 */
export async function obtainAllCategories() {
  return await apiFetch<{ categories: CategoryData[] }>("/api/categories/all", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}

/**
 * Fetches categories for the logged-in user.
 * @returns A list of categories for the user.
 */
export async function fetchCategoriesForUser() {
  return await apiFetch<{ categories: CategoryData[] }>("/api/categories/all", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}

/**
 * Deletes a category.
 * @param categoryId The ID of the category to delete.
 */
export async function deleteCategory(categoryId: number | undefined) {
  const response = await fetch(`/api/categories/delete?id=${categoryId}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Category deletion failed");
  }
}

/**
 * Obtains all spendings for a specific month and year for a user.
 * @param data The month and year to filter spendings.
 * @returns A list of categories for the specified month and year for the user.
 */
export async function obtainCategoriesMonthly(data: { month: number; year?: number }) {
  return apiFetch<{ categories: CategoryData[] }>(
    `/api/categories/monthly?month=${data.month}&year=${data.year}`,
    { method: "GET" }
  );
}
/**
 * Obtains all spendings for categories for two months.
 * @param data The months and years to compare.
 * @returns A list of categories for the specified months and years.
 */
export async function obtainComparisonCategories(data: { month1: number; year1?: number; month2: number; year2?: number }) {
  return apiFetch<{ month1: CategoryData[], month2: CategoryData[] }>(
    `/api/categories/compare?month1=${data.month1}&year1=${data.year1}&month2=${data.month2}&year2=${data.year2}`,
    { method: "GET" }
  );
}