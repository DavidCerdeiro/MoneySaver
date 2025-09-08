const API_URL = import.meta.env.VITE_API_URL;

/**
 * Fetches a resource, if the token access is expired tries to refresh it and retries the request.
 * @param url - The URL of the resource to fetch.
 * @param options - Fetch options.
 * @returns The response from the fetch call.
 */
export async function fetchWithRefresh(
  url: string,
  options: RequestInit = {}
): Promise<Response> {
  const response = await fetch(url, {
    ...options,
    credentials: "include",
  });

  if (response.status === 401 || response.status === 500) {
    const refreshResponse = await fetch(`${API_URL}/api/auth/sessions/refresh`, {
      method: "POST",
      credentials: "include",
    });

    if (!refreshResponse.ok) {
      throw new Error("Please log in again");
    }

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
  const fullUrl = `${API_URL}${path}`;
  const response = await fetchWithRefresh(fullUrl, {
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

  const text = await response.text();
  // If the response is empty, don't try to parse JSON
  return text ? (JSON.parse(text) as T) : ({} as T);
}