import type { SignUpFormData } from "../schemas/signup";

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
