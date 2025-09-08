const API_URL = import.meta.env.VITE_API_URL;

/**
 * Function to upload a bill
 * @param file - The bill file to upload
 * @param idSpending - The ID of the spending associated with the bill
 */
export async function uploadBill(file: File, idSpending: number) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("idSpending", idSpending.toString());

    await fetch(`${API_URL}/api/bills`, {
        method: "POST",
        body: formData,
        credentials: "include",
    });
}

/**
 * Function to get the download URL for a bill
 * @param idBill - The ID of the bill to get the download URL for
 * @returns The download URL for the bill
 */
export async function getDownloadUrl(idBill: number) {
    const response = await fetch(`${API_URL}/api/bills/${idBill}/download-url`, {
        method: "GET",
        credentials: "include",
    });

    if (!response.ok) {
        throw new Error("Failed to fetch download URL");
    }

    const data = await response.json();
    return data.downloadUrl;
}