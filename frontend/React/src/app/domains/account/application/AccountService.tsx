import { apiFetch } from "../../shared/service/ApiClient";
import { createAccountSchema } from "../schemas/Account";
import type { AccountData } from "../schemas/Account";
import { z } from "zod";
import type { FetchAccountData} from "../schemas/FetchAccount";

/**
 * Adds new accounts by requesting the TrueLayer API and saving the results to the backend.
 * @param data - Data required to fetch accounts from TrueLayer.
 * @returns A promise that resolves to an array of AccountData objects.
 * @throws Will throw an error if the backend response is invalid or if no accounts are found.
 */
export async function addAccounts(data: FetchAccountData): Promise<AccountData[]> {
  const response = await apiFetch<AccountData[]>(`/api/accounts`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  const schema = z.array(createAccountSchema());
  const parsed = schema.safeParse(response);

  if (!parsed.success) {
    console.error("Error de validación del backend:", parsed.error);
    throw new Error("Invalid account data received from backend");
  }

  if(parsed.data.length === 0){
    throw new Error("No accounts found");
  }
  return parsed.data;
}

/**
 * Fetch accounts linked to the authenticated user.
 * @returns A promise that resolves to an object containing an array of AccountData objects.
 * @throws Will throw an error if the backend response is invalid.
 */
export async function fetchAccountsForUser(): Promise<{ accounts: AccountData[] }> {
  const json = await apiFetch<{ accounts: AccountData[] }>("/api/accounts", {
    method: "GET",
  });

  const schema = z.object({
    accounts: z.array(createAccountSchema()),
  });
  const parsed = schema.safeParse(json);

  if (!parsed.success) {
    console.error("Error de validación del backend:", parsed.error);
    throw new Error("Invalid account data received from backend");
  }

  return parsed.data;
}

/**
 * Deletes an account its by ID.
 * @param id - Account ID to delete
 * @returns No content
 */
export async function deleteAccount(id: number){
  return await apiFetch(`/api/accounts/${id}`, {
    method: "DELETE"
  });

}
