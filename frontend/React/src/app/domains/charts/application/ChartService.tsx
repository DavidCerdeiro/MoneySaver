import { obtainCategoriesMonthly } from "../../category/application/CategoryService";
import { getEmojiById } from "../../category/components/EmojiFunctions";
import { apiFetch } from "../../shared/service/ApiClient";

const baseColors = ["var(--chart-1)", "var(--chart-2)", "var(--chart-3)", "var(--chart-4)", "var(--chart-5)", "var(--chart-6)", "var(--chart-7)"];

/**
 * Load categories for a specific month and year.
 * @param month - The month for which to load categories.
 * @param year - The year for which to load categories.
 * @returns An array of category data.
 */
export async function loadCategories(month: number, year: number) {
  try {
    const categories = await obtainCategoriesMonthly({ month, year });

    const formattedCategories = categories.categories.map((category, index) => ({
      name: `${getEmojiById(category.icon)} ${category.name}`,
      total: Number(category.totalSpending ?? 0),
      totalLabel: `${category.totalSpending || 0} €`,
      fill: baseColors[index % baseColors.length],
    }));

    return formattedCategories;
  } catch (error) {
        console.error("Error fetching categories:", error);
  }
};

/**
 * Fetch all available chart types.
 * @returns An array of chart type data.
 */
export async function getAllTypeCharts() {
  return apiFetch(`/api/type-charts/all`, { method: "GET" });
}