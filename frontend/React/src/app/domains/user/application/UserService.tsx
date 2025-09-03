import { apiFetch } from "../../shared/service/ApiClient";
import type { UserData } from "../schemas/User";

/**
 * Function to obtain user profile
 * @returns Promise<Response>
 */
export async function getProfile() {
  return apiFetch<{
    typeChart: any;
    idTypeChart: number;
    email: string;
    surname: string;
    name: string;
    id: number; user: UserData 
}>(
    `/api/users/me`,
    { method: "GET" }
  );

}

/**
 * Function to delete user profile
 * @param data - contains the code and locale of the user
 * @returns Promise<Response>
 */
export async function deleteProfile(data: {code: string, locale: string}
) {
  return apiFetch(
    `/api/users/me`,
    { method: "DELETE", body: JSON.stringify(data) }
  );
}

/**
 * Function to send a verification email for account deletion
 * @param data - contains the locale of the user
 * @returns Promise<Response>
 */
export async function verificationEmailToDelete(data: {locale: string}) {
  return apiFetch(
    `/api/users/verification`,
    { method: "POST", body: JSON.stringify(data) }
  );
}

/**
 * Function to modify user profile
 * @param data UserData
 * @returns Promise<Response>
 */
export async function editProfile(data: UserData) {
  return apiFetch(
    `/api/users/me`,
    { method: "PUT", body: JSON.stringify(data) }
  );
}


export async function loadFavouriteTypeCharts() {
  return apiFetch(`/api/users/me/type-chart`, { method: "GET" });
}

export async function logout() {
  return fetch(`/api/auth/sessions`, { method: "DELETE" });
}