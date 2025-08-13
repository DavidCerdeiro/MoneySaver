import { apiFetch } from "../../shared/service/ApiClient";
import type { SpendingData } from "../schemas/Spending";
import type { SpendingResponse } from "../schemas/SpendingResponse";

/**
 * Add a new spending
 * @param data Spending data to add
 */
export async function addSpending(data: SpendingData) {
  return apiFetch("/api/spendings/add", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

/**
 * Obtain all periodic types
 * @returns List of periodic types
 */
export async function obtainAllTypePeriodic() {
  const response = await fetch("/api/type-periodic/all", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
  
  return await response.json();
}

/**
 * Obtain all spendings by month and year
 * @param data Month and year to filter spendings
 * @returns List of spendings for the specified month and year
 */
export async function obtainAllSpendingsByMonthAndYear(data: { month: number; year?: number }) {
  return apiFetch<{ spendings: SpendingResponse[] }>(
    `/api/spendings/all?month=${data.month}&year=${data.year}`,
    { method: "GET" }
  );
}

/**
 * Obtain all establishments
 * @returns List of establishments
 */
export async function obtainAllEstablishments() {
  const response = await fetch(`/api/establishments/all`, {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Failed to fetch establishments");
  }
  return await response.json();
}