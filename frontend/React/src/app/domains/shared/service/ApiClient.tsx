// src/shared/api/apiClient.ts
export async function fetchWithRefresh(url: string, options: RequestInit = {}): Promise<Response> {
  // 1. Original request
  const response = await fetch(url, {
    ...options,
    credentials: "include",
  });

  // 2. If 401 (token expired), try refresh
  if (response.status === 401 || response.status === 500) {
    const refreshResponse = await fetch("/api/users/auth/refresh", {
      method: "POST",
      credentials: "include",
    });

    if (!refreshResponse.ok) {
      //TODO: Implementar este proceso
      throw new Error("Sesión expirada. Por favor inicia sesión nuevamente.");
    }

    // 3. Retry original request
    return fetch(url, {
      ...options,
      credentials: "include",
    });
  }

  return response;
}

/**
 * API Helper
 */
export async function apiFetch<T>(path: string, options: RequestInit = {}): Promise<T> {
  const response = await fetchWithRefresh(path, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
  });
  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.message || `Error ${response.status}`);
  }
  return response.json() as Promise<T>;
}
