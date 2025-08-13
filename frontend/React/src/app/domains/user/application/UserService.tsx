import { apiFetch } from "../../shared/service/ApiClient";
import type { UserData } from "../schemas/User";

/**
 * Function to obtain user profile
 * @returns Promise<Response>
 */
export async function obtainUserProfile() {
  return apiFetch<{
    email: string;
    surname: string;
    name: string;
    id: number; user: UserData 
}>(
    `/api/users/getProfile`,
    { method: "GET" }
  );

}

/**
 * Function to delete user profile
 * @param data - contains the code and locale of the user
 * @returns Promise<Response>
 */
export async function deleteProfile(data: {code: string, locale: string}) {
  return apiFetch(
    `/api/users/delete`,
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
    `/api/users/verification-email/delete`,
    { method: "POST", body: JSON.stringify(data) }
  );
}

/**
 * Function to modify user profile
 * @param data UserData
 * @returns Promise<Response>
 */
export async function modifyProfile(data: UserData) {
  return apiFetch(
    `/api/users/modify`,
    { method: "PUT", body: JSON.stringify(data) }
  );
}
