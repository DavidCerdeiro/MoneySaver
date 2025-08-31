import { z } from "zod";

export const createTransactionSchema = () => z.object({
  code: z.string().min(1, { message: 'errors.requiredField' }),
});

export type TransactionData = z.infer<ReturnType<typeof createTransactionSchema>>;