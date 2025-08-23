export async function uploadBill(file: File, idSpending: number) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("idSpending", idSpending.toString());

    await fetch("/api/bills/add", {
        method: "POST",
        body: formData,
        credentials: "include",
    });
}
