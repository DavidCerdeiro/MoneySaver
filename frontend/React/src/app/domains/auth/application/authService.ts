import type {SignUpFormData} from "@/app/domains/auth/schemas/signup";
import type {LogInFormData} from "@/app/domains/auth/schemas/login";
import type {ForgotPasswordData} from "@/app/domains/auth/schemas/forgotpassword";
import type {EmailVerificationData} from "@/app/domains/auth/schemas/emailverification";
import type {ResetPasswordData} from "@/app/domains/auth/schemas/resetpassword";
const API_URL = import.meta.env.VITE_API_URL;

/**
 * This function is used to sign up a user.
 * @param data - The data to sign up a user, excluding the confirmPassword field.
 * @returns The user data if signup is successful.
 */
export async function signUpUser(data: Omit<SignUpFormData, "confirmPassword">) {
  const response = await fetch(`${API_URL}/api/users`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }

  return await response.json();
}

/**
 * This function is used to log in a user.
 * @param data - The data to log in a user, including email and password.
 * @returns - The user data if login is successful.
 */
export async function logInUser(data: LogInFormData) {
  const response = await fetch(`${API_URL}/api/auth/sessions`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Login failed");
  }
 
  return await response.json();
}

/**
 * This function is used to initiate the forgot password process.
 * @param data - The data to log in a user, including email and password.
 */
export async function forgotPassword(data: ForgotPasswordData) {
  const response = await fetch(`${API_URL}/api/users/password-reset`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
 
}

/**
 * This function is used to verify the email during the login or forgot password process.
 * @param data - The data to verify the email, including the verification code and email.
 */
export async function emailVerification(data: EmailVerificationData) {
  const response = await fetch(`${API_URL}/api/users/authenticate`, {
    method: "PATCH",
    credentials: "include",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
 
}

/**
 * This function is used to authenticate the user after sign up.
 * @param data - The data to authenticate the user, including the verification code and email.
 */
export async function authUser(data: EmailVerificationData) {
  const response = await fetch(`${API_URL}/api/users/authenticate`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
}

/**
 * This function is used to reset the password after the user has verified their email.
 * @param data - The data to reset the password, including the new password and the verification code.
 */
export async function resetPassword(data: ResetPasswordData) {
  const response = await fetch(`${API_URL}/api/users/password-reset`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }

}
/**
 * This function is used to fetch data from a URL with automatic token refresh.
 * @param url - The URL to fetch data from.
 * @param options - The options to configure the fetch request.
 * @returns - The response from the fetch request.
 */
export async function fetchWithRefresh(
    url: string,
    options: RequestInit = {}
  ): Promise<Response> {
    
  const response = await fetch(url, {
    ...options,
    credentials: 'include',
  });

  if (response.ok) {
    return response;
  }

  const refreshResponse = await fetch(`${API_URL}/api/auth/sessions/refresh`, {
    method: 'POST',
    credentials: 'include',
  });

  if (!refreshResponse.ok) {
    throw new Error('No se pudo refrescar el token, por favor inicia sesión de nuevo.');
  }

  const retryResponse = await fetch(url, {
    ...options,
    credentials: 'include',
  });

  return retryResponse;
}
