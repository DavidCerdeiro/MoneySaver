import { apiFetch } from "../../shared/service/ApiClient";
import type { EstablishmentData } from "../schemas/Establishment";
import type { SpendingData } from "../schemas/Spending";
import type { SpendingResponse } from "../schemas/SpendingResponse";
const API_URL = import.meta.env.VITE_API_URL;

/**
 * Add a new spending
 * @param data Spending data to add
 */
export async function addSpending(data: SpendingData) {
  return apiFetch("/api/spendings", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

/**
 * Obtain all periodic types
 * @returns List of periodic types
 */
export async function obtainAllTypePeriodic() {
  const response = await fetch(`${API_URL}/api/type-periodic`, {
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
    `/api/spendings/${data.year}/${data.month}`,
    { method: "GET" }
  );
}

/**
 * Obtain all establishments
 * @returns List of establishments
 */
export async function obtainAllEstablishments(): Promise<EstablishmentData[]> {
  const res = await apiFetch<{ establishments: EstablishmentData[] }>(
    `/api/establishments`,
    { method: "GET", headers: { "Content-Type": "application/json" } }
  );

  return res.establishments;
}

/**
 * Original Process file to obtain information about spending
 * @param formData Form data containing the file to process
 * @returns Processed spending information
export async function processFileDirect(formData: FormData) {
  const response = await fetch(`${API_URL}/api/spendings/documents`, {
    method: "POST",
    body: formData,
    credentials: "include",
  });

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.message || `Error ${response.status}`);
  }

  return response.json();
}
  */

/**
 * MOCK: Process file to obtain information about spending
 * Simulates the processing of a bill file and returns mock spending information after a delay
 * @param _formData Form data containing the file to process
 * @returns Processed spending information
 */
export async function processFileDirect(_formData: FormData) {
    return new Promise<{
        supplierName: string;
        receiptDate: string;
        totalAmount: number;
        idEstablishment: number;
        establishmentName: string;
        idCategory: number;
    }>((resolve) => {
        setTimeout(() => {
            resolve({
                supplierName: "MERCADONA",
                receiptDate: "2024-04-10",
                totalAmount: 34.50,
                idEstablishment: 1, 
                establishmentName: "MERCADONA",
                idCategory: 2 
            });
        }, 2000); // Simulate a 2-second delay to mimic processing time
    });
}