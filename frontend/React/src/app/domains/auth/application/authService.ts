import type {SignUpFormData} from "@/app/domains/auth/schemas/SignUp";
import type {LogInFormData} from "@/app/domains/auth/schemas/LogIn";
import type {ForgotPasswordData} from "@/app/domains/auth/schemas/ForgotPassword";
import type {EmailVerificationData} from "@/app/domains/auth/schemas/EmailVerification";
import type {ResetPasswordData} from "@/app/domains/auth/schemas/ResetPassword";
/**
 * This function is used to sign up a user.
 * @param data - The data to sign up a user, excluding the confirmPassword field.
 * @returns The user data if signup is successful.
 */
export async function signUpUser(data: Omit<SignUpFormData, "confirmPassword">) {
  const response = await fetch("/api/users/signup", {
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

/**
 * This function is used to initiate the forgot password process.
 * @param data - The data to log in a user, including email and password.
 */
export async function forgotPassword(data: ForgotPasswordData) {
  const response = await fetch("/api/users/forgot-password", {
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
  const response = await fetch("/api/users/verify", {
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
 * This function is used to authenticate the user after sign up.
 * @param data - The data to authenticate the user, including the verification code and email.
 */
export async function authUser(data: EmailVerificationData) {
  const response = await fetch("/api/users/auth", {
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
  const response = await fetch("/api/users/forgot-password/reset", {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }

}