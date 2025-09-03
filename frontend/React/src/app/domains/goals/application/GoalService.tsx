import { apiFetch } from "../../shared/service/ApiClient";
import type { GoalData } from "../schemas/Goal";

export async function addGoal(data: GoalData) {
    return await apiFetch("/api/goals", {
        method: "POST",
        body: JSON.stringify(data),
    });
}

export async function obtainAllGoalsFromUser(data: { month: number; year?: number }): Promise<GoalData[]> {
    return await apiFetch(`/api/goals/${data.year}/${data.month}`, {
        method: "GET",
    });
}

export async function editGoal(data: GoalData) {
    return await apiFetch(`/api/goals/${data.id}`, {
        method: "PUT",
        body: JSON.stringify(data),
    });
}

export async function deleteGoal(id: number) {
    return await apiFetch(`/api/goals/${id}`, {
        method: "DELETE",
    });
}