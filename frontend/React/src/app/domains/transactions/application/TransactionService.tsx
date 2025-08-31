import { apiFetch } from "../../shared/service/ApiClient";
import type { AddTransactionData } from "../schemas/AddTransactionData";
import type { AddTransactionResponse } from "../schemas/AddTransactionResponse";
import type { ExtractTransactionData } from "../schemas/ExtractTransactionData";
import { createExtractTransactionsResponseSchema, type ExtractTransactionResponse } from "../schemas/ExtractTransactionResponse";
import type{ TransactionResponse } from "../schemas/TransactionResponse";

export async function extractTransactions(
  data: ExtractTransactionData
): Promise<ExtractTransactionResponse> {
  const json = await apiFetch("/api/transactions/extract", {
    method: "POST",
    body: JSON.stringify(data),
  });

  const parsed = createExtractTransactionsResponseSchema.safeParse(json);

  if (!parsed.success) {
    console.error("Error de validación del backend:", parsed.error);
    throw new Error("Invalid extract transaction data received from backend");
  }

  // ✅ Aquí comprobamos si no hay transacciones
  if (!parsed.data.transactions || parsed.data.transactions.length === 0) {
    throw new Error("No transactions found");
  }

  return parsed.data;
}

export async function addTransaction(data: AddTransactionData): Promise<AddTransactionResponse> {
  return apiFetch("/api/transactions/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
}

export async function getAllUserTransactionsByMonthAndYear(month: number, year: number): Promise<TransactionResponse[]> {
  return apiFetch(`/api/transactions/all?month=${month}&year=${year}`, {
    method: "GET",
  });

}

