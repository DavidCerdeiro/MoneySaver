import { z } from "zod";

export const createTransactionResponseSchema = () => z.object({
  id: z.number(),
  name: z.string().min(2).max(100),
  amount: z.number().min(0),
  date: z.string().min(10).max(10),
  accountName: z.string().min(2).max(100),
  accountNumber: z.string().min(2).max(100),
  categoryIcon: z.string().optional(),
  categoryName: z.string().optional(),
  establishmentName: z.string().optional(),
});

export type TransactionResponse = z.infer<ReturnType<typeof createTransactionResponseSchema>>;
