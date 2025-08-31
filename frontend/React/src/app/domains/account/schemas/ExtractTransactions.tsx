import { z } from "zod";

export const createExtractTransactionSchema  = () => z.object({
    code: z.string().min(1, { message: 'errors.requiredField' }),
})

export type ExtractTransactionData = z.infer<ReturnType<typeof createExtractTransactionSchema>>;