import { apiFetch } from "../../shared/service/ApiClient";
import type { AddTransactionData } from "../schemas/AddTransactionData";
import type { AddTransactionResponse } from "../schemas/AddTransactionResponse";
import type { ExtractTransactionData } from "../schemas/ExtractTransactionData";
import { createExtractTransactionsResponseSchema, type ExtractTransactionResponse } from "../schemas/ExtractTransactionResponse";
import type{ TransactionResponse } from "../schemas/TransactionResponse";

/**
 * Function to extract transactions from a bank account
 * @param data - Data required to extract transactions from a bank account
 * @returns A promise that resolves to the extracted transaction data
 */
export async function extractTransactions(
  data: ExtractTransactionData
): Promise<ExtractTransactionResponse> {
  const json = await apiFetch(`/api/accounts/${data.account?.id}/transactions?from=${data.minDate}&to=${data.maxDate}&code=${data.code}`, {
    method: "GET",
  });

  const parsed = createExtractTransactionsResponseSchema.safeParse(json);

  if (!parsed.success) {
    console.error("Error de validación del backend:", parsed.error);
    throw new Error("Invalid extract transaction data received from backend");
  }

  if (!parsed.data.transactions || parsed.data.transactions.length === 0) {
    throw new Error("No transactions found");
  }

  return parsed.data;
}

/**
 * Function to add a new transaction
 * @param data - Data required to add a new transaction
 * @returns A promise that resolves to the added transaction data
 */
export async function addTransaction(data: AddTransactionData): Promise<AddTransactionResponse> {
  return apiFetch("/api/transactions", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
}

/**
 * Function to get all user transactions by month and year
 * @param month - The month to filter transactions
 * @param year - The year to filter transactions
 * @returns A promise that resolves to an array of transaction responses
 */
export async function getAllUserTransactionsByMonthAndYear(month: number, year: number): Promise<TransactionResponse[]> {
  return apiFetch(`/api/transactions?month=${month}&year=${year}`, {
    method: "GET",
  });

}

