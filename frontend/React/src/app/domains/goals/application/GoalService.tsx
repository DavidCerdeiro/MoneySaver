import { apiFetch } from "../../shared/service/ApiClient";
import type { GoalData } from "../schemas/Goal";

export async function addGoal(data: GoalData) {
    return await apiFetch("/api/goals/add", {
        method: "POST",
        body: JSON.stringify(data),
    });
}

export async function obtainAllGoalsFromUser(data: { month: number; year?: number }): Promise<GoalData[]> {
    return await apiFetch(`/api/goals/allFromUser?month=${data.month}&year=${data.year}`, {
        method: "GET",
    });
}

export async function editGoal(data: GoalData) {
    return await apiFetch("/api/goals/edit", {
        method: "PUT",
        body: JSON.stringify(data),
    });
}

export async function deleteGoal(id: number) {
    return await apiFetch(`/api/goals/delete?id=${id}`, {
        method: "DELETE",
    });
}