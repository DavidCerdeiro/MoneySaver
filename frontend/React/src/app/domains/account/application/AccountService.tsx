import { apiFetch } from "../../shared/service/ApiClient";
import { createAccountSchema } from "../schemas/Account";
import type { AccountData } from "../schemas/Account";
import { z } from "zod";
import type { FetchAccountData} from "../schemas/FetchAccount";
/**
 * Obtain the access TrueLayer accessToken
 */
export async function addAccounts(data: FetchAccountData): Promise<AccountData[]> {
  // Llamada al backend
  const response = await apiFetch<AccountData[]>(`/api/accounts/extract`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  // Validar array de cuentas directamente
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



export async function fetchAccountsForUser(): Promise<{ accounts: AccountData[] }> {
  const json = await apiFetch<{ accounts: AccountData[] }>("/api/accounts/all", {
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

export async function deleteAccount(id: number){
  const response = await fetch(`/api/accounts/delete?id=${id}`, {
    method: "DELETE",
    credentials: "include",
  });

  if (!response.ok) {
    throw new Error("Failed to delete account");
  }
}
