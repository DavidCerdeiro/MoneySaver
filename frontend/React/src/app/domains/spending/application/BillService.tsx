export async function uploadBill(file: File, idSpending: number) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("idSpending", idSpending.toString());

    await fetch("/api/bills", {
        method: "POST",
        body: formData,
        credentials: "include",
    });
}

export async function getDownloadUrl(idBill: number) {
    const response = await fetch(`/api/bills/${idBill}/download-url`, {
        method: "GET",
        credentials: "include",
    });

    if (!response.ok) {
        throw new Error("Failed to fetch download URL");
    }

    const data = await response.json();
    return data.downloadUrl;
}