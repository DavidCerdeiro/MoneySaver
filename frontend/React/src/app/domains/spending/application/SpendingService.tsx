import type { SpendingData } from "../schemas/Spending";


export async function addSpending(data: SpendingData) {
  const response = await fetch("/api/spendings/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Spending addition failed");
  }
}

export async function obtainAllTypePeriodic() {
  const response = await fetch("/api/type-periodic/all", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });
  
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Signup failed");
  }
  
  return await response.json();
}

export async function obtainAllSpendingsByMonthAndUserId(data: { month: number, idUser: number, year?: number }) {
  const response = await fetch(`/api/spendings/all?month=${data.month}&idUser=${data.idUser}&year=${data.year}`, {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || "Failed to fetch spendings");
  }
  return await response.json();
}