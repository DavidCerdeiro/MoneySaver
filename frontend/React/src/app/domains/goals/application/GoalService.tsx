import { apiFetch } from "../../shared/service/ApiClient";
import type { GoalData } from "../schemas/Goal";

/**
 * Function add a new saving goal
 * @param data - Goal data to create
 * @returns The created goal
 */
export async function addGoal(data: GoalData) {
    return await apiFetch("/api/goals", {
        method: "POST",
        body: JSON.stringify(data),
    });
}

/**
 * Function obtain all goals from user
 * @param data - Object containing month and year
 * @returns A promise that resolves to an array of GoalData objects.
 */
export async function obtainAllGoalsFromUser(data: { month: number; year?: number }): Promise<GoalData[]> {
    return await apiFetch(`/api/goals/${data.year}/${data.month}`, {
        method: "GET",
    });
}

/**
 * Function edit an existing saving goal
 * @param data - Goal data to update
 * @returns The updated goal
 */
export async function editGoal(data: GoalData) {
    return await apiFetch(`/api/goals/${data.id}`, {
        method: "PUT",
        body: JSON.stringify(data),
    });
}

/**
 * Function delete an existing saving goal
 * @param id - Goal ID to delete
 * @returns - No content
 */
export async function deleteGoal(id: number) {
    return await apiFetch(`/api/goals/${id}`, {
        method: "DELETE",
    });
}