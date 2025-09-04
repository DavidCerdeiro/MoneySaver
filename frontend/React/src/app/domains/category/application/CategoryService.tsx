import type { CategoryData } from "../schemas/Category";
import { apiFetch } from "../../shared/service/ApiClient";
const API_URL = import.meta.env.VITE_API_URL;
/**
 * Adds a new category.
 * @param data The category data to add.
 * @returns The added category data.
 */
export async function addCategory(data: CategoryData) {
  return await apiFetch("/api/categories", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

/**
 * Modifies an existing category.
 * @param data The category data to modify.
 * @returns The modified category data.
 */
export async function editCategory(id: number | undefined, data: CategoryData) {
  const response = await fetch(`${API_URL}/api/categories/${id}`, {
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
export async function getCategories() {
  return await apiFetch<{ categories: CategoryData[] }>("/api/categories", {
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
  // Llamada usando el helper apiFetch
  await apiFetch<void>(`/api/categories/${categoryId}`, {
    method: "DELETE",
  });
}

/**
 * Obtains all spendings for a specific month and year for a user.
 * @param data The month and year to filter spendings.
 * @returns A list of categories for the specified month and year for the user.
 */
export async function obtainCategoriesMonthly(data: { month: number; year?: number }) {
  return apiFetch<{ categories: CategoryData[] }>(
    `/api/categories/${data.year}/${data.month}`,
    { method: "GET" }
  );
}
/**
 * Obtains all spendings for categories for two months.
 * @param data The months and years to compare.
 * @returns A list of categories for the specified months and years.
 */
export async function obtainComparisonCategories(data: { month1: number; year1?: number; month2: number; year2?: number}) {
  return apiFetch<{ month1: CategoryData[], month2: CategoryData[] }>(
    `/api/categories/comparison?year1=${data.year1}&month1=${data.month1}&year2=${data.year2}&month2=${data.month2}`,
    { method: "GET" }
  );
}