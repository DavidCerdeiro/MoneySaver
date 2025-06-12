import type { ForgotPasswordData } from "../schemas/forgotpassword";
import type { LogInFormData } from "../schemas/login";
import type { SignUpFormData } from "../schemas/signup";

export async function signUpUser(data: Omit<SignUpFormData, "confirmPassword">) {

  const response = await fetch("/api/users/create", {
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
export async function logInUser(data: LogInFormData) {
  const response = await fetch("/api/users/login", {
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

export async function forgotPassword(data: ForgotPasswordData) {
  const response = await fetch("/api/users/forgotPassword", {
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